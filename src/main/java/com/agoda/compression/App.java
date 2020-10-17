package com.agoda.compression;

import java.io.IOException;
import java.nio.file.Paths;

import com.agoda.compression.factory.Compression;
import com.agoda.compression.factory.CompressionFactory;
import com.agoda.compression.factory.CompressionType;

public class App {
	public static void main(String[] args) throws IOException {
		Compression compression = CompressionFactory.getCompression(CompressionType.ZIP);
		compression.compress(Paths.get("/Users/ashishkumarlahoti/Downloads/Agoda"),
				Paths.get("/Users/ashishkumarlahoti/Downloads"), 100);
		compression.decompress(Paths.get("/Users/ashishkumarlahoti/Downloads/Agoda.zip"),
				Paths.get("/Users/ashishkumarlahoti/Downloads/Agoda1"));
	}
}
