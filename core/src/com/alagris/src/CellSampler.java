package com.alagris.src;

import java.util.BitSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class CellSampler implements Disposable
{
	private Texture cellTexture;

	private Sprite cellSpriteLeft, cellSpriteRight;
	private float x, y;
	private ArrowsColumn arrowsColumn;

	public CellSampler(float cellWidth, float cellHeight, float x, float y)
	{
		cellTexture = new Texture(Gdx.files.internal(Drawable.ovalImage));
		cellTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		cellSpriteLeft = buildCellSpriteLeft();
		cellSpriteRight = buildCellSpriteRight();
		setSizeOfCell(cellWidth, cellHeight);
		this.x = x;
		this.y = y;
		int arrowWidth = (int) cellWidth / 2;
		arrowsColumn = new ArrowsColumn((int) (x + cellWidth * 2 - arrowWidth / 2), (int) y, arrowWidth,
				(int) cellHeight, 1);
	}

	public void setPositionOfDownLeftCorner(float x, float y)
	{
		this.x = x;
		this.y = y;
		arrowsColumn.setLocation(x, y);
	}

	/**
	 * Pile has size of 2 cells
	 * 
	 * @param cellWidth
	 * @param cellHeight
	 */
	public void setSizeOfCell(float cellWidth, float cellHeight)
	{
		cellSpriteLeft.setSize(cellWidth, cellHeight);
		cellSpriteRight.setSize(cellWidth, cellHeight);
	}

	private Sprite buildCellSpriteLeft()
	{
		return new Sprite(cellTexture, 0, 0, cellTexture.getWidth() / 2, cellTexture.getHeight());
	}

	private Sprite buildCellSpriteRight()
	{
		return new Sprite(cellTexture, cellTexture.getWidth() / 2, 0, cellTexture.getWidth() / 2,
				cellTexture.getHeight());
	}

	@Override
	public void dispose()
	{
		cellTexture.dispose();
	}

	public void render(SpriteBatch batchLeft, SpriteBatch batchRight, BitSet set)
	{
		batchLeft.begin();
		renderLeft(batchLeft, set);
		batchLeft.end();
		batchRight.begin();
		renderRight(batchRight, set);
		batchRight.end();
		renderArrow();
	}

	public void renderLeft(SpriteBatch batch, BitSet set)
	{
		setColorForIndex(cellSpriteLeft, 0, set);
		cellSpriteLeft.setPosition(x, y);
		cellSpriteLeft.draw(batch);
		setColorForIndex(cellSpriteLeft, 2, set);
		cellSpriteLeft.setPosition(x + getCellW() * 2, y);
		cellSpriteLeft.draw(batch);
	}

	private void setColorForIndex(Sprite sprite, int i, BitSet set)
	{
		if (set.get(i))
		{
			sprite.setColor(StripesMatrix.darkCell);
		}
		else
		{
			sprite.setColor(StripesMatrix.lightCell);
		}
	}

	public void renderArrow()
	{
		arrowsColumn.render();
	}

	public void renderRight(SpriteBatch batch, BitSet set)
	{
		setColorForIndex(cellSpriteRight, 1, set);
		cellSpriteRight.setPosition(x + getCellW(), y);
		cellSpriteRight.draw(batch);
		setColorForIndex(cellSpriteRight, 3, set);
		cellSpriteRight.setPosition(x + getCellW() * 3, y);
		cellSpriteRight.draw(batch);
	}

	private float getCellW()
	{
		return cellSpriteLeft.getWidth();
	}

}
