package com.alagris.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonSquare extends Button
{
	private GlyphLayout glyphLayout;
	private BitmapFontCache bitmapFontCache;
	private SpriteBatch batchFont, batchTexture;
	private Sprite spriteTexture;

	public static final Color clickColor = new Color(0.4f, 0.7f, 1f, 1f);

	public ButtonSquare(float x, float y, float width, float height, Texture texture, String text)
	{
		this(x, y, width, height, texture, text, StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
	}

	private ButtonSquare(float x, float y, float width, float height, Texture texture, String text, BitmapFont font,
			SpriteBatch batch)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.batchFont = batch;
		spriteTexture = new Sprite(texture);
		spriteTexture.setBounds(x, y, width, height);
		batchTexture = new SpriteBatch();
		bitmapFontCache = new BitmapFontCache(font);
		glyphLayout = new GlyphLayout();
		setText(text);
		setColor(StripesMatrix.darkCell.r, StripesMatrix.darkCell.g, StripesMatrix.darkCell.b, 1);
	}

	public void setText(String text)
	{
		name = text;
		if (name == null) return;

		glyphLayout.setText(StaticBitmapFont.getBitmapFont(), name);
		bitmapFontCache.setColor(getRed(), getGreen(), getBlue(), getAlpha());

		textX = getX() + width / 2 - glyphLayout.width / 2;
		textY = getY() + height / 2 + glyphLayout.height / 2;
		if (textX < getX())
		{
			textX = getX();
		}
		if (textY < getY())
		{
			textY = getY();
		}
		bitmapFontCache.setText(glyphLayout, textX, textY);
	}

	@Override
	public void render()
	{
		renderTexture(getRed(), getGreen(), getBlue(), getAlpha());
		renderFont();
	}

	@Override
	public void renderClick()
	{
		renderTexture(clickColor);
		renderFont();
	}

	@Override
	public void onClick()
	{
		renderClick();
		System.out.println("Button clicked: " + name);
	}

	@Override
	public void renderMouseEnter()
	{
		renderTexture(getRed() + 0.05f, getGreen() + 0.05f, getBlue() + 0.05f, getAlpha());
		renderFont();
	}

	private void renderTexture(Color color)
	{
		renderTexture(color.r, color.g, color.b, color.a);
	}

	private void renderTexture(float r, float g, float b, float a)
	{
		batchTexture.begin();
		spriteTexture.setColor(r, g, b, a);
		spriteTexture.draw(batchTexture);
		batchTexture.end();
	}

	private void renderFont()
	{
		batchFont.begin();
		bitmapFontCache.draw(batchFont);
		batchFont.end();
	}

	public boolean checkButton()
	{
		return super.checkButton(FastClicker.getTouchX(), FastClicker.getTouchY(), Gdx.input.justTouched());
	}

	public String getText()
	{
		return name;
	}

	@Override
	public void dispose()
	{
	}

}
