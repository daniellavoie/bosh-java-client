package dev.daniellavoie.bosh.client.webflux.cli;

import java.util.function.Consumer;

import reactor.core.publisher.Mono;

public interface BoshCli {
	Mono<Void> runCliCommand(String arguments, Consumer<String> stdSink, Consumer<String> errSink);
}
