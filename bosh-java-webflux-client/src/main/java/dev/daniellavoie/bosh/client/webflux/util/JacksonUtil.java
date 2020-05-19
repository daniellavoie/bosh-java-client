package dev.daniellavoie.bosh.client.webflux.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public abstract class JacksonUtil {
	private static final ObjectMapper OBJECTMAPPER = new ObjectMapper()
			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).findAndRegisterModules();
	private static final ObjectMapper YAML_OBJECTMAPPER = new ObjectMapper(new YAMLFactory())
			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).findAndRegisterModules();

	public static <T> T read(String src, Class<T> valueType) {
		return read(src, valueType, OBJECTMAPPER);
	}

	public static <T> T read(String src, TypeReference<T> valueType) {
		return read(src, valueType, OBJECTMAPPER);
	}

	private static <T> T read(String src, Class<T> valueType, ObjectMapper objectMapper) {
		try {
			return objectMapper.readValue(src, valueType);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T read(String src, TypeReference<T> valueType, ObjectMapper objectMapper) {
		try {
			return objectMapper.readValue(src, valueType);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T read(File file, Class<T> valueType, ObjectMapper objectMapper) {
		try {
			return objectMapper.readValue(file, valueType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readYaml(String src, Class<T> valueType) {
		return read(src, valueType, YAML_OBJECTMAPPER);
	}

	public static <T> T readYaml(File file, Class<T> valueType) {
		return read(file, valueType, YAML_OBJECTMAPPER);
	}

	public static String write(Object value) {
		return write(value, OBJECTMAPPER);
	}

	private static String write(Object value, ObjectMapper objectMapper) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String writeYaml(Object value) {
		return write(value, YAML_OBJECTMAPPER);
	}
}
