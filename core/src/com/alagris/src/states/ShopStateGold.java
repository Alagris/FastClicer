package com.alagris.src.states;

import com.alagris.src.ArrowsColumn;
import com.alagris.src.ButtonSquare;
import com.alagris.src.CellSelector;
import com.alagris.src.CenteredText;
import com.alagris.src.FastClicker;
import com.alagris.src.LeftText;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.Rectanglef;
import com.alagris.src.RightText;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.StripesMatrix;
import com.alagris.src.TextBounds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShopStateGold implements ShopStatePanelInterface
{

	private ShopState parent;
	private Texture goldTexture;
	private Sprite goldSprite;
	private SpriteBatch goldBatch;
	private CenteredText currencyText, titleText;
	private RightText ammountsText;
	private LeftText pricesText;
	private ArrowsColumn arrowsColumn;
	private TextBounds optionsBounds;
	private Rectanglef currencyBounds,selectionBounds;
	private ButtonSquare buttonChangeCurrency;
	private CellSelector optionSelector;
	private static final float[] price = { 0.99f, 1.8f, 3.49f, 11.99f, 19.99f };
	private static final int[] goldAmmount = { 25000, 50000, 100000, 500000, 1000000 };
	private int selectedOption = -1;
	private float lineH;
	
	public ShopStateGold(ShopState parent)
	{
		this.parent = parent;
		goldTexture = new Texture("Gold256x128.png");
		goldSprite = new Sprite(goldTexture);
		goldBatch = new SpriteBatch();
		currencyText = new CenteredText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
		titleText = new CenteredText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
		ammountsText = new RightText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());
		pricesText = new LeftText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());

		float w = FastClicker.WIDTH / 7;
		buttonChangeCurrency = new ButtonSquare(w, w * 3, w * 5, w, RectangleRenderer.getWHITE_PIXEL(), "My currency");

		float marginY = FastClicker.HEIGHT / 20;
		float marginX = FastClicker.WIDTH / 20;

		float titleTextX = 0;
		float titleTextW = FastClicker.WIDTH;
		float titleTextY = FastClicker.HEIGHT - marginY;
		titleText.setText("Get tons of gold\nin a short time.", titleTextX, titleTextY, titleTextW);

		int arrowW = FastClicker.WIDTH / 4;
		int arrowH = arrowW;
		int arrowX = (int) ((FastClicker.WIDTH - arrowW) / 2);
		int arrowY = (int) (titleTextY - titleText.getHeight() - arrowH - marginY);
		arrowsColumn = new ArrowsColumn(arrowX, arrowY, arrowW, arrowH, 1);

		int goldSpriteW = FastClicker.WIDTH - arrowX - arrowW;
		int goldSpriteH = arrowW;
		int goldSpriteX = arrowX + arrowW;
		int goldSpriteY = arrowY;
		goldSprite.setBounds(goldSpriteX, goldSpriteY, goldSpriteW, goldSpriteH);

		float currencyX = marginX;
		float currencyY = goldSpriteY;
		float currencyW = arrowX;
		float currencyH = goldSpriteH;
		currencyBounds = new Rectanglef(currencyX, currencyY, currencyW, currencyH);

		float optionsAreaX = marginX;
		float optionsAreaY = arrowY - marginY;
		float optionsAreaW = FastClicker.WIDTH - 2 * marginX;
		optionsBounds = new TextBounds(optionsAreaX, optionsAreaY, optionsAreaW);

		setCurrency("EUR", 1);

		lineH = StaticBitmapFont.getBitmapFont().getLineHeight();
		optionSelector = new CellSelector(FastClicker.WIDTH, StaticBitmapFont.getBitmapFont().getLineHeight(), 0,optionsBounds.getY() - lineH* price.length);
		float extraOutline = marginX/4;
		selectionBounds = new Rectanglef(optionsBounds.getX()-extraOutline, optionsBounds.getY()-lineH*(price.length-0.25f), optionsBounds.getWidth()+extraOutline*2,lineH);
	}

	/**
	 * @param howMuchCostsOneEuro
	 *            - e.g. for one euro you gets 2 dollars then
	 *            howMuchCostsOneEuro=2
	 */
	private void setCurrency(String currencySign, float howMuchCostsOneEuro)
	{
		currencyText.setText(currencySign, currencyBounds.getX(), currencyBounds.getY(), currencyBounds.getWidth(),
				currencyBounds.getHeight());

		String s = "";
		for (int i = 0; i < price.length; i++)
		{
			s += (howMuchCostsOneEuro * price[i]) + "\n";
		}
		resetGlyphLayoutPrices(s);
		s = "";
		for (int i = 0; i < goldAmmount.length; i++)
		{
			s += goldAmmount[i] + "\n";
		}
		resetGlyphLayoutAmmounts(s);
	}

	private void resetGlyphLayoutPrices(String s)
	{
		pricesText.setText(s, optionsBounds.getX(), optionsBounds.getY(), optionsBounds.getWidth());
	}

	private void resetGlyphLayoutAmmounts(String s)
	{
		ammountsText.setText(s, optionsBounds.getX(), optionsBounds.getY(), optionsBounds.getWidth());
	}

	@Override
	public void render()
	{
		if(selectedOption != -1){
			RectangleRenderer.begin();
			RectangleRenderer.setColor(StripesMatrix.darkCell);
			RectangleRenderer.setAlpha(0.8f);
			RectangleRenderer.setBounds(selectionBounds.getX(), selectionBounds.getY()+lineH*(selectedOption), selectionBounds.getWidth(),selectionBounds.getHeight());
			RectangleRenderer.renderRect();
			RectangleRenderer.end();
		}
		StaticBitmapFont.begin();
		titleText.render();
		pricesText.render();
		currencyText.render();
		ammountsText.render();
		StaticBitmapFont.end();
		arrowsColumn.render();
		goldBatch.begin();
		goldSprite.draw(goldBatch);
		goldBatch.end();
		if (buttonChangeCurrency.checkButton())
		{
			
		}
	}

	@Override
	public void update()
	{
		if (Gdx.input.isTouched(0))
		{
			int option = optionSelector.getSelectedRow_roundUp(FastClicker.HEIGHT - Gdx.input.getY(0));
			if (option > 0 && option <= price.length)
			{
				selectedOption = option-1;
			}
		}

	}

	@Override
	public void dispose()
	{
		goldBatch.dispose();
	}

	@Override
	public boolean buySelectedItem()
	{
		return false;
	}

	@Override
	public String getErrorText()
	{
		return "Select quantity";
	}

}
