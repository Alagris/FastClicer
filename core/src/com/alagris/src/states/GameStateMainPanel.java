package com.alagris.src.states;

import com.alagris.src.ArrowsColumn;
import com.alagris.src.CellSelector;
import com.alagris.src.Drawable;
import com.alagris.src.FastClicker;
import com.alagris.src.HeartBar;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.StripesMatrix;
import com.alagris.src.StripesSet;
import com.alagris.src.TimeBar;
import com.alagris.src.Timer;
import com.alagris.src.TimerWithListener;
import com.alagris.src.states.GameState.PlayingStates;
import com.alagris.src.StripesSet.GameMode;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;

public class GameStateMainPanel implements Disposable, Drawable
{

	//////////////////////////////
	///////// VARIABLES //////////
	//////////////////////////////

	private StripesMatrix stripesMatrix;
	private StripesSet stripesSet;
	private TimeBar timeBar;
	private HeartBar heartBar;
	private HeartBar heartGoldBar;
	private CellSelector selector;
	private int heightOfTimeBar;
	private int lifesLeft, goldLifesLeft;
	private int level;
	private Timer timerForBlinks;
	private TimerWithListener timerAfterMistake, timerAfterCorrect;
	private double timeElapsed, timeForSolving;
	private boolean isGameOver;
	private boolean isBlinkingEnabled;
	private boolean isBlinkOn = false;
	private boolean hasCorrectAnswerJustBeenSelected = false;
	private ArrowsColumn arrowsColumn;
	private GameState parent;
	//////////////////////////////
	////// FINAL VARIABLES ///////
	//////////////////////////////
	private static final double blinkingTimeInSeconds = 1;
	private static final int blinksNumber = 10;
	private static final double timeOfSingleBlinkInSeconds = blinkingTimeInSeconds / blinksNumber;
	private static final double startTimeInSeconds = 3.5;
	
	//////////////////////////////
	/////// CONSTRUCTORS /////////
	//////////////////////////////

	public GameStateMainPanel( GameState parent)
	{
		this.parent = parent;
	}

	//////////////////////////////
	//////// GAME LOGIC //////////
	//////////////////////////////

	public void startGame()
	{
		goldLifesLeft = FastClicker.getGoldLifes();
		lifesLeft = FastClicker.getLifes();
		timeForSolving = 5;
		timeElapsed = 0;
		level = 1;
		isGameOver = false;
		changeSets(0.5);
		
	}

	private double getTimeForLevel(int level)
	{
		return startTimeInSeconds * timeFunction(level) / 2;
	}

	/**
	 * a = current time at the beginning = 5
	 */

	/**
	 * b = new time at the beginning = startTimeInSeconds
	 */
	/**
	 * c = new power base
	 */
	/**
	 * e = current power base = 0.99004985
	 */
	/**
	 * d = exponent value at the point that must be equal in previous and new
	 * equation = 100
	 */

	/**
	 * 
	 * c = (b/a)^(1/b)*e = (b/5)^(1/100)*0.99004985
	 */
	private static final double c = Math.pow((startTimeInSeconds / 5), (1 / 100)) * 0.99004985;

	private double timeFunction(int level)
	{
		return Math.pow(c, level);
	}

	@Override
	public void render()
	{
		timeBar.render((float) ((timeForSolving - timeElapsed) / timeForSolving));
		heartBar.render(lifesLeft);
		heartGoldBar.render(goldLifesLeft);
		stripesMatrix.render();
		if (isBlinkOn)
		{
			stripesMatrix.renderSinglePile(stripesSet.getErrorPileColumn(), stripesSet.getErrorPileRow());
		}
		StaticBitmapFont.begin();
		StaticBitmapFont.draw("" + level, (float) (FastClicker.WIDTH * 0.85), FastClicker.HEIGHT - heightOfTimeBar / 3);
		StaticBitmapFont.end();
		arrowsColumn.render();
	}

	@Override
	public void update()
	{
		if (isGameOver) return;
		if (hasCorrectAnswerJustBeenSelected)
		{
			timerAfterCorrect.update();
		}
		else if (isBlinkingEnabled)
		{
			timerAfterMistake.update();
			if (!timerAfterMistake.hasAlarmAlreadyFired())
			{
				blink();
			}
		}
		else
		{
			timeElapsed += Gdx.graphics.getDeltaTime();
			if (Gdx.input.isTouched(0) && Gdx.input.justTouched())
			{
				int c = selector.getSelectedColumn_roundDown(FastClicker.getTouchX());
				if (c > 0)
				{
					int clickedY = FastClicker.getTouchY();
					if (clickedY > stripesMatrix.getY())
					{
						int r = selector.getSelectedRow_roundDown(clickedY);
						if (r < stripesSet.getRows())
						{
							if (r == stripesSet.getErrorPileRow() && c == stripesSet.getErrorPileColumn())
							{
								// Next level
								nextLevel();
							}
							else
							{
								// One life is gone
								lifesLeft--;
								enableCorrectAnswerBlinking();
							}
						}
					}
				}
			}
			else
			{
				if (timeElapsed > timeForSolving)
				{
					// Game over
					timeIsUp();
				}
			}
		}
	}

	private void timeIsUp()
	{
		if (goldLifesLeft <= 0)
		{
			lifesLeft = 0;
		}
		else
		{
			goldLifesLeft--;
		}

		enableCorrectAnswerBlinking();
	}

	private void blink()
	{
		timerForBlinks.update();
		if (timerForBlinks.hasElapsedMoreThan(timeOfSingleBlinkInSeconds))
		{
			timerForBlinks.setElapsedTime(timerForBlinks.getElapsedTime() - timeOfSingleBlinkInSeconds);
			isBlinkOn = !isBlinkOn;
		}
	}

	private void enableCorrectAnswerBlinking()
	{
		isBlinkingEnabled = true;
		timerAfterMistake.resetTimer();
	}

	private void takeLife()
	{
		if (lifesLeft < 1)
		{
			gameOver();
		}
		else
		{
			setLevel(level);
		}
	}

	private void nextLevel()
	{
		stripesSet.repairErrorCell();
		hasCorrectAnswerJustBeenSelected = true;
		timerAfterCorrect.resetTimer();
	}
	
	private void changeSets(double feruency){
		switch(FastClicker.getGamemode()){
			case FastClicker.MODE_SIMPLE:
				stripesSet.changeSets_mode2(feruency);
				break;
			case FastClicker.MODE_MIXED:
				stripesSet.changeSets_mode3(feruency);
				break;
			case FastClicker.MODE_FULL:
				stripesSet.changeSets_mode1(feruency);
				break;
		}
	}
	
	private void setLevel(int level)
	{
		if (parent.isGoingToBePaused())
		{
			parent.setPlayingState(PlayingStates.PAUSED);
		}
		this.level = level;
		timeElapsed = 0;
		timeForSolving = getTimeForLevel(level);
		changeSets(0.5);
		switch (level) {
			case 2:
				parent.getMainClass().getSwarmInterface().unlock(23533);
				break;
			case 10:
				parent.getMainClass().getSwarmInterface().unlock(23535);
				break;
			case 100:
				parent.getMainClass().getSwarmInterface().unlock(23537);
				break;
			case 150:
				parent.getMainClass().getSwarmInterface().unlock(23539);
				break;
			case 180:
				parent.getMainClass().getSwarmInterface().unlock(23541);
				break;
			case 200:
				parent.getMainClass().getSwarmInterface().unlock(23547);
				break;
			case 220:
				parent.getMainClass().getSwarmInterface().unlock(23549);
				break;
			case 250:
				parent.getMainClass().getSwarmInterface().unlock(23543);
				break;
		}
	}

	@Override
	public void dispose()
	{
		timeBar.dispose();
		heartBar.dispose();
		heartGoldBar.dispose();
		stripesMatrix.dispose();
	}

	
	
	public void create()
	{
		stripesSet = new StripesSet(GameMode.EASY);
		heightOfTimeBar = (int) (FastClicker.HEIGHT * 0.06);
		stripesMatrix = new StripesMatrix(0, heightOfTimeBar, FastClicker.WIDTH,
				FastClicker.HEIGHT - heightOfTimeBar * 2, stripesSet);
		selector = stripesMatrix.getCellSelector();
		timeBar = new TimeBar(0, 0, FastClicker.WIDTH, heightOfTimeBar);
		heartBar = new HeartBar(heightOfTimeBar, 0, FastClicker.HEIGHT - heightOfTimeBar, FastClicker.WIDTH,
				heightOfTimeBar, 8, Color.RED);
		heartGoldBar = new HeartBar(heightOfTimeBar, 0, 0, FastClicker.WIDTH, heightOfTimeBar, 5,
				FastClicker.goldHeartColor);
		timerForBlinks = new Timer();
		timerAfterMistake = new TimerWithListener(0, blinkingTimeInSeconds)
		{
			@Override
			public void whenTimeIsUp(double time, double limit)
			{
				isBlinkingEnabled = false;
				isBlinkOn = false;
				takeLife();
			}
		};
		timerAfterCorrect = new TimerWithListener(0, blinkingTimeInSeconds / 2)
		{
			@Override
			public void whenTimeIsUp(double time, double limit)
			{
				hasCorrectAnswerJustBeenSelected = false;
				setLevel(level + 1);
			}
		};
		int pileWidth = (int) (stripesMatrix.getCellWidth() * 2);
		int arrowWidth = (int) (pileWidth / 4);
		int arrowsNumber = stripesSet.getRows();
		arrowsColumn = new ArrowsColumn((int) (pileWidth - arrowWidth / 2), (int) stripesMatrix.getY(), arrowWidth,
				(int) stripesMatrix.getHeight(), arrowsNumber);
	}

	//////////////////////////////
	//// GETTERS AND SETTERS /////
	//////////////////////////////


	public void setGameOver(boolean isGameOver)
	{
		this.isGameOver = isGameOver;
	}

	public void gameOver()
	{
		// If you change something here
		// you must also make the same changes
		// in GameState.finishGameSesionWithoutShowingAdv()
		isGameOver = true;
		parent.setPlayingState(PlayingStates.GAME_OVER);
	}

	public int getLevel()
	{
		return level;
	}

	public boolean isGameOver()
	{
		return isGameOver;
	}

}
