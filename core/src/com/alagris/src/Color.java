package com.alagris.src;

public class Color {
	public static final Color GREEN = new Color(0, 1, 0, 1);
	public static final Color RED = new Color(1, 0, 0, 1);
	public static final Color WHITE = new Color(1, 1, 1, 1);
	public static final Color BLACK = new Color(0, 0, 0, 1);
	public static final Color GREY = new Color(1.9921875f, 1.9921875f, 1.9921875f, 1);

	private float[] color;

	public float[] getFloats() {
		return color;
	}

	public Color() {
		color = new float[4];
	}

	public Color(float r, float g, float b) {
		this(r, g, b, 1);
	}

	public Color(float r, float g, float b, float a) {
		color = new float[4];
		set(r, g, b, a);
	}

	public void set(float r, float g, float b, float a) {
		set(r, g, b);
		setAlpha(a);
	}

	public void set(float r, float g, float b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
	}

	public void setColor(float color[]) {
		this.color = color;
	}

	public float getRed() {
		return color[0];
	}

	public void setRed(float red) {
		color[0] = red;
	}

	public float getGreen() {
		return color[1];
	}

	public void setGreen(float green) {
		color[1] = green;
	}

	public float getBlue() {
		return color[2];
	}

	public void setBlue(float blue) {
		color[2] = blue;
	}

	public float getAlpha() {
		return color[3];
	}

	public void setAlpha(float alpha) {
		color[3] = alpha;
	}

}
