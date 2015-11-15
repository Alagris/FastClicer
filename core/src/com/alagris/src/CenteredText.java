package com.alagris.src;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CenteredText
{
	private GlyphLayout glyphLayout;
	private BitmapFontCache bitmapFontCache;
	private SpriteBatch batch;
	private BitmapFont font;
	private String stringValue;

	public CenteredText(BitmapFont font, SpriteBatch batch)
	{
		this(font, font.getColor(), batch);
	}

	public CenteredText(BitmapFont font, Color c, SpriteBatch batch1)
	{
		this.font = font;
		batch = batch1;
		glyphLayout = new GlyphLayout();
		bitmapFontCache = new BitmapFontCache(font);
		bitmapFontCache.setColor(c);
	}
	/**x,y - coordinates of bottom left corner*/
	public void setText(String text, float x, float y, float width, float height)
	{
		stringValue = text;
		resetText(width);
		bitmapFontCache.setText(getGlyphLayout(), x , y + (height ) / 2);
	}
	
	/**x,y - coordinates of upper left corner*/
	public void setText(String text, float x, float y, float width)
	{
		stringValue = text;
		resetText(width);
		bitmapFontCache.setText(getGlyphLayout(), x , y);
	}

	public float getX()
	{
		return bitmapFontCache.getX();
	}

	public float getY()
	{
		return bitmapFontCache.getY();
	}

	public float getWidth()
	{
		return glyphLayout.width;
	}

	public float getHeight()
	{
		return glyphLayout.height;
	}

	private void resetText(float width)
	{
		glyphLayout = new GlyphLayout(getFont(), getText(), getColor(), width, Align.center, true);
	}

	public void render()
	{
		bitmapFontCache.draw(batch);
	}

	public void begin()
	{
		batch.begin();
	}

	public void end()
	{
		batch.end();
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
		return bitmapFontCache.getColor();
	}

	public void setColor(Color color)
	{
		setColor(color.r, color.g, color.b, color.a);
	}

	public void setColor(float r, float g, float b, float a)
	{
		bitmapFontCache.setColor(r, g, b, a);
	}

	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}

	public String getText()
	{
		return stringValue;
	}

}
