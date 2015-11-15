package com.alagris.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class ArrowsColumn implements Disposable
{
	private Texture texture;
	private Sprite sprite;
	private SpriteBatch batch;
	private int y, arrowsNumber, arrowHeight;

	public ArrowsColumn(int x, int y, int w, int h, int arrowsNumber)
	{
		texture = new Texture(Gdx.files.internal(Drawable.arrowImage));
		sprite = new Sprite(texture);
		batch = new SpriteBatch();
		setBounds(x, y, w, h, arrowsNumber);
	}

	public void setBounds(int x, int y, int w, int h, int arrowsNumber)
	{
		this.y = y;
		this.arrowsNumber = arrowsNumber;
		this.arrowHeight = h / arrowsNumber;
		sprite.setSize(w, arrowHeight);
		sprite.setX(x);
	}

	public void render()
	{
		batch.begin();
		for (int i = 0; i < arrowsNumber; i++)
		{
			sprite.setY(y + i * arrowHeight);
			sprite.draw(batch);
		}
		batch.end();
	}

	@Override
	public void dispose()
	{
		texture.dispose();
		batch.dispose();
	}

	public void setLocation(float x, float y2)
	{
		this.y = (int) y2;
		sprite.setX(x);
	}

}
