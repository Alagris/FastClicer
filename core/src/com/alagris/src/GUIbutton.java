package com.alagris.src;

import com.badlogic.gdx.utils.Disposable;

public interface GUIbutton extends Disposable {

	/** Renders normal look of button */
	public void render();

	/** Renders button clicked */
	public void renderClick();

	/** Action to do on activation */
	public void onClick();

	/** Renders button while mouse is in button's area */
	public void renderMouseEnter();
}
