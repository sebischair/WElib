package de.tum.in.wwwmatthes.stm.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
	
	// Read & Write File
	
	public static String readFile(File file) throws IOException {
		return readFile(FileUtil.stringToPath(file.getAbsolutePath()));
	}
		
	public static String readFile(Path path) throws IOException {
		byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, Charset.defaultCharset());
	}
		
	public static String readFileWithRelativePath(Path path) throws IOException {
		return readFile(relativePath(path));
	}
	
	public static void writeFile(Path path, byte[] bytes) throws IOException {
		Files.write(path, bytes);
	}
	
	public static void writeFileWithRelativePath(Path path, byte[] bytes) throws IOException {
		writeFile(relativePath(path), bytes);
	}
	
	public static void writeTextFile(Path path, String text) throws IOException {
		Files.write(path, text.getBytes(Charset.defaultCharset()));
	}
	
	public static void writeTextFileWithRelativePath(Path path, String text) throws IOException {
		writeTextFile(relativePath(path), text);
	}

	// Create Directory
	
	public static void createDirectory(Path path) throws IOException {
		Files.createDirectory(path);
	}
	
	public static void createDirectoryWithRelativePath(Path path) throws IOException {
		System.out.println(path);
		System.out.println(FileUtil.class.getResource(path.toString()));
		//System.out.println(relativePath(path));
		//createDirectory(relativePath(path));
	}
	
	// Other
	
	public static String getFileNameWithoutExtension(File file) {
		String filename 	= file.getName();
		int pos 			= filename.lastIndexOf(".");
		String justName = pos > 0 ? filename.substring(0, pos) : filename;
		return justName;
	}
	
	// PRIVATE: Path
	
	
	private static Path stringToPath(String string) {
		return Paths.get(string);
	}
	
	private static Path relativePath(Path path) {
		return Paths.get(FileUtil.class.getResource(path.toString()).getPath());
	}
	
}
