package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.CenteredText;
import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShopStateGoldFree implements ShopStatePanelInterface
{
	/**
	 * If ads frequency is equal this variable then "Never show ads" option is
	 * enabled
	 */
	public static final int minAdsFrequency = 10;
	private Texture goldTexture;
	private Sprite goldSprite;
	private SpriteBatch goldBatch;
	private GlyphLayout glyphLayoutText;
	private int glyph1X, glyph1Y, glyph2Y, glyph3Y;
	private CenteredText glyphLayoutAdsFreq;
	private ButtonSquare buttonMoreFrequently, buttonLessFrequently;

	public ShopStateGoldFree(ShopState parent)
	{
		goldTexture = new Texture("Gold256x128.png");
		goldSprite = new Sprite(goldTexture);
		goldBatch = new SpriteBatch();
		glyphLayoutAdsFreq = new CenteredText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
		String text = "Reclaim your gold that you earn on seeing ads. So far you collected:";
		float h = FastClicker.HEIGHT / 20;
		float w = FastClicker.WIDTH / 40;
		int glyph1W = (int) (FastClicker.WIDTH - w * 2);
		glyphLayoutText = StaticBitmapFont.getGlyphLayout(text, glyph1W);
		glyph1X = (int) (w);
		glyph1Y = (int) (FastClicker.HEIGHT - h);

		glyph2Y = (int) (glyph1Y - glyphLayoutText.height - h);
		goldSprite.setSize(FastClicker.WIDTH / 3 * 2, FastClicker.WIDTH / 3);
		goldSprite.setPosition(FastClicker.WIDTH / 6, glyph2Y - goldSprite.getHeight() - h);

		w = FastClicker.WIDTH / 7;
		buttonMoreFrequently = new ButtonSquare(w, w * 3, w * 2, w, RectangleRenderer.getWHITE_PIXEL(), "Less");
		buttonLessFrequently = new ButtonSquare(w * 4, w * 3, w * 2, w, RectangleRenderer.getWHITE_PIXEL(), "More");
		glyph3Y = (int) (buttonMoreFrequently.getAbsoluteHeight() + 2 * h);

		resetText();
	}

	private void resetText()
	{
		if (FastClicker.getNumberOfLostGamesToShowAdv() == minAdsFrequency)
		{
			glyphLayoutAdsFreq.setText("Never show ads.", 0, glyph3Y, FastClicker.WIDTH);
		}
		else
		{
			String s = "Show ads after \n" + FastClicker.getNumberOfLostGamesToShowAdv() + " lost game";
			if (FastClicker.getNumberOfLostGamesToShowAdv() > 1) s += "s";
			glyphLayoutAdsFreq.setText(s, 0, glyph3Y, FastClicker.WIDTH);
		}
	}

	@Override
	public void render()
	{
		goldBatch.begin();
		goldSprite.draw(goldBatch);
		goldBatch.end();
		StaticBitmapFont.begin();
		StaticBitmapFont.draw(glyphLayoutText, glyph1X, glyph1Y);
		GlyphLayout goldNumber = new GlyphLayout(StaticBitmapFont.getBitmapFont(),
				"" + FastClicker.getMoneyToReclaim());
		StaticBitmapFont.draw(goldNumber, (FastClicker.WIDTH - goldNumber.width) / 2, glyph2Y);
		glyphLayoutAdsFreq.render();
		StaticBitmapFont.end();
		if (buttonLessFrequently.checkButton())
		{
			if (FastClicker.getNumberOfLostGamesToShowAdv() < minAdsFrequency)
			{
				FastClicker.setNumberOfLostGamesToShowAdv(FastClicker.getNumberOfLostGamesToShowAdv() + 1);
				resetText();
			}

		}
		if (buttonMoreFrequently.checkButton())
		{
			if (FastClicker.getNumberOfLostGamesToShowAdv() > 1)
			{
				FastClicker.setNumberOfLostGamesToShowAdv(FastClicker.getNumberOfLostGamesToShowAdv() - 1);
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
		buttonLessFrequently.dispose();
		buttonMoreFrequently.dispose();
		goldTexture.dispose();
		goldBatch.dispose();
	}

	@Override
	public boolean buySelectedItem()
	{
		FastClicker.setMoney(FastClicker.getMoney() + FastClicker.getMoneyToReclaim());
		FastClicker.setMoneyToReclaim(0);
		return true;
	}

	@Override
	public String getErrorText()
	{
		return null;
	}

}
