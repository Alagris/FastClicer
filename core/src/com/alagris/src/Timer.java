package com.alagris.src;

import com.badlogic.gdx.Gdx;

/**
 * Created by Alagris on 17/06/2015.
 */
public class Timer {
	private double elapsedTime;

	public Timer(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Timer() {
	}

	public void update() {
		elapsedTime += Gdx.graphics.getDeltaTime();
	}

	public double getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public boolean hasElapsedMoreThan(double limit) {
		return elapsedTime > limit;
	}

	public double howMuchTo(double limit) {
		return limit - getElapsedTime();
	}

	public void resetTimer() {
		setElapsedTime(0);
	}
}
