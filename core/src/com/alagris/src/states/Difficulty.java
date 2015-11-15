package com.alagris.src.states;

public enum Difficulty {
	EASY(100), NORMAL(50), HARD(25);

	private int value;

	private Difficulty(int difficulty) {
		this.setValue(difficulty);
	}

	public int getValue() {
		return value;
	}

	private void setValue(int value) {
		this.value = value;
	}

}