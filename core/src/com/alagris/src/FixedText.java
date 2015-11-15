package com.alagris.src;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FixedText
{
	private GlyphLayout glyphLayout;
	private BitmapFontCache bitmapFontCache;
	private SpriteBatch batch;
	private BitmapFont font;
	private String stringValue;

	public FixedText(String text, BitmapFont font, SpriteBatch batch)
	{
		this(text, font, null, batch);
	}

	public FixedText(String text, BitmapFont font, Color c, SpriteBatch batch1)
	{
		this.font = font;
		if (c == null)
		{
			glyphLayout = new GlyphLayout(font, text);
		}
		else
		{
			glyphLayout = new GlyphLayout(font, text, c, 0, Align.left, false);
		}
		stringValue = text;
		bitmapFontCache = new BitmapFontCache(font);
		bitmapFontCache.setText(glyphLayout, 0, 0);
		batch = batch1;
	}

	public void setLocation(float x, float y)
	{
		bitmapFontCache.setPosition(x, y);
	}

	public void render()
	{
		bitmapFontCache.draw(batch);
	}

	public GlyphLayout getGlyphLayout()
	{
		return glyphLayout;
	}

	public BitmapFontCache getBitmapFontCache()
	{
		return bitmapFontCache;
	}

	public SpriteBatch getBatch()
	{
		return batch;
	}

	public BitmapFont getFont()
	{
		return font;
	}

	public Color getColor()
	{
		return font.getColor();
	}

	public float getScaleX()
	{
		return font.getScaleX();
	}

	public float getScaleY()
	{
		return font.getScaleY();
	}

	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}

	public BitmapFontData getData()
	{
		return font.getData();
	}

	public String getStringValue()
	{
		return stringValue;
	}

	public void setStringValue(String stringValue)
	{
		this.stringValue = stringValue;
		bitmapFontCache.setText(glyphLayout, bitmapFontCache.getX(), bitmapFontCache.getY());
	}
}
