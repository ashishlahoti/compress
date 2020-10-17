package com.agoda.compression.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CompressionFactory {

	private final static Map<CompressionType, Supplier<Compression>> compressionTypes = new HashMap<>();

	static {
		compressionTypes.put(CompressionType.ZIP, ZipCompression::new);
	}

	public static Compression getCompression(CompressionType compressionType) {
		Supplier<Compression> compression = compressionTypes.get(compressionType);
		if (compression != null) {
			return compression.get();
		}
		throw new IllegalArgumentException("No such compression type " + compressionType.name());
	}
}
