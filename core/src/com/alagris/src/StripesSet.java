package com.alagris.src;

import java.util.BitSet;

/**
 * Created by Alagris on 08/06/2015.
 */
public class StripesSet
{
	private final StripesGenerator parentSet0, parentSet1;
	private final BitSet[] children;
	private final GameMode mode;

	public Cell getErrorCell()
	{
		return errorCell;
	}

	public int getErrorPileColumn()
	{
		return (int) (errorCell.x / 2);
	}

	public int getErrorPileRow()
	{
		return errorCell.y;
	}

	private final Cell errorCell;
	private int extraErrorCellX;

	public StripesSet(GameMode mode)
	{
		errorCell = new Cell();
		this.mode = mode;
		this.parentSet0 = new StripesGenerator(mode.rows);
		this.parentSet1 = new StripesGenerator(mode.rows);
		children = new BitSet[mode.childSetsNumber];
		if (mode.childSetsNumber < 2) throw new IllegalArgumentException();
		for (int i = 0; i < children.length; i++)
		{
			children[i] = new StripesGenerator(mode.rows);
		}
	}

	/** Mode with mixed cells */
	public void changeSets_mode1(double darkStripesFrequency)
	{
		parentSet0.generateSet(darkStripesFrequency);
		parentSet1.generateSet(darkStripesFrequency);
		int[] intersection = parentSet1.XNOR(parentSet0);
		int randomChild = (int) (Math.random() * children.length);
		int randomRow;
		if (intersection.length == 0)
		{
			randomRow = (int) (Math.random() * getRows());
			parentSet0.flip(randomRow);
		}
		else
		{

			randomRow = intersection[(int) (Math.random() * intersection.length)];
		}
		buildChildren();
		errorCell.set(randomChild + 2, randomRow);
		children[randomChild].flip(randomRow);
		extraErrorCellX = -1;
		if (Math.random() > 0.5)
		{
			extraErrorCellX += children.length - randomChild + 2;
			children[extraErrorCellX - 2].flip(randomRow);
		}

	}

	/**
	 * Mode with universal cells (but on the right are only single-colored
	 * cells)
	 */
	public void changeSets_mode3(double darkStripesFrequency)
	{
		parentSet0.generateSet(darkStripesFrequency);
		parentSet1.generateSet(darkStripesFrequency);
		int[] intersection = parentSet1.XNOR(parentSet0);
		int randomChild = (int) (Math.random() * children.length);
		int randomRow;
		if (intersection.length == 0)
		{
			randomRow = (int) (Math.random() * getRows());
			parentSet0.flip(randomRow);
		}
		else
		{

			randomRow = intersection[(int) (Math.random() * intersection.length)];
		}
		buildChildren_noColorMixing();
		errorCell.set(randomChild + 2, randomRow);
		children[randomChild].flip(randomRow);
		extraErrorCellX = children.length - randomChild + 1;
		children[extraErrorCellX - 2].flip(randomRow);
	}

	/** no mixed cells mode */
	public void changeSets_mode2(double darkStripesFrequency)
	{
		parentSet0.generateSet(darkStripesFrequency);
		parentSet1.set(parentSet0);

		buildChildren();
		int randomChild = (int) (Math.random() * children.length);
		int randomRow = (int) (Math.random() * parentSet1.getStripesCount());

		errorCell.set(randomChild + 2, randomRow);
		children[randomChild].flip(randomRow);
		extraErrorCellX = children.length - randomChild + 1;
		children[extraErrorCellX - 2].flip(randomRow);
	}

	private void buildChildren_noColorMixing()
	{
		for (int ii = 0; ii < getRows(); ii++)
		{
			boolean color = Math.random() > 0.5 ? parentSet1.get(ii) : parentSet0.get(ii);
			for (int i = 0; i < children.length; i++)
			{
				children[i].set(ii, color);
			}
		}
	}

	private void buildChildren()
	{
		for (int i = 0; i < children.length; i++)
		{
			buildChild(children[i]);
		}
	}

	private void buildChild(BitSet set)
	{
		for (int i = 0; i < getRows(); i++)
		{
			set.set(i, Math.random() > 0.5 ? parentSet1.get(i) : parentSet0.get(i));
		}
	}

	public int getRows()
	{
		return mode.rows;
	}

	public int getColumns()
	{
		return mode.childSetsNumber + 2;// plus 2 parents
	}

	/**
	 * Parent columns are at x=0 or x=1 . Child columns are at x>1. First column
	 * (x=0) is maximally on the left. First row is at y=0 and is at the bottom
	 * of set.
	 */
	public boolean isDarkAt(int x, int y)
	{
		switch (x) {
			case 0:
				return parentSet0.get(y);
			case 1:
				return parentSet1.get(y);
			default:
				return children[x - 2].get(y);
		}
	}

	/**
	 * Parent columns are at x=0 or x=1 . Child columns are at x>1. First column
	 * (x=0) is maximally on the left. First row is at y=0 and is at the bottom
	 * of set.
	 */
	public void setCellAt(int x, int y, boolean dark)
	{
		switch (x) {
			case 0:
				parentSet0.set(y, dark);
				break;
			case 1:
				parentSet1.set(y, dark);
				break;
			default:
				children[x - 2].set(y, dark);
				break;
		}
	}

	/**
	 * Parent columns are at x=0 or x=1 . Child columns are at x>1. First column
	 * (x=0) is maximally on the left. First row is at y=0 and is at the bottom
	 * of set.
	 */
	public void flipCellAt(int x, int y)
	{
		switch (x) {
			case 0:
				parentSet0.flip(y);
				break;
			case 1:
				parentSet1.flip(y);
				break;
			default:
				children[x - 2].flip(y);
				break;
		}
	}

	public void repairErrorCell()
	{
		flipCellAt(errorCell.x, errorCell.y);
		if (extraErrorCellX != -1)
		{
			flipCellAt(extraErrorCellX, errorCell.y);
		}
	}

	public enum GameMode
	{
		EASY(2, 5), NORMAL(2, 10), HARD(3, 15), EXTREME(4, 20);

		private int childSetsNumber, rows;

		GameMode(int childSetsNumber, int rows)
		{
			this.childSetsNumber = childSetsNumber;
			this.rows = rows;
		}
	}

}
