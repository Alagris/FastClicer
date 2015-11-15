package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.Drawable;
import com.alagris.src.FastClicker;
import com.alagris.src.FixedText;
import com.alagris.src.IncomeAnimation;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.StripesMatrix;
import com.alagris.src.states.GameState.PlayingStates;
import com.alagris.src.states.GameStates.StateOfGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class GameStateGaveOverPanel implements Disposable, Drawable
{

	private ButtonSquare buttonRestartGame;
	private ButtonSquare buttonExit;
	private int x, y, w, h;
	private SpriteBatch newRecordBatch;
	private FixedText scoreText, bestText, newRecordText, incomeText;
	private GameState parent;
	private int score;
	private BitmapFont font;
	private boolean showNewRecord = false;
	private Sprite newRecordSprite;
	private Texture newRecordTexture;
	private RecordLevel textureLoadedFromLevel = RecordLevel.LEVEL_0;
	private int income;
	private IncomeAnimation incomeAnimation;

	public GameStateGaveOverPanel(int x, int y, int width, int height, GameState parent)
	{
		// colour variables
		final Color numbersFontColor = Color.GREEN;
		final Color textFontColor = StripesMatrix.darkCell;
		// variables indicating layout
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		int marginY = (int) (h * 0.2);
		int marginX = w / 10;
		int buttonX = x + marginX;
		int buttonW = w - 2 * marginX;
		// reading best record

		// copy of static font
		font = StaticBitmapFont.buildNewBitmapFontObject();
		font.setColor(numbersFontColor);
		// buttons
		buttonRestartGame = new ButtonSquare(buttonX, y + marginY * 2f, buttonW, marginY,
				RectangleRenderer.getWHITE_PIXEL(), "Once again");
		buttonExit = new ButtonSquare(buttonX, y + marginY / 2, buttonW, marginY, RectangleRenderer.getWHITE_PIXEL(),
				"Exit");
		// batches
		newRecordBatch = new SpriteBatch();
		// score texts
		scoreText = new FixedText("Score:", font, textFontColor, StaticBitmapFont.getSpriteBatch());
		incomeText = new FixedText("Gold:", font, textFontColor, StaticBitmapFont.getSpriteBatch());
		bestText = new FixedText("Best:", font, textFontColor, StaticBitmapFont.getSpriteBatch());
		newRecordText = new FixedText("!!NEW RECORD!!", font, Color.RED, StaticBitmapFont.getSpriteBatch());
		scoreText.setLocation(x + marginX, y + marginY * 4.5f);
		incomeText.setLocation(x + marginX, y + marginY * 4f);
		bestText.setLocation(x + marginX, y + marginY * 3.5f);
		newRecordText.setLocation(x + (w - newRecordText.getGlyphLayout().width) / 2, y + marginY * 5f);

		// Sprite
		loadRecordTexture();
		newRecordSprite = new Sprite(newRecordTexture);
		newRecordSprite.setBounds(x + w / 3 * 2, y + marginY * 3.5f, w / 2, w / 2);
		// income animation
		incomeAnimation = new IncomeAnimation(font, x + w / 2, y + marginY * 4f,-1, StaticBitmapFont.getSpriteBatch(), 2);
	}

	private void loadRecordTexture()
	{
		RecordLevel[] r = RecordLevel.values();
		for (int j = 1; j < r.length; j++)
		{
			if (FastClicker.getBest() < r[j].maxScore)
			{
				textureLoadedFromLevel = r[j];
				newRecordTexture = new Texture(Gdx.files.internal(r[j].path));
				break;
			}
		}
	}

	public void setScore(int score)
	{
		this.score = score;

		if (this.score > FastClicker.getBest())
		{
			showNewRecord = true;
			FastClicker.setBest(this.score);
			parent.getMainClass().getSwarmInterface().submitScore(19899, FastClicker.getBest());
			if (FastClicker.getBest() > textureLoadedFromLevel.maxScore)
			{
				newRecordTexture.dispose();
				loadRecordTexture();
				newRecordSprite.setTexture(newRecordTexture);
			}
		}
		income = incomeAnimation.moneyFromScoreFunction(score);
		incomeAnimation.setAnimation(FastClicker.getMoney(), income);
		FastClicker.setMoney(FastClicker.getMoney() + income);
	}

	@Override
	public void render()
	{
		renderBackground();
		if (showNewRecord)
		{
			if (FastClicker.getBest() >= RecordLevel.LEVEL_0.minScore)
			{// new record
				if (FastClicker.getBest() >= RecordLevel.LEVEL_1.minScore)
				{
					renderNewRecordImage();
				}
				StaticBitmapFont.begin();
				renderNewRecord();
			}
			else
			{
				StaticBitmapFont.begin();
			}
		}
		else
		{
			StaticBitmapFont.begin();
		}

		renderScoreText(score);
		renderMoneyText();
		renderBestScoreText(FastClicker.getBest());
		StaticBitmapFont.end();
		if (buttonRestartGame.checkButton())
		{
			parent.setPlayingState(PlayingStates.PLAYING);
			showNewRecord = false;
		}
		if (buttonExit.checkButton())
		{
			onExitPressed();
		}

	}

	private void onExitPressed()
	{
		parent.getMainClass().setCurrentSate(StateOfGame.MENU);
		showNewRecord = false;
	}

	private void renderNewRecordImage()
	{
		newRecordBatch.begin();
		newRecordSprite.draw(newRecordBatch);
		newRecordBatch.end();
	}

	private void renderNewRecord()
	{
		newRecordText.render();
	}

	@Override
	public void update()
	{// TODO:remove backspace
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE))
		{
			onExitPressed();
		}
		incomeAnimation.update();
	}

	@Override
	public void dispose()
	{
		buttonExit.dispose();
		buttonRestartGame.dispose();
	}

	private void renderScoreText(int number)
	{
		scoreText.render();
		renderScoreNextToText("" + number, scoreText.getBitmapFontCache());
	}

	private void renderMoneyText()
	{
		incomeText.render();
		renderScoreNextToText("" + incomeAnimation.moneyAtThisMomentOfAnimation, incomeText.getBitmapFontCache());
		incomeAnimation.render();
	}

	private void renderBestScoreText(int number)
	{
		bestText.render();
		renderScoreNextToText("" + number, bestText.getBitmapFontCache());
	}

	private void renderScoreNextToText(String text, BitmapFontCache cacheOfText)
	{
		renderText(text, x + w / 2, cacheOfText.getY());
	}

	private void renderText(String text, float x, float y)
	{

		font.draw(StaticBitmapFont.getSpriteBatch(), text, x, y);
	}

	private void renderBackground()
	{
		RectangleRenderer.begin();
		RectangleRenderer.setBounds(x, y, w, h);
		RectangleRenderer.setColor(1, 1, 1, 1f);
		RectangleRenderer.renderRect();
		RectangleRenderer.end();
	}

	private enum RecordLevel
	{
		/** Between 10-99 */
		LEVEL_0(10, 99, null), /** Between 100-149 */
		LEVEL_1(100, 149, "star.png"), /** Between 150-199 */
		LEVEL_2(150, 199, "newRecordRainbow.png"), /** more of equal 200 */
		LEVEL_3(200, Integer.MAX_VALUE, "newRecordRainbow.png");

		final int minScore;
		final int maxScore;
		final String path;

		private RecordLevel(int minScore, int maxScore, String pathToImage)
		{
			this.minScore = minScore;
			this.maxScore = maxScore;
			this.path = pathToImage;
		}
	}

}
