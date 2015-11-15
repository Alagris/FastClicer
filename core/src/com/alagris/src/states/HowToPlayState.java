package com.alagris.src.states;

import java.util.BitSet;

import com.alagris.src.ButtonSquare;
import com.alagris.src.CellSampler;
import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.TimerWithListener;
import com.alagris.src.states.GameStates.StateOfGame;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class HowToPlayState implements State
{

	private ButtonSquare next, back;
	private FastClicker mainClass;
	private int pages = 7;
	private int currentPageIndex = 0;
	private GlyphLayout glyphPage0, glyphPage1, glyphPage2, glyphPage3, glyphPage4, glyphPage5,glyphPage6;
	private int textX, textY;
	private CellSampler sampler;
	private BitSet correctBitSet, incorrectBitSet, universalBitSet;
	private SpriteBatch batchLeft, batchRight;
	private TimerWithListener timer;

	@Override
	public void createState(FastClicker mainClass)
	{
		this.mainClass = mainClass;
		float y = FastClicker.HEIGHT / 20;
		float width = FastClicker.WIDTH / 4;
		float height = y * 2;
		next = new ButtonSquare(FastClicker.WIDTH - width, y, width, height, RectangleRenderer.getWHITE_PIXEL(),
				"Next");
		back = new ButtonSquare(0, y, width, height, RectangleRenderer.getWHITE_PIXEL(), "Back");
		textX = FastClicker.WIDTH / 20;
		textY = (int) (FastClicker.HEIGHT * 0.95);
		int textWidth = FastClicker.WIDTH - textX * 2;
		String text0 = "1. Cells on the LEFT indicate pattern.\n\n2. Cells on the RIGHT follow the pattern. "
				+ "\n\n3. Cell that doesn't follow the pattern is incorrect and has to be fixed (click it).";
		String text1 = "4. If you click on correct cell you will lose a "
				+ "life.\n\n5. Game will be over, if time is up (unless you "
				+ "have gold hearts) or you lose all lifes.\n\n6."
				+ " Time elapses faster as you get to higher levels.";
		String text2 = "Correct examples:\n\nCells of one color give cells of the same color. ";
		String text3 = "Incorrect examples:\n\nThere is always only one incorrect cell (on the right)";
		String text4 = "Universal cells:\n\nMixed cells give any kind of cells and are always correct";
		String text5 = "Important notes:\n\nPress back button and game will pause when you pass current level"
				+ ".\n\nGold hearts let you play once again the same level when time is up. Go to shop to buy them.";
		String text6 = "About ads:\n\nEverytime you see advertisement we get a few cents for pizza and you get 50 gold. "
				+ "Fair deal, isn't it? Of course if you really don't want to give us some pizza you could disable "
				+ "ads... FOR FREE (go to shop). PS. you don't have to click the ad to get gold.";
		glyphPage0 = new GlyphLayout(StaticBitmapFont.getBitmapFont(), text0,
				StaticBitmapFont.getBitmapFont().getColor(), textWidth, Align.left, true);
		glyphPage1 = new GlyphLayout(StaticBitmapFont.getBitmapFont(), text1,
				StaticBitmapFont.getBitmapFont().getColor(), textWidth, Align.left, true);
		glyphPage2 = new GlyphLayout(StaticBitmapFont.getBitmapFont(), text2,
				StaticBitmapFont.getBitmapFont().getColor(), textWidth, Align.left, true);
		glyphPage3 = new GlyphLayout(StaticBitmapFont.getBitmapFont(), text3,
				StaticBitmapFont.getBitmapFont().getColor(), textWidth, Align.left, true);
		glyphPage4 = new GlyphLayout(StaticBitmapFont.getBitmapFont(), text4,
				StaticBitmapFont.getBitmapFont().getColor(), textWidth, Align.left, true);
		glyphPage5 = new GlyphLayout(StaticBitmapFont.getBitmapFont(), text5,
				StaticBitmapFont.getBitmapFont().getColor(), textWidth, Align.left, true);
		glyphPage6 = new GlyphLayout(StaticBitmapFont.getBitmapFont(), text6,
				StaticBitmapFont.getBitmapFont().getColor(), textWidth, Align.left, true);
		correctBitSet = new BitSet();
		setBitSet(correctBitSet, true, true, true, true);
		incorrectBitSet = new BitSet();
		setBitSet(incorrectBitSet, true, true, true, false);
		universalBitSet = new BitSet();
		setBitSet(universalBitSet, true, false, true, false);
		sampler = new CellSampler(FastClicker.WIDTH / 4, FastClicker.WIDTH / 4, 0, FastClicker.HEIGHT / 2);

		batchLeft = new SpriteBatch();
		batchRight = new SpriteBatch();

		timer = new TimerWithListener(0, 1)
		{
			int flipCount = 0;

			@Override
			public void whenTimeIsUp(double time, double limit)
			{
				switch (currentPageIndex) {
					case 2:
						correctBitSet.flip(0, 4);
						break;
					case 3:
						incorrectBitSet.flip(0, 4);
						break;
					case 4:
						flipCount++;

						switch (flipCount) {
							case 0:
								universalBitSet.flip(0, 4);
								break;
							case 1:
								universalBitSet.flip(2, 4);
								break;
							case 2:
								universalBitSet.flip(0, 2);
								break;
							case 3:
								universalBitSet.flip(2, 3);
								break;
							case 4:
								universalBitSet.flip(2, 4);
								break;
							case 5:
								universalBitSet.flip(3, 4);
								flipCount = -1;
								break;
						}
						break;
				}
				timer.resetTimer();
			}
		};
	}

	private void setBitSet(BitSet s, boolean... data)
	{
		for (int i = 0; i < data.length; i++)
		{
			s.set(i, data[i]);
		}
	}

	@Override
	public void render()
	{
		if (next.checkButton())
		{
			if (currentPageIndex >= pages - 1)
			{
				mainClass.setCurrentSate(StateOfGame.GAME);
			}
			else
			{
				if (currentPageIndex == pages - 2)
				{
					next.setText("Play");
				}
				currentPageIndex++;
			}
		}
		if (back.checkButton())
		{
			if (currentPageIndex < 1)
			{
				mainClass.setCurrentSate(StateOfGame.MENU);
			}
			else
			{
				if (currentPageIndex == pages - 1)
				{
					next.setText("Next");
				}
				currentPageIndex--;
			}
		}
		renderPage();
	}

	private void renderPage()
	{
		switch (currentPageIndex) {
			case 0:
				renderGlyph(glyphPage0);
				break;
			case 1:
				renderGlyph(glyphPage1);
				break;
			case 2:
				renderGlyph(glyphPage2);
				sampler.render(batchLeft, batchRight, correctBitSet);
				break;
			case 3:
				renderGlyph(glyphPage3);
				sampler.render(batchLeft, batchRight, incorrectBitSet);
				break;
			case 4:
				renderGlyph(glyphPage4);
				sampler.render(batchLeft, batchRight, universalBitSet);
				break;
			case 5:
				renderGlyph(glyphPage5);
				break;
			case 6:
				renderGlyph(glyphPage6);
				break;
				
		}
	}

	private void renderGlyph(GlyphLayout l)
	{
		StaticBitmapFont.begin();
		StaticBitmapFont.draw(l, textX, textY);
		StaticBitmapFont.end();
	}

	@Override
	public void update()
	{
		if (currentPageIndex != 0)
		{
			timer.update();
		}
	}

	@Override
	public void dispose()
	{
		batchLeft.dispose();
		batchRight.dispose();
		back.dispose();
		next.dispose();
	}

	@Override
	public void stateResumed()
	{
		if (currentPageIndex == pages - 1)
		{
			next.setText("Next");
		}
		currentPageIndex = 0;
	}

	@Override
	public void statePaused()
	{

	}

}
