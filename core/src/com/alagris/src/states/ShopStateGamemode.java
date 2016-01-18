package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.CenteredText;
import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.TextBounds;

public class ShopStateGamemode implements ShopStatePanelInterface
{
	private CenteredText glyphLayoutTitle, glyphLayoutGamemode;
	private ButtonSquare buttonEasier, buttonHarder;
	private TextBounds boundsTitle, boundsGamemode;

	public ShopStateGamemode(ShopState parent)
	{
		glyphLayoutTitle = new CenteredText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
		glyphLayoutGamemode = new CenteredText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
		boundsTitle = new TextBounds();
		boundsGamemode = new TextBounds();
		float h = FastClicker.HEIGHT / 20;
		float w = FastClicker.WIDTH / 40;

		boundsTitle.setX(w);
		boundsTitle.setY(FastClicker.HEIGHT - h);
		boundsTitle.setWidth(FastClicker.WIDTH - 2 * w);

		w = FastClicker.WIDTH / 7;
		buttonEasier = new ButtonSquare(w, w * 3, w * 2, w, RectangleRenderer.getWHITE_PIXEL(), "<<");
		buttonHarder = new ButtonSquare(w * 4, w * 3, w * 2, w, RectangleRenderer.getWHITE_PIXEL(), ">>");

		boundsGamemode.setX(0);
		boundsGamemode.setY((buttonEasier.getAbsoluteHeight() + boundsTitle.getY())/2);
		boundsGamemode.setWidth(FastClicker.WIDTH);

		resetText();
	}

	private void resetText()
	{
		String s = null;
		switch (FastClicker.getGamemode()) {
			case FastClicker.MODE_SIMPLE:
				s = "EASY";
				break;
			case FastClicker.MODE_MIXED:
				s = "MEDIUM";
				break;
			case FastClicker.MODE_FULL:
				s = "HARD";
				break;
		}
		glyphLayoutGamemode.setText(s, boundsGamemode.getX(), boundsGamemode.getY(), boundsGamemode.getWidth());

		glyphLayoutTitle.setText("Your current game mode is", boundsTitle.getX(), boundsTitle.getY(),
				boundsTitle.getWidth());
	}

	@Override
	public void render()
	{
		StaticBitmapFont.begin();
		glyphLayoutTitle.render();
		glyphLayoutGamemode.render();
		StaticBitmapFont.end();
		if (buttonHarder.checkButton())
		{
			if (FastClicker.getGamemode() <= 1)
			{
				FastClicker.setGamemode((byte) (FastClicker.getGamemode() + 1));
				resetText();
			}

		}
		if (buttonEasier.checkButton())
		{
			if (FastClicker.getGamemode() >= 1)
			{
				FastClicker.setGamemode((byte) (FastClicker.getGamemode() - 1));
				resetText();
			}
		}
	}

	@Override
	public void update()
	{

	}

	@Override
	public void dispose()
	{
		buttonHarder.dispose();
		buttonEasier.dispose();
	}

	@Override
	public boolean buySelectedItem()
	{
		return true;

	}

	@Override
	public String getErrorText()
	{
		return "yeaahh!";
	}

}
