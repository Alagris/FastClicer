package com.alagris.src;

public abstract class Graphics extends Rectanglef {
	// ////////////////////////////////////////////
	// / variables
	// ////////////////////////////////////////////
	protected Color RGB = new Color(1f, 1f, 1f, 1f);

	// ////////////////////////////////////////////
	// / constructors
	// ////////////////////////////////////////////
	public Graphics() {
		super();
	}

	public Graphics(Rectanglef r) {
		super(r);
	}

	public Graphics(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Graphics(Pointf location, float width, float height) {
		super(location, width, height);
	}

	// ////////////////////////////////////////////
	// / setters and getters
	// ////////////////////////////////////////////
	public void setColor(float r, float g, float b) {
		getColor().set(r, g, b);
	}

	public void setColor(float r, float g, float b, float a) {
		getColor().set(r, g, b, a);
	}

	public Color getColor() {
		return RGB;
	}

	@Override
	public String toString() {
		return super.toString() + " RGB{ red:" + RGB.getRed() + " green:" + RGB.getGreen() + " blue:" + RGB.getBlue()
				+ "}";
	}

	public float[] getFloats() {
		return getColor().getFloats();
	}

	public void setAlpha(float alpha) {
		getColor().setAlpha(alpha);
	}

	public float getGreen() {
		return getColor().getGreen();
	}

	public void setGreen(float green) {
		getColor().setGreen(green);
	}

	public void setRed(float red) {
		getColor().setRed(red);
	}

	public void set(float r, float g, float b) {
		getColor().set(r, g, b);
	}

	public float getBlue() {
		return getColor().getBlue();
	}

	public void setBlue(float blue) {
		getColor().setBlue(blue);
	}

	public void setColor(float[] color) {
		getColor().setColor(color);
	}

	public float getRed() {
		return getColor().getRed();
	}

	public float getAlpha() {
		return getColor().getAlpha();
	}
}
