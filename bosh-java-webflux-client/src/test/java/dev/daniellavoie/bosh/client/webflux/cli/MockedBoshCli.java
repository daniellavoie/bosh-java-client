package dev.daniellavoie.bosh.client.webflux.cli;

import java.util.function.Consumer;

import reactor.core.publisher.Mono;

public class MockedBoshCli implements BoshCli {
	
	@Override
	public Mono<Void> runCliCommand(String arguments, Consumer<String> stdSink, Consumer<String> errSink) {
		return Mono.empty();
	}
	
	

}
