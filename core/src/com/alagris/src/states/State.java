package com.alagris.src.states;

import com.alagris.src.Drawable;
import com.alagris.src.FastClicker;
import com.badlogic.gdx.utils.Disposable;

public interface State extends Drawable, Disposable {
	void createState(FastClicker mainClass);

	void stateResumed();

	void statePaused();
}
