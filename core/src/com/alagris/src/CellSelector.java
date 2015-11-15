package com.alagris.src;

public class CellSelector {
	public static final boolean SELECTOR_MODE_ROUND_DOWN = true;
	public static final boolean SELECTOR_MODE_ROUND_UP = false;

	public float getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(float cellWidth) {
		this.cellWidth = cellWidth;
	}

	private float cellWidth;

	public float getCellHeight() {
		return cellHeight;
	}

	public void setCellHeight(float cellHeight) {
		this.cellHeight = cellHeight;
	}

	private float cellHeight;
	/** Indicates location of selection area */
	private float x, y;

	public CellSelector(float cellWidth, float cellHeight, float x, float y) {
		this.cellHeight = cellHeight;
		this.cellWidth = cellWidth;
		setLocation(x, y);
	}

	/**
	 */
	public Cell getCell_roundDown(Pointf p) {
		return getCell_roundDown(p.getX(), p.getY());
	}

	/**
	 */
	public Cell getCell_roundDown(float x, float y) {
		return getCellShifted_roundDown(getXonSelectionArea(x), getYonSelectionArea(y));
	}

	/**
	 * This method assumes that position arguments (x,y) have already been
	 * shifted according to x and y position of selector
	 * 
	 */
	private Cell getCellShifted_roundDown(float x, float y) {
		return new Cell(getSelectedColumn_roundDown(x), getSelectedRow_roundDown(y));
	}

	/**
	 */
	public Cell getCell_roundUp(Pointf p) {
		return getCell_roundUp(p.getX(), p.getY());
	}

	/**
	 */
	public Cell getCell_roundUp(float x, float y) {
		return getCellShifted_roundUp(getXonSelectionArea(x), getYonSelectionArea(y));
	}

	/**
	 * This method assumes that position arguments (x,y) have already been
	 * shifted according to x and y position of selector
	 * 
	 */
	private Cell getCellShifted_roundUp(float x, float y) {
		return new Cell(getSelectedColumn_roundUp(x), getSelectedRow_roundUp(y));
	}

	/**
	 */
	public Cell getCell(Pointf p, boolean selectorMode) {
		return getCell(p.getX(), p.getY(), selectorMode);
	}

	/**
	 */
	public Cell getCell(float x, float y, boolean selectorMode) {
		return getCellShifted(getXonSelectionArea(x), getYonSelectionArea(y), selectorMode);
	}

	private Cell getCellShifted(float x, float y, boolean selectorMode) {
		if (selectorMode) {
			return getCellShifted_roundDown(x, y);
		} else {
			return getCellShifted_roundUp(x, y);
		}
	}

	public int getSelectedColumn_roundUp(float x) {
		return getSelectedColumnShifted_roundUp(getXonSelectionArea(x));
	}

	/**
	 * This method assumes that position arguments (x,y) have already been
	 * shifted according to x and y position of selector
	 * 
	 */
	private int getSelectedColumnShifted_roundUp(float x) {
		return (int) Math.ceil((double) x / cellWidth);
	}

	public int getSelectedColumn_roundDown(float x) {
		return getSelectedColumnShifted_roundDown(getXonSelectionArea(x));
	}

	/**
	 * This method assumes that position arguments (x,y) have already been
	 * shifted according to x and y position of selector
	 * 
	 */
	private int getSelectedColumnShifted_roundDown(float x) {
		return (int) ((double) x / cellWidth);
	}

	public int getSelectedRow_roundUp(float y) {
		return getSelectedRowShifted_roundUp(getYonSelectionArea(y));
	}

	/**
	 * This method assumes that position arguments (x,y) have already been
	 * shifted according to x and y position of selector
	 * 
	 */
	private int getSelectedRowShifted_roundUp(float y) {
		return (int) Math.ceil((double) y / cellHeight);
	}

	public int getSelectedRow_roundDown(float y) {
		return getSelectedRowShifted_roundDown(getYonSelectionArea(y));
	}

	/**
	 * This method assumes that position arguments (x,y) have already been
	 * shifted according to x and y position of selector
	 * 
	 */
	private int getSelectedRowShifted_roundDown(float y) {
		return (int) ((double) y / cellHeight);
	}

	/**
	 * Returns x coordinates of point on the screen relatively to x coordinates
	 * of location of this selection area
	 * 
	 * @param x
	 *            - x coordinates of point on screen
	 */
	public float getXonSelectionArea(float x) {
		return x - getX();
	}

	/**
	 * Returns y coordinates of point on the screen relatively to y coordinates
	 * of location of this selection area
	 * 
	 * @param y
	 *            - y coordinates of point on screen
	 */
	public float getYonSelectionArea(float y) {
		return y - getY();
	}

	public void setLocation(float x, float y) {
		setX(x);
		setY(y);
	}

	/** Indicates location of selection area */
	public float getX() {
		return x;
	}

	/** Indicates location of selection area */
	public void setX(float x) {
		this.x = x;
	}

	/** Indicates location of selection area */
	public float getY() {
		return y;
	}

	/** Indicates location of selection area */
	public void setY(float y) {
		this.y = y;
	}
}
