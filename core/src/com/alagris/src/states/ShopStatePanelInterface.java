package com.alagris.src.states;

import com.alagris.src.Drawable;
import com.badlogic.gdx.utils.Disposable;

public interface ShopStatePanelInterface extends Disposable, Drawable
{
	/** Returns true if player can afford to buy the item and transaction occurs */
	public boolean buySelectedItem();
	public String getErrorText();
}
