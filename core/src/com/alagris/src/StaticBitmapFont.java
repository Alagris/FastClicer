package com.alagris.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class StaticBitmapFont
{
	private static BitmapFont bitmapFont;
	private static SpriteBatch spriteBatch;
	private static float scaleH, scaleW;
	private static final Color defaultColor = Color.WHITE;
	private static Texture glyphsTexture;
	private static TextureRegion textureRegion;
	private static BitmapFontData data;

	public static GlyphLayout getGlyphLayout(CharSequence str)
	{
		return new GlyphLayout(bitmapFont, str);
	}

	public static GlyphLayout getGlyphLayout(CharSequence str, Color color, float targetWidth)
	{
		return new GlyphLayout(bitmapFont, str, color, targetWidth, Align.left, true);
	}

	public static GlyphLayout getGlyphLayout(CharSequence str, float targetWidth)
	{
		return new GlyphLayout(bitmapFont, str, defaultColor, targetWidth, Align.center, true);
	}

	public static BitmapFont buildNewBitmapFontObjectWithNewData(float scaleH, float scaleW)
	{
		BitmapFont font = new BitmapFont(Gdx.files.internal("Neuropolitical RG.fnt"), textureRegion, false);
		font.getData().setScale(scaleW, scaleH);
		return font;
	}

	public static BitmapFont buildNewBitmapFontObjectWithNewData()
	{
		BitmapFont font = new BitmapFont(Gdx.files.internal("Neuropolitical RG.fnt"), textureRegion, false);
		font.getData().setScale(scaleW, scaleH);
		return font;
	}

	public static BitmapFont buildNewBitmapFontObject()
	{
		BitmapFont font = new BitmapFont(data, textureRegion, true);
		return font;
	}

	public static void initialize(int w, int h)
	{
		spriteBatch = new SpriteBatch();
		glyphsTexture = new Texture(Gdx.files.internal("Neuropolitical RG.png"), false);
		textureRegion = new TextureRegion(glyphsTexture);
		data = new BitmapFontData(Gdx.files.internal("Neuropolitical RG.fnt"), false);
		bitmapFont = new BitmapFont(data, textureRegion, true);
		// height
		float defaultH = 16 * 80;
		// width
		// float defaultW = 10 * 80;
		scaleH = h / defaultH;
		scaleW = scaleH;// w / defaultW;
		bitmapFont.getData().setScale(scaleW, scaleH);
		bitmapFont.setColor(defaultColor);
	}

	public static void begin()
	{
		spriteBatch.begin();
	}

	public static void end()
	{
		spriteBatch.end();
	}

	public static GlyphLayout draw(CharSequence str, float x, float y, float extraScale)
	{
		bitmapFont.getData().scale(extraScale);
		GlyphLayout gl = bitmapFont.draw(spriteBatch, str, x, y);
		bitmapFont.getData().setScale(scaleW, scaleH);
		return gl;
	}

	public static GlyphLayout draw(String str, int x, float y, Color c)
	{
		bitmapFont.setColor(c);
		GlyphLayout gl = bitmapFont.draw(spriteBatch, str, x, y);
		bitmapFont.setColor(defaultColor);
		return gl;
	}

	public static GlyphLayout draw(CharSequence str, float x, float y)
	{
		return bitmapFont.draw(spriteBatch, str, x, y);
	}

	public static GlyphLayout draw(CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap)
	{
		return bitmapFont.draw(spriteBatch, str, x, y, targetWidth, halign, wrap);
	}

	public static GlyphLayout draw(CharSequence str, float x, float y, int start, int end, float targetWidth,
			int halign, boolean wrap)
	{
		return bitmapFont.draw(spriteBatch, str, x, y, start, end, targetWidth, halign, wrap);
	}

	public static void draw(GlyphLayout layout, float x, float y)
	{
		bitmapFont.draw(spriteBatch, layout, x, y);
	}

	public static void draw(GlyphLayout layout, float x, float y, float extraScale)
	{
		bitmapFont.getData().setScale(scaleW * extraScale, scaleH * extraScale);
		bitmapFont.draw(spriteBatch, layout, x, y);
		bitmapFont.getData().setScale(scaleW, scaleH);
	}

	public static void setColor(Color color)
	{
		bitmapFont.setColor(color);
	}

	public static void setColor(float r, float g, float b, float a)
	{
		bitmapFont.setColor(r, g, b, a);
	}

	public static void dispose()
	{
		spriteBatch.dispose();
		bitmapFont.dispose();
		glyphsTexture.dispose();
	}

	public Color getColor()
	{
		return bitmapFont.getColor();
	}

	public float getScaleX()
	{
		return bitmapFont.getScaleX();
	}

	public float getScaleY()
	{
		return bitmapFont.getScaleY();
	}

	public TextureRegion getRegion()
	{
		return bitmapFont.getRegion();
	}

	public Array<TextureRegion> getRegions()
	{
		return bitmapFont.getRegions();
	}

	public TextureRegion getRegion(int index)
	{
		return bitmapFont.getRegion(index);
	}

	public float getLineHeight()
	{
		return bitmapFont.getLineHeight();
	}

	public float getSpaceWidth()
	{
		return bitmapFont.getSpaceWidth();
	}

	public float getXHeight()
	{
		return bitmapFont.getXHeight();
	}

	public float getCapHeight()
	{
		return bitmapFont.getCapHeight();
	}

	public float getAscent()
	{
		return bitmapFont.getAscent();
	}

	public float getDescent()
	{
		return bitmapFont.getDescent();
	}

	public static boolean isFlipped()
	{
		return bitmapFont.isFlipped();
	}

	public static void setUseIntegerPositions(boolean integer)
	{
		bitmapFont.setUseIntegerPositions(integer);
	}

	public static boolean usesIntegerPositions()
	{
		return bitmapFont.usesIntegerPositions();
	}

	public static BitmapFontCache getCache()
	{
		return bitmapFont.getCache();
	}

	public static BitmapFontData getData()
	{
		return bitmapFont.getData();
	}

	public static void setFixedWidthGlyphs(CharSequence glyphs)
	{
		bitmapFont.setFixedWidthGlyphs(glyphs);
	}

	public static boolean ownsTexture()
	{
		return bitmapFont.ownsTexture();
	}

	public static void setOwnsTexture(boolean ownsTexture)
	{
		bitmapFont.setOwnsTexture(ownsTexture);
	}

	public static BitmapFontCache newFontCache()
	{
		return bitmapFont.newFontCache();
	}

	public static void setColor(float color)
	{
		spriteBatch.setColor(color);
	}

	public static float getPackedColor()
	{
		return spriteBatch.getPackedColor();
	}

	public static void flush()
	{
		spriteBatch.flush();
	}

	public static void disableBlending()
	{
		spriteBatch.disableBlending();
	}

	public static void enableBlending()
	{
		spriteBatch.enableBlending();
	}

	public static void setBlendFunction(int srcFunc, int dstFunc)
	{
		spriteBatch.setBlendFunction(srcFunc, dstFunc);
	}

	public static int getBlendSrcFunc()
	{
		return spriteBatch.getBlendSrcFunc();
	}

	public static int getBlendDstFunc()
	{
		return spriteBatch.getBlendDstFunc();
	}

	public static Matrix4 getProjectionMatrix()
	{
		return spriteBatch.getProjectionMatrix();
	}

	public static Matrix4 getTransformMatrix()
	{
		return spriteBatch.getTransformMatrix();
	}

	public static void setProjectionMatrix(Matrix4 projection)
	{
		spriteBatch.setProjectionMatrix(projection);
	}

	public static void setTransformMatrix(Matrix4 transform)
	{
		spriteBatch.setTransformMatrix(transform);
	}

	public static void setShader(ShaderProgram shader)
	{
		spriteBatch.setShader(shader);
	}

	public static ShaderProgram getShader()
	{
		return spriteBatch.getShader();
	}

	public static boolean isBlendingEnabled()
	{
		return spriteBatch.isBlendingEnabled();
	}

	public static boolean isDrawing()
	{
		return spriteBatch.isDrawing();
	}

	public static BitmapFont getBitmapFont()
	{
		return bitmapFont;
	}

	public static SpriteBatch getSpriteBatch()
	{
		return spriteBatch;
	}

	public static Texture getGlyphsTexture()
	{
		return glyphsTexture;
	}

	public static TextureRegion getTextureRegion()
	{
		return textureRegion;
	}

	public static float getScaleH()
	{
		return scaleH;
	}

	public static float getScaleW()
	{
		return scaleW;
	}

}
