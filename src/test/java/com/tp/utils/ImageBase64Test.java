package com.tp.utils;

import java.io.InputStream;

import org.junit.Test;

public class ImageBase64Test {
	private static final String PNG = "png";
	private static final String GIF = "gif";

	@Test
	public void encodeGame() throws Exception {

		String path = "/image/game_1x.gif";
		InputStream io = this.getClass().getResourceAsStream(path);
		String base64 = FileUtils.encodeBase64Img(io, GIF);
		System.out.println("game: " + base64);
	}

	@Test
	public void encodeSoft() throws Exception {
		String path = "/image/yingyong_1x.gif";
		InputStream io = this.getClass().getResourceAsStream(path);
		String base64 = FileUtils.encodeBase64Img(io, GIF);
		System.out.println("soft: " + base64);
	}

	@Test
	public void encodeMore() throws Exception {
		String path = "/image/more_2x.gif";
		InputStream io = this.getClass().getResourceAsStream(path);
		String base64 = FileUtils.encodeBase64Img(io, GIF);
		System.out.println("more: " + base64);
	}

	@Test
	public void encodeBaidu() throws Exception {
		String path = "/image/baidu.png";
		InputStream io = this.getClass().getResourceAsStream(path);
		String base64 = FileUtils.encodeBase64Img(io, PNG);
		System.out.println("baidu: " + base64);
	}

	@Test
	public void encodeZhidao() throws Exception {
		String path = "/image/zhidao.png";
		InputStream io = this.getClass().getResourceAsStream(path);
		String base64 = FileUtils.encodeBase64Img(io, PNG);
		System.out.println("zhidao: " + base64);
	}

}
