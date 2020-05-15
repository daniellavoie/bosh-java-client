package dev.daniellavoie.bosh.client;

import java.util.concurrent.Flow.Publisher;

public interface BoshClient {
	Publisher<Void> deploy(String manifest);
}
