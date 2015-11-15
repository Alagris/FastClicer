package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.CenteredText;
import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.TextBounds;

public class ShopStateGamemode implements ShopStatePanelInterface
{
	/**
	 * If ads frequency is eqal this variable then "Never show ads" option is
	 * enabled
	 */
	private CenteredText glyphLayoutTitle, glyphLayoutGamemode;
	private ButtonSquare buttonEasier, buttonHarder;
	private TextBounds boundsTitle,boundsGamemode;
	public ShopStateGamemode(ShopState parent)
	{
		glyphLayoutTitle = new CenteredText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
		glyphLayoutGamemode = new CenteredText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
		boundsTitle = new TextBounds();
		boundsGamemode = new TextBounds();
		float h = FastClicker.HEIGHT / 20;
		float w = FastClicker.WIDTH / 40;
		
		boundsTitle.setX(w);
		boundsTitle.setY(FastClicker.HEIGHT-h);
		boundsTitle.setWidth(FastClicker.WIDTH-2*w);
		
		w = FastClicker.WIDTH / 7;
		buttonEasier = new ButtonSquare(w, w * 3, w * 2, w, RectangleRenderer.getWHITE_PIXEL(), "<<");
		buttonHarder = new ButtonSquare(w * 4, w * 3, w * 2, w, RectangleRenderer.getWHITE_PIXEL(), ">>");

		boundsGamemode.setX(0);
		boundsGamemode.setY(buttonEasier.getAbsoluteHeight() + h);
		boundsGamemode.setWidth(FastClicker.WIDTH);
		
		resetText();
	}

	private void resetText()
	{
		String s = null,s2 = "Buy checkpoint! Start every game at ";
		switch(FastClicker.getGamemode()){
			case FastClicker.MODE_SIMPLE:
				s = "EASY MODE";
				s2 += "easy mode from level";
				break;
			case FastClicker.MODE_MIXED:
				s = "MEDIUM MODE";
				s2 += "medium mode from level";
				break;
			case FastClicker.MODE_FULL:
				s = "HARD MODE";
				s2 += "hard mode from level";
				break;
		}
		glyphLayoutGamemode.setText(s,boundsGamemode.getX(),boundsGamemode.getY(),boundsGamemode.getWidth());
		
		glyphLayoutTitle.setText(s2, boundsTitle.getX(),boundsTitle.getY(),boundsTitle.getWidth());
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
		switch(FastClicker.getGamemode()){
			case FastClicker.MODE_SIMPLE:
				if(FastClicker.getLifes()>1){
					FastClicker.setLifes(FastClicker.getLifes()-1);
					return true;
				}
				return false;
			case FastClicker.MODE_MIXED:
				if(FastClicker.getGoldLifes()>0){
					FastClicker.setGoldLifes(FastClicker.getGoldLifes()-1);
					return true;
				}
				return false;
			case FastClicker.MODE_FULL:
				if(FastClicker.getLifes()>1 && FastClicker.getGoldLifes()>0){
					FastClicker.setLifes(FastClicker.getLifes()-1);
					FastClicker.setGoldLifes(FastClicker.getGoldLifes()-1);
					return true;
				}
				return false;
		}
		System.err.println("Error: com.alagris.src.states.ShopStateGamemode.buySelectedItem()");
		return false;
		
	}

	@Override
	public String getErrorText()
	{
		return "Not enough hearts";
	}

}
