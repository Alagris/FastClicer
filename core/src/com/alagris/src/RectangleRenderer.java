package com.alagris.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class RectangleRenderer {
	private static Texture WHITE_PIXEL;
	private static Sprite SPRITE;
	private static SpriteBatch BATCH;

	public static void initialize() {
		WHITE_PIXEL = new Texture(Gdx.files.internal("whitePixel.png"));
		SPRITE = new Sprite(WHITE_PIXEL);
		BATCH = new SpriteBatch();
	}

	public static final void setBounds(int x, int y, int w, int h) {
		SPRITE.setBounds(x, y, w, h);
	}

	public static void setRegion(int x, int y, int width, int height) {
		SPRITE.setRegion(x, y, width, height);
	}

	public static void setBounds(float x, float y, float width, float height) {
		SPRITE.setBounds(x, y, width, height);
	}

	public static void setSize(float width, float height) {
		SPRITE.setSize(width, height);
	}

	public static void setPosition(float x, float y) {
		SPRITE.setPosition(x, y);
	}

	public static void setX(float x) {
		SPRITE.setX(x);
	}

	public static void setY(float y) {
		SPRITE.setY(y);
	}

	public static void setCenterX(float x) {
		SPRITE.setCenterX(x);
	}

	public static void setCenterY(float y) {
		SPRITE.setCenterY(y);
	}

	public static void setCenter(float x, float y) {
		SPRITE.setCenter(x, y);
	}

	public static void setColor(Color tint) {
		SPRITE.setColor(tint);
	}

	public static void setAlpha(float a) {
		SPRITE.setAlpha(a);
	}

	public static void setColor(float r, float g, float b, float a) {
		SPRITE.setColor(r, g, b, a);
	}

	public static void setColor(float color) {
		SPRITE.setColor(color);
	}

	public static Color getColor() {
		return SPRITE.getColor();
	}

	public static void setRegion(float u, float v, float u2, float v2) {
		SPRITE.setRegion(u, v, u2, v2);
	}

	public static void begin() {
		BATCH.begin();
	}

	public static void end() {
		BATCH.end();
	}

	public static void renderRect() {

		SPRITE.draw(BATCH);

	}

	public static void dispose() {
		BATCH.dispose();
		WHITE_PIXEL.dispose();
	}

	public static Texture getWHITE_PIXEL() {
		return WHITE_PIXEL;
	}

	public static Sprite getSPRITE() {
		return SPRITE;
	}

	public static SpriteBatch getBATCH() {
		return BATCH;
	}
}
