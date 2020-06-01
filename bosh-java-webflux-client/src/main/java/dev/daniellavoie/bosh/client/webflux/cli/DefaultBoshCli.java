package dev.daniellavoie.bosh.client.webflux.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class DefaultBoshCli implements BoshCli {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBoshCli.class);

	private final String cliPath;

	public DefaultBoshCli(String cliPath) {
		this.cliPath = cliPath;

		LOGGER.info("Testing Bosh CLI.");

		runCliCommand("environments", log -> LOGGER.info("Test bosh cli environment event : {}", log),
				log -> LOGGER.info("Test bosh cli environment error : {}", log)).block();
	}

	private RuntimeException buildError(List<String> errorsBuffer) {
		return new RuntimeException("Failed to update Bosh environment. Cause : \n"
				+ errorsBuffer.stream().collect(Collectors.joining("\n")));
	}

	@Override
	public Mono<Void> runCliCommand(String arguments, Consumer<String> stdSink, Consumer<String> errSink) {
		try {
			var errorsBuffer = new ArrayList<String>();

			var commandAndArguments = Stream.concat(Stream.of(cliPath), Arrays.stream(arguments.split(" ")))
					.toArray(String[]::new);

			Process process = new ProcessBuilder(commandAndArguments).start();
			var pid = process.pid();

			Flux.fromStream(new BufferedReader(new InputStreamReader(process.getInputStream())).lines())

					.doOnNext(output -> LOGGER.debug("Stdout from process {} : {}", pid, output))

					.doOnNext(stdSink::accept)

					.doOnError(throwable -> LOGGER.error("Failed to process output from process " + process.pid() + ".",
							throwable))

					.subscribeOn(Schedulers.boundedElastic())

					.subscribe();

			Flux.fromStream(new BufferedReader(new InputStreamReader(process.getErrorStream())).lines())

					.doOnNext(output -> LOGGER.debug("Error from process {} : ", pid, output))

					.doOnNext(errorsBuffer::add)

					.doOnNext(errSink::accept)

					.doOnError(throwable -> LOGGER.error("Failed to process error output from process.", throwable))

					.subscribeOn(Schedulers.boundedElastic())

					.subscribe();

			return Mono.fromFuture(process.onExit())

					.doOnSubscribe(subscription -> LOGGER.info(
							"Started process " + process.pid() + " with command \n\n{}\n", cliPath + " " + arguments))

					.doOnNext(completedProcess -> LOGGER.info("Process " + process.pid() + " exited with {}",
							completedProcess.exitValue()))

					.<Void>flatMap(completedProcess -> completedProcess.exitValue() == 0 ? Mono.empty()
							: Mono.error(buildError(errorsBuffer)))

					.subscribeOn(Schedulers.boundedElastic());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
