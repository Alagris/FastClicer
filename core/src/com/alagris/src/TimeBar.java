package com.alagris.src;

import com.badlogic.gdx.utils.Disposable;

public class TimeBar implements Disposable {
	private int x, y, w, h;

	public TimeBar(int x, int y, int width, int height) {
		setBounds(x, y, width, height);

	}

	private void setBounds(int x2, int y2, int w2, int h2) {
		x = x2;
		y = y2;
		w = w2;
		h = h2;
	}

	public void render(float percentageOfLeftTime) {
		RectangleRenderer.begin();
		RectangleRenderer.setBounds(x, y, w * percentageOfLeftTime, h);
		RectangleRenderer.setColor(1 - percentageOfLeftTime, percentageOfLeftTime, 0, 1);
		RectangleRenderer.renderRect();
		RectangleRenderer.end();
	}

	@Override
	public void dispose() {
	}

}
