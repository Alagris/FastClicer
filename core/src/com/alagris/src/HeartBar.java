package com.alagris.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class HeartBar implements Disposable
{
	private Texture texture;
	private Sprite sprite;
	private SpriteBatch batch;
	private int cornerX, cornerY;
	private int max;

	public HeartBar(int sizeOfHeart, int centerOfBarX, int centerOfBarY, int max, Color c)
	{
		this(max, c);
		setHeartSize(sizeOfHeart);
		cornerX = centerOfBarX;
		cornerY = centerOfBarY;
	}

	public HeartBar(int sizeOfHeart, int x, int y, int w, int h, int max, Color c)
	{
		this(max, c);
		setHeartSize(sizeOfHeart);
		cornerX = x;
		cornerY = y;
	}

	private HeartBar(int max, Color c)
	{
		texture = new Texture(Gdx.files.internal("heart256x256.png"));
		sprite = new Sprite(texture);
		sprite.setColor(c);
		batch = new SpriteBatch();
		this.max = max;
	}

	/**
	 * Hearts are squares
	 * 
	 * @param sizeOfHeart
	 *            - in pixels
	 */
	public void setHeartSize(int sizeOfHeart)
	{
		sprite.setSize(sizeOfHeart, sizeOfHeart);
	}

	public void render(int heartsNumber)
	{

		// if (renderInCenter)
		// {
		// int x = (int) (cornerX - sprite.getWidth() * heartsNumber / 2);
		// int y = (int) (cornerY - sprite.getHeight() / 2);
		// for (int i = 0; i < heartsNumber; i++, x += sprite.getWidth())
		// {
		// sprite.setPosition(x, y);
		// sprite.draw(batch);
		// }
		// }
		// else
		// {
		if (heartsNumber > max)
		{
			GlyphLayout gl = new GlyphLayout(StaticBitmapFont.getBitmapFont(), "X " + heartsNumber);
			StaticBitmapFont.begin();
			StaticBitmapFont.draw(gl, cornerX + sprite.getWidth(), cornerY + (sprite.getHeight() + gl.height) / 2);
			StaticBitmapFont.end();
			batch.begin();
			sprite.setPosition(cornerX, cornerY);
			sprite.draw(batch);
		}
		else
		{
			batch.begin();
			for (int i = 0, x = cornerX; i < heartsNumber; i++, x += sprite.getWidth())
			{
				sprite.setPosition(x, cornerY);
				sprite.draw(batch);
			}
		}
		// }
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		texture.dispose();
	}
}
