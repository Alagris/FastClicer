package com.alagris.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Alagris on 12/06/2015.
 */
public class StripesMatrix extends Graphics implements Drawable, Disposable
{

	public StripesSet set;
	private float cellWidth;
	private float cellHeight;
	private Texture cellTexture;
	private Sprite cellSpriteLeft, cellSpriteRight, pileSprite;
	private SpriteBatch batchLeft, batchRight, pileBatch;

	public StripesMatrix(Rectanglef r, StripesSet set)
	{
		super(r);
		setModel(set);
	}

	public StripesMatrix(float x, float y, float width, float height, StripesSet set)
	{
		super(x, y, width, height);
		setModel(set);
	}

	public StripesMatrix(Pointf location, float width, float height, StripesSet set)
	{
		super(location, width, height);
		setModel(set);
	}

	private void setModel(StripesSet set)
	{
		this.set = set;
		cellWidth = getWidth() / set.getColumns();
		cellHeight = getHeight() / set.getRows();
		cellTexture = new Texture(Gdx.files.internal(Drawable.ovalImage));
		cellTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		cellSpriteLeft = buildCellSpriteLeft();
		batchLeft = new SpriteBatch();
		cellSpriteRight = buildCellSpriteRight();
		batchRight = new SpriteBatch();
		pileSprite = buildPileSprite();
		pileSprite.setColor(com.badlogic.gdx.graphics.Color.RED);
		pileBatch = new SpriteBatch();
		setSizeOfCell(cellWidth, cellHeight);

	}

	private Sprite buildCellSpriteLeft()
	{
		return new Sprite(cellTexture, 0, 0, cellTexture.getWidth() / 2, cellTexture.getHeight());
	}

	private Sprite buildPileSprite()
	{
		return new Sprite(cellTexture);
	}

	private Sprite buildCellSpriteRight()
	{
		return new Sprite(cellTexture, cellTexture.getWidth() / 2, 0, cellTexture.getWidth() / 2,
				cellTexture.getHeight());
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
		pileSprite.setSize(cellWidth * 2, cellHeight);
	}

	public CellSelector getCellSelector()
	{
		// cellWidth * 2
		// because during the game it won't matter
		// which part (left or right) of pile player chose
		// it's important to just chose the correct row
		return new CellSelector(cellWidth * 2, cellHeight, getX(), getY());
	}

	@Override
	public void setWidth(float width)
	{
	}

	@Override
	public void setHeight(float height)
	{
	}

	/** Coordinates of most left bottom cell are x=0 y=0 */
	public void renderSinglePile(int cellX, int cellY)
	{
		pileBatch.begin();
		renderSingleCell(pileBatch, pileSprite, getX() + cellX * cellWidth * 2, getY() + cellY * cellHeight);
		pileBatch.end();
	}

	/** Coordinates of most left bottom cell are x=0 y=0 */
	private void renderSingleCell(Batch batch, Sprite sprite, int cellX, int cellY)
	{
		renderSingleCell(batch, sprite, getX() + cellX * cellWidth, getY() + cellY * cellHeight);
	}

	/** Coordinates of most left bottom cell are x=0 y=0 */
	private void renderSingleCell(Batch batch, Sprite sprite, float x, float y)
	{
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

	@Override
	public void render()
	{
		for (int column = 0; column < set.getColumns(); column += 2)
		{
			batchLeft.begin();
			renderColumn(batchLeft, cellSpriteLeft, column);
			batchLeft.end();
		}

		for (int column = 1; column < set.getColumns(); column += 2)
		{
			batchRight.begin();
			renderColumn(batchRight, cellSpriteRight, column);
			batchRight.end();
		}
	}

	public static final Color darkCell = Color.valueOf("2473cb");
	public static final Color lightCell = new Color(92.2f, 92.2f, 92.2f, 1);

	private void renderColumn(Batch batch, Sprite sprite, int column)
	{

		for (int row = 0; row < set.getRows(); row++)
		{
			if (set.isDarkAt(column, row))
			{
				sprite.setColor(darkCell);
			}
			else
			{
				sprite.setColor(lightCell);
			}
			renderSingleCell(batch, sprite, column, row);
		}
	}

	@Override
	public void update()
	{

	}

	@Override
	public void dispose()
	{
		cellTexture.dispose();
		batchLeft.dispose();
		batchRight.dispose();
		pileBatch.dispose();
	}

	public float getCellWidth()
	{
		return cellWidth;
	}

	public float getCellHeight()
	{
		return cellHeight;
	}
}
