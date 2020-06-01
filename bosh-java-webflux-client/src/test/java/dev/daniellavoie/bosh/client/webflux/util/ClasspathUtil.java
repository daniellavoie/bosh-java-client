package dev.daniellavoie.bosh.client.webflux.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

public abstract class ClasspathUtil {
	public static String readContent(String resource) {
		try {
			return new BufferedReader(new InputStreamReader(new ClassPathResource(resource).getInputStream())).lines()
					.collect(Collectors.joining("\n"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
