package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.Drawable;
import com.alagris.src.FastClicker;
import com.alagris.src.FixedText;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.StripesMatrix;
import com.alagris.src.states.GameState.PlayingStates;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public class GameStatePausedPanel implements Disposable, Drawable
{
	private ButtonSquare buttonResume;
	private ButtonSquare buttonExit;
	private int x, y, w, h;
	private GameState parent;
	private FixedText pausedText;

	public GameStatePausedPanel(int x1, int y1, int w1, int h1, GameState gameState)
	{
		parent = gameState;
		x = x1;
		y = y1;
		w = w1;
		h = h1;
		int marginY = (int) (h * 0.2);
		int marginX = w / 8;
		int buttonX = x + marginX;
		int buttonW = w - 2 * marginX;
		buttonResume = new ButtonSquare(buttonX, y + marginY * 2f, buttonW, marginY, RectangleRenderer.getWHITE_PIXEL(),
				"Resume");
		buttonExit = new ButtonSquare(buttonX, y + marginY / 2, buttonW, marginY, RectangleRenderer.getWHITE_PIXEL(),
				"Exit");
		BitmapFont font = StaticBitmapFont.buildNewBitmapFontObjectWithNewData(StaticBitmapFont.getScaleW() * 2,
				StaticBitmapFont.getScaleH() * 2);
		pausedText = new FixedText("PAUSED", font, StripesMatrix.darkCell, StaticBitmapFont.getSpriteBatch());
		;
		pausedText.setLocation((FastClicker.WIDTH - pausedText.getGlyphLayout().width) / 2,
				buttonResume.getAbsoluteHeight() + marginY);
	}

	@Override
	public void render()
	{
		RectangleRenderer.begin();
		RectangleRenderer.setBounds(x, y, w, h);
		RectangleRenderer.setColor(1, 1, 1, 1f);
		RectangleRenderer.renderRect();
		RectangleRenderer.end();
		if (buttonExit.checkButton())
		{
			parent.finishGameSesionWithoutShowingAdv();
		}
		if (buttonResume.checkButton())
		{
			parent.setPlayingStateWithoutPrompt(PlayingStates.PLAYING);
		}
		pausedText.getBatch().begin();
		pausedText.render();
		pausedText.getBatch().end();
	}

	@Override
	public void update()
	{

	}

	@Override
	public void dispose()
	{
		buttonExit.dispose();
		buttonResume.dispose();
	}

}
