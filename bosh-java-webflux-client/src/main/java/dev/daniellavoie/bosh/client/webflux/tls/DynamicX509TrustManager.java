package dev.daniellavoie.bosh.client.webflux.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

public class DynamicX509TrustManager extends X509ExtendedTrustManager {
	private final X509TrustManager delegate;

	public DynamicX509TrustManager(byte[][] CAs) {
		try {
			var trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

			var keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(null, null);

			IntStream.range(0, CAs.length).boxed()
					.forEach(index -> addCertificateToTrustStore(index, CAs[index], keystore));

			trustManagerFactory.init(keystore);

			this.delegate = Arrays.stream(trustManagerFactory.getTrustManagers())
					.filter(trustManager -> trustManager instanceof X509TrustManager).findFirst()
					.map(trustManager -> (X509TrustManager) trustManager)
					.orElseThrow(() -> new IllegalArgumentException("Could not find an X509TrustManager."));
		} catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void addCertificateToTrustStore(int index, byte[] ca, KeyStore keystore) {
		try {
			keystore.setCertificateEntry(Integer.toString(index + 1), (X509Certificate) CertificateFactory
					.getInstance("X.509").generateCertificate(new ByteArrayInputStream(ca)));
		} catch (KeyStoreException | CertificateException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String s) throws CertificateException {
		delegate.checkClientTrusted(chain, s);
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String s, Socket socket) throws CertificateException {
		delegate.checkClientTrusted(chain, s);
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String s, SSLEngine sslEngine) throws CertificateException {
		delegate.checkClientTrusted(chain, s);
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String s) throws CertificateException {
		delegate.checkServerTrusted(chain, s);
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String s, Socket socket) throws CertificateException {
		delegate.checkServerTrusted(chain, s);
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String s, SSLEngine sslEngine) throws CertificateException {
		delegate.checkServerTrusted(chain, s);
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return delegate.getAcceptedIssuers();
	}
}
