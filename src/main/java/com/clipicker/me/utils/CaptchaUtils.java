package com.clipicker.me.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.UUID;

public class CaptchaUtils {

	public static final int CAPTCHA_CHAR_SIZE = 6;
	public static final String CAPTCHA_KEY = "CAPTCHA_KEY";

	/**
	 * Generates a random alpha-numeric string of eight characters.
	 *
	 * @return random alpha-numeric string of eight characters.
	 */
	public static String generateText() {
		return UUID.randomUUID().toString().substring(0, CAPTCHA_CHAR_SIZE);
	}

	/**
	 * Generates a PNG image of text 180 pixels wide, 40 pixels high with white background.
	 *
	 * @param text expects string size eight (8) characters.
	 * @return byte array that is a PNG image generated with text displayed.
	 */
	public static byte[] generateImage(String text) {
		int w = 140, h = 40;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(Color.white);
		g.fillRect(0, 0, w, h);
		g.setFont(new Font("Serif", Font.ITALIC | Font.BOLD | Font.TYPE1_FONT, 26));
		g.setColor(Color.blue);
		int start = 10;
		byte[] bytes = text.getBytes();

		Random random = new Random();
		for (int i = 0; i < bytes.length; i++) {
			g.setColor(Color.decode("#000000"));
			g.drawString(new String(new byte[]{bytes[i]}), start + (i * 20), (int) (Math.random() * 20 + 20));
		}
		g.setColor(Color.white);
		for (int i = 0; i < 8; i++) {
			g.drawOval((int) (Math.random() * 160), (int) (Math.random() * 10), 30, 30);
		}
		g.dispose();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", bout);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bout.toByteArray();
	}
}