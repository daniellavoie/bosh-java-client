package dev.daniellavoie.bosh.client.webflux.util;

import javax.net.ssl.SSLException;

import dev.daniellavoie.bosh.client.webflux.tls.DynamicX509TrustManager;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

public abstract class SslUtil {
	public static SslContext createSSLContext(byte[][] ca) {
		try {
			return SslContextBuilder.forClient().trustManager(new DynamicX509TrustManager(ca)).build();
		} catch (SSLException e) {
			throw new RuntimeException(e);
		}
	}
}
