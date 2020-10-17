package com.agoda.compression.factory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompression implements Compression {

	public void compress(Path inDir, Path outDir, long maxSize) throws IOException {
		if (Files.isDirectory(outDir)) {
			Path zipPath = outDir.resolve(inDir.getFileName() + ".zip");
			Files.deleteIfExists(zipPath);
			Files.createFile(zipPath);
			try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipPath))) {
				if (Files.isDirectory(inDir)) {
					Files.walk(inDir).filter(path -> !Files.isDirectory(path)).forEach(path -> {
						System.out.println(path.toAbsolutePath());
						ZipEntry zipEntry = new ZipEntry(inDir.relativize(path).toString());
						try {
							zipOutputStream.putNextEntry(zipEntry);
							if (Files.isRegularFile(path)) {
								Files.copy(path, zipOutputStream);
							}
							zipOutputStream.closeEntry();
						} catch (IOException e) {
							System.err.println(e);
						}
					});
				}
			} catch (IOException e) {
				System.err.println(e);
			}
		} else {
			System.out.println("Output Directory Path is not correct");
		}
	}

	public void decompress(Path inDir, Path outDir) throws IOException {
		if (!Files.exists(inDir)) {
			System.out.println("Zip file path doesnot exist");
		}
		deleteDirectoryIfExist(outDir);
		Files.createDirectory(outDir);
		try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(inDir))) {
			ZipEntry entry;
			while ((entry = zipInputStream.getNextEntry()) != null) {
				final Path toPath = outDir.resolve(entry.getName());
				if (entry.isDirectory()) {
					Files.createDirectory(toPath);
				} else {
					if (!Files.exists(toPath.getParent())) {
						Files.createDirectories(toPath.getParent());
					}
					Files.copy(zipInputStream, toPath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteDirectoryIfExist(Path dir) throws IOException {
		if (Files.exists(dir)) {
			Files.walk(dir).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		}
	}
}
