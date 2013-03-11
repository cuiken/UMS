package com.tp.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.tp.entity.FileType;

public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	private static final String ROOT_PATH = Constants.LOCKER_STORAGE;

	private FileUtils() {
	}

	public static List<File> unZip(File srcFile, String rootPath) {
		try {
			ZipFile zipFile = new ZipFile(srcFile, "GBK", true);

			Enumeration<ZipArchiveEntry> files = zipFile.getEntries();
			String folderName = UUIDGenerator.uuid2();
			File baseDir = new File(rootPath + folderName);
			baseDir.mkdirs();
			List<ZipArchiveEntry> entries = Lists.newArrayList();
			while (files.hasMoreElements()) {
				ZipArchiveEntry file = files.nextElement();
				if (file.isDirectory()) {
					new File(baseDir, file.getName()).mkdirs();

				} else {
					entries.add(file);
				}
			}
			return outputFile(entries, zipFile, baseDir);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Collections.emptyList();
		}

	}

	public static String getExtension(String fname) {
		int index = StringUtils.lastIndexOf(fname, Constants.DOT_SEPARATOR);
		return StringUtils.substring(fname, index + 1);
	}

	public static String getFileName(String fullName) {
		int index = StringUtils.lastIndexOf(fullName, Constants.DOT_SEPARATOR);
		return StringUtils.substring(fullName, 0, index);
	}

	public static List<String> imgExtensions() {
		return Arrays.asList(Constants.IMG_EXTENSION);

	}

	public static long getFileSize(String child) {
		return new File(Constants.LOCKER_STORAGE, child).length();
	}

	public static boolean isPreClient(String fname) {

		return StringUtils.containsIgnoreCase(fname, FileType.PREVIEW_CLIENT.getValue());

	}

	public static boolean isAd(String fname) {
		return StringUtils.containsIgnoreCase(fname, FileType.AD.getValue());
	}

	public static boolean isPreWeb(String fname) {
		return StringUtils.containsIgnoreCase(fname, FileType.PREVIEW_WEB.getValue());
	}

	public static boolean isIcon(String fname) {
		return StringUtils.containsIgnoreCase(fname, FileType.ICON.getValue());
	}

	public static boolean isUx(String ext) {
		return StringUtils.equalsIgnoreCase(ext, FileType.UX.getValue());
	}

	public static boolean isHUx(String fname) {
		return StringUtils.containsIgnoreCase(fname, FileType.UX_H.getValue());
	}

	public static boolean isWUx(String fname) {
		return StringUtils.containsIgnoreCase(fname, FileType.UX_W.getValue());
	}

	public static boolean isApk(String ext) {
		return StringUtils.equalsIgnoreCase(ext, FileType.APK.getValue());
	}

	private static List<File> outputFile(List<ZipArchiveEntry> entries, ZipFile zipFile, File baseDir) throws Exception {
		List<File> files = Lists.newArrayList();
		for (ZipArchiveEntry entry : entries) {
			File file = new File(baseDir, entry.getName());

			String relativePath = StringUtils.substring(file.getPath(), ROOT_PATH.length());
			files.add(new File(relativePath));
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
			InputStream inputStream = zipFile.getInputStream(entry);
			byte[] data = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			outputStream.close();
		}

		zipFile.close();
		return files;
	}

	public static String getIconLevel(String fileName) {

		Pattern p = Pattern.compile("[\\d]+");
		Matcher m = p.matcher(fileName);
		while (m.find()) {
			return m.group();
		}
		return "";
	}

	public static String getImageSpec(String fname) {
		int start = StringUtils.lastIndexOf(fname, "_");
		int dotEnd = StringUtils.lastIndexOf(fname, Constants.DOT_SEPARATOR);
		return StringUtils.substring(fname, start + 1, dotEnd);
	}

	public static String getIcon(File srcFile, String destPath, String fname) throws Exception {
		if (srcFile != null) {
			String extension = FileUtils.getExtension(fname);
			File targetDir = new File(destPath);
			File targetFile = new File(targetDir, UUIDGenerator.uuid2() + "." + extension);
			org.apache.commons.io.FileUtils.copyFile(srcFile, targetFile);
			return StringUtils.substringAfter(targetFile.getPath(), Constants.LOCKER_STORAGE);

		}
		return "";
	}

	public static String encodeBase64Img(String path) throws Exception {

		File image = new File(path);
		if (!image.exists())
			return "";
		FileInputStream input = new FileInputStream(image);
		return encodeBase64Img(input,getExtension(image.getName()));
	}
	
	public static String encodeBase64Img(InputStream input,String ext) throws Exception{
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int n = 0;
		try {
			while (-1 != (n = input.read(buffer))) {
				out.write(buffer, 0, n);
			}
			out.flush();
		} finally {
			input.close();
		}
		return "data:image/" + ext + ";base64," + Encodes.encodeBase64(out.toByteArray());
	}
}
