package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShopStateHearts implements ShopStatePanelInterface
{
	private Texture heartTexture;
	private Sprite heartSprite;
	private SpriteBatch heartBatch;
	private GlyphLayout glyphLayoutText, glyphLayoutNumber;
	private int glyph1X, glyph1Y, glyph2X, glyph2Y, h, price;
	private ButtonSquare buttonRedHeart, buttonGoldHeart;
	private boolean isGoldHeartShown = false;

	public ShopStateHearts(ShopState parent)
	{
		h = FastClicker.HEIGHT / 20;
		heartTexture = new Texture("heart256x256.png");
		heartSprite = new Sprite(heartTexture);
		heartSprite.setBounds(FastClicker.WIDTH / 6, FastClicker.HEIGHT / 3, FastClicker.WIDTH / 3 * 2,
				FastClicker.WIDTH / 3 * 2);
		heartBatch = new SpriteBatch();
		glyphLayoutText = new GlyphLayout();
		glyphLayoutNumber = new GlyphLayout();
		float w = FastClicker.WIDTH / 7;
		buttonRedHeart = new ButtonSquare(w, w * 3, w * 2, w, RectangleRenderer.getWHITE_PIXEL(), "Red");
		buttonGoldHeart = new ButtonSquare(w * 4, w * 3, w * 2, w, RectangleRenderer.getWHITE_PIXEL(), "Gold");
		resetEverything();
	}

	@Override
	public void render()
	{
		if (buttonGoldHeart.checkButton())
		{
			resetEverythingGold();
		}
		if (buttonRedHeart.checkButton())
		{
			resetEverything();

		}
		StaticBitmapFont.begin();
		StaticBitmapFont.draw(glyphLayoutText, glyph1X, glyph1Y);
		StaticBitmapFont.draw(glyphLayoutNumber, glyph2X, glyph2Y);
		StaticBitmapFont.end();
		heartBatch.begin();
		heartSprite.draw(heartBatch);
		heartBatch.end();
	}

	@Override
	public void update()
	{

	}

	@Override
	public void dispose()
	{
		heartBatch.dispose();
		heartTexture.dispose();
	}

	@Override
	public boolean buySelectedItem()
	{
		if (price <= FastClicker.getMoney())
		{
			FastClicker.setMoney(FastClicker.getMoney() - price);
			if (isGoldHeartShown)
			{
				FastClicker.setGoldLifes(FastClicker.getGoldLifes() + 1);
				resetEverythingGold();
			}
			else
			{
				FastClicker.setLifes(FastClicker.getLifes() + 1);
				resetEverything();
			}

			return true;
		}
		else
		{
			return false;
		}

	}

	///////////////////
	//// RED //////////
	///////////////////

	private void resetEverything()
	{
		isGoldHeartShown = false;
		heartSprite.setColor(Color.RED);
		resetPrice();
		resetText();
	}

	private void resetPrice()
	{
		price = getPriceOfNextlife();
	}

	private void resetText()
	{
		glyphLayoutText.setText(StaticBitmapFont.getBitmapFont(), getText());
		glyphLayoutNumber.setText(StaticBitmapFont.getBitmapFont(), price + "");
		glyph1X = (int) ((FastClicker.WIDTH - glyphLayoutText.width) / 2);
		glyph1Y = FastClicker.HEIGHT - h;
		glyph2X = (int) ((FastClicker.WIDTH - glyphLayoutNumber.width) / 2);
		glyph2Y = (int) (glyph1Y - glyphLayoutText.height - h);
	}

	private int getPriceOfNextlife()
	{
		return heartPriceFunction(FastClicker.getLifes() + 1);
	}

	private String getText()
	{
		String s;
		if (FastClicker.getLifes() == 1)
		{
			s = "You have one life now.";
		}
		else
		{
			s = "You have " + FastClicker.getLifes() + " lifes now.";
		}
		s += "\nPrice of next life is:";
		return s;
	}

	private int heartPriceFunction(int heartNumber)
	{
		return (int) (100 * Math.pow(heartNumber - 1, 3));
	}

	///////////////////
	//// GOLD//////////
	///////////////////

	private void resetEverythingGold()
	{
		isGoldHeartShown = true;
		heartSprite.setColor(FastClicker.goldHeartColor);
		resetPriceGold();
		resetTextGold();
	}

	private void resetPriceGold()
	{
		price = getPriceOfNextlifeGold();
	}

	private void resetTextGold()
	{
		glyphLayoutText.setText(StaticBitmapFont.getBitmapFont(), getTextGold());
		glyphLayoutNumber.setText(StaticBitmapFont.getBitmapFont(), price + "");
		glyph1X = (int) ((FastClicker.WIDTH - glyphLayoutText.width) / 2);
		glyph1Y = FastClicker.HEIGHT - h;
		glyph2X = (int) ((FastClicker.WIDTH - glyphLayoutNumber.width) / 2);
		glyph2Y = (int) (glyph1Y - glyphLayoutText.height - h);
	}

	private int getPriceOfNextlifeGold()
	{
		return heartPriceFunctionGold(FastClicker.getGoldLifes() + 1);
	}

	private String getTextGold()
	{
		String s;
		switch (FastClicker.getGoldLifes()) {
			case 0:
				s = "You have no gold lifes\n    First one costs:";
				break;
			case 1:
				s = "You have one gold life\nnow. Price of next is:";
				break;
			default:
				s = "You have " + FastClicker.getGoldLifes() + " gold lifes\nnow. Price of next is:";
				break;
		}
		return s;
	}

	private int heartPriceFunctionGold(int heartNumber)
	{
		return (int) (1000 * Math.pow(heartNumber, 2));
	}

	@Override
	public String getErrorText()
	{
		return "not enough gold";
	}

}
