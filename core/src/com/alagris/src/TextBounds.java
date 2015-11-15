package com.alagris.src;

public class TextBounds
{
	public TextBounds()
	{
	}
	
	public TextBounds(float x, float y, float width)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	private float x,y,width;
}
