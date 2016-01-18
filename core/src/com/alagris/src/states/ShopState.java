package com.alagris.src.states;

import com.alagris.src.AnimatedText;
import com.alagris.src.ButtonSquare;
import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.states.GameStates.StateOfGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

public class ShopState implements State
{
	private byte currentPageIndex = 0;
	private FastClicker mainClass;
	private ShopStatePanelInterface heartsPanel, goldPanel, freeGoldPanel, gamemodePanel;
	private ButtonSquare buttonBuy, buttonExit, buttonNext, buttonPrevious;
	private AnimatedText animatedText;
	private GlyphLayout playerGoldTextGlyph, playerGoldValueGlyph;
	private ShopStatePanelInterface[] panels;
	private int goldTextX, goldTextY, goldNumberY, goldNumberX;

	@Override
	public void createState(FastClicker mainClass)
	{
		this.mainClass = mainClass;
		float w = FastClicker.WIDTH / 7;
		float h = w / 2;
		animatedText = new AnimatedText(StaticBitmapFont.getBitmapFont(), 0, h * 2.5f, FastClicker.WIDTH,
				StaticBitmapFont.getSpriteBatch(), 1f);
		buttonExit = new ButtonSquare(w, 0, w * 2, h * 2, RectangleRenderer.getWHITE_PIXEL(), "Exit");
		buttonBuy = new ButtonSquare(w * 4, 0, w * 2, h * 2, RectangleRenderer.getWHITE_PIXEL(), "Buy");
		buttonNext = new ButtonSquare(w * 5, h * 3, w * 2, h * 2, RectangleRenderer.getWHITE_PIXEL(), "Next");
		buttonPrevious = new ButtonSquare(0, h * 3, w * 2, h * 2, RectangleRenderer.getWHITE_PIXEL(), "Back");
		heartsPanel = new ShopStateHearts(this);
		goldPanel = new ShopStateGold(this);
		freeGoldPanel = new ShopStateGoldFree(this);
		gamemodePanel = new ShopStateGamemode(this);
		panels = new ShopStatePanelInterface[] { heartsPanel, goldPanel, freeGoldPanel, gamemodePanel };
		animatedText.setText("not enough gold", Color.RED);
		playerGoldValueGlyph = new GlyphLayout();
		playerGoldTextGlyph = new GlyphLayout(StaticBitmapFont.getBitmapFont(), "Your gold:");
		goldTextX = (int) ((FastClicker.WIDTH - playerGoldTextGlyph.width) / 2);
		goldTextY = (int) (h * 5);
		goldNumberY = (int) (h * 4);
	}

	public void setErrorText(String text)
	{
		if (text == null) return;
		animatedText.setText(text, Color.RED);
	}

	public void resetGoldValue()
	{
		playerGoldValueGlyph.setText(StaticBitmapFont.getBitmapFont(), "" + FastClicker.getMoney(), Color.YELLOW, 0,
				Align.left, false);
		goldNumberX = (int) ((FastClicker.WIDTH - playerGoldValueGlyph.width) / 2);
	}

	@Override
	public void render()
	{
		panels[currentPageIndex].render();
		if (buttonBuy.checkButton())
		{
			if (!panels[currentPageIndex].buySelectedItem())
			{
				setErrorText(panels[currentPageIndex].getErrorText());
				animatedText.startAnimation();

			}
			else
			{
				resetGoldValue();
			}
		}
		if (buttonExit.checkButton())
		{
			mainClass.setCurrentSate(StateOfGame.MENU);
		}
		if (buttonNext.checkButton())
		{
			currentPageIndex++;
			if (currentPageIndex >= panels.length)
			{
				currentPageIndex = 0;
			}
		}
		if (buttonPrevious.checkButton())
		{
			currentPageIndex--;
			if (currentPageIndex <= -1)
			{
				currentPageIndex = (byte) (panels.length - 1);
			}
		}
		StaticBitmapFont.begin();
		StaticBitmapFont.draw(playerGoldTextGlyph, goldTextX, goldTextY);
		StaticBitmapFont.draw(playerGoldValueGlyph, goldNumberX, goldNumberY);
		animatedText.render();

		StaticBitmapFont.end();
	}

	@Override
	public void update()
	{
		panels[currentPageIndex].update();
		animatedText.update();
	}

	@Override
	public void dispose()
	{
		buttonBuy.dispose();
		buttonExit.dispose();
		buttonNext.dispose();
		buttonPrevious.dispose();
		heartsPanel.dispose();
		goldPanel.dispose();
	}

	@Override
	public void stateResumed()
	{
		resetGoldValue();
	}

	@Override
	public void statePaused()
	{

	}

}
