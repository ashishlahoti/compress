package com.agoda.compression.factory;

import java.io.IOException;
import java.nio.file.Path;

public interface Compression {

	public void compress(Path inDir, Path outDir, long maxSize) throws IOException;

	public void decompress(Path inDir, Path outDir) throws IOException;
}
