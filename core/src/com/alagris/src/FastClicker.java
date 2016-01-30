package com.alagris.src;

import com.alagris.src.specific.AdMobEventListenerInteger;
import com.alagris.src.specific.AdMobInterface;
import com.alagris.src.specific.SwarmInterface;
import com.alagris.src.states.GameStates;
import com.alagris.src.states.GameStates.StateOfGame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public final class FastClicker extends ApplicationAdapter
{

	/*
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! BEFORE DISTRIBUTING
	 * THE GAME!!!!!! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * 
	 * 
	 * 
	 */

	//////////////////////////////
	///////// VARIABLES //////////
	//////////////////////////////

	public static int WIDTH;
	public static int HEIGHT;
	private GameStates states;
	private static int touchX, touchY;
	private GlyphLayout errorGlyphs = null;
	private static final Data data = new Data();
	public static final boolean IS_DEBUG_MODE = true;
	private static boolean shouldSave = false;
	private SwarmInterface swarmInterface;
	private final AdMobInterface adMobInterface;
	private TimerWithListener connectionTimer;
	private boolean adFailedToLoad = false;
	public static final Color goldHeartColor = Color.YELLOW;
	public static final byte MODE_SIMPLE = 0, MODE_MIXED = 1, MODE_FULL = 2;
	/** income function is Math.pow(score,levelToPower) */
	public static final double levelToPower_easy = 1.2, levelToPower_medium = 1.3, levelToPower_hard = 1.4;
	public static double levelToPower = levelToPower_easy;
	public static final int maxRedHeartPrice = moneyFromScoreFunction(150);
	public static final int maxGoldHeartPrice = moneyFromScoreFunction(200);

	public static int moneyFromScoreFunction(int score)
	{
		return (int) Math.pow(score, FastClicker.levelToPower);
	}

	public static int goldHeartPriceFunction(long heartNumber)
	{
		return (int) (1000 * Math.pow(heartNumber, 2));
	}

	public static int redHeartPriceFunction(long heartNumber)
	{
		return (int) (100 * Math.pow(heartNumber - 1, 3));
	}
	//////////////////////////////
	//////// GAME LOGIC //////////
	//////////////////////////////

	public FastClicker(SwarmInterface swarmInterface, final AdMobInterface adMobGDX)
	{
		this.setSwarmInterface(swarmInterface);
		this.adMobInterface = adMobGDX;
		connectionTimer = new TimerWithListener(20)
		{
			@Override
			public void whenTimeIsUp(double time, double limit)
			{
				if (FastClicker.this.swarmInterface.isOnline())
				{
					adMobInterface.requestNewInterstitial();
					adFailedToLoad = false;
				}
				else
				{
					resetTimer();
				}
			}
		};
		adMobInterface.getAdMobListener().setOnAdFailedToLoadListener(onAdFailedToLoadListener);
	}

	private AdMobEventListenerInteger onAdFailedToLoadListener = new AdMobEventListenerInteger()
	{
		@Override
		public void evenOccured(int i)
		{
			if (i == 2)
			{
				connectionTimer.resetTimer();
				adFailedToLoad = true;
			}
		}
	};

	public void setCurrentSate(StateOfGame state)
	{
		states.setCurrentState(state);
	}

	@Override
	public void create()
	{
		readData();
		Gdx.gl.glClearColor(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, 1);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		RectangleRenderer.initialize();
		StaticBitmapFont.initialize(WIDTH, HEIGHT);
		states = new GameStates(this, StateOfGame.MENU);
	}

	private void readData()
	{
		data.readDataLocally();

		// this sets variables that depend on game mode
		setGamemode(getGamemode());
		shouldSave = false;
	}

	@Override
	public void render()
	{

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateInputs();
		if (adFailedToLoad) connectionTimer.update();
		states.update();
		states.render();
		renderError();
	}

	private void renderError()
	{
		if (errorGlyphs != null)
		{
			StaticBitmapFont.begin();
			StaticBitmapFont.draw(errorGlyphs, 0, errorGlyphs.height);
			StaticBitmapFont.end();
		}
	}

	private static void updateInputs()
	{
		touchX = Gdx.input.getX(0);
		touchY = HEIGHT - Gdx.input.getY(0);
	}

	@Override
	public void pause()
	{
		saveData();
		states.pause();
		super.pause();
	}

	@Override
	public void resume()
	{
		states.resume();
		super.resume();
	}

	@Override
	public void dispose()
	{
		saveData();
		states.dispose();
		StaticBitmapFont.dispose();
		RectangleRenderer.dispose();
		super.dispose();
	}

	private void saveData()
	{
		if (shouldSave)
		{
			shouldSave = false;
			data.writeDataLocally();
		}
	}

	public void uploadDataToCloud()
	{
		data.uploadDataToCloud(getSwarmInterface());
	}

	public void setData(Data d)
	{
		for (int i = 0; i < data.array.length; i++)
			data.array[i][0] = d.array[i][0];
	}

	public static int getTouchX()
	{
		return touchX;
	}

	public static int getTouchY()
	{
		return touchY;
	}

	public static long getMoney()
	{
		return data.money[0];
	}

	public static void setMoney(long money)
	{
		shouldSave = true;
		data.money[0] = money;
	}

	public static long getLifes()
	{
		return data.lifes[0];
	}

	public static void setLifes(long lifes)
	{
		shouldSave = true;
		data.lifes[0] = lifes;
	}

	public static long getBest()
	{
		return data.best[0];
	}

	public static void setBest(long best)
	{
		shouldSave = true;
		data.best[0] = best;
	}

	public SwarmInterface getSwarmInterface()
	{
		return swarmInterface;
	}

	private void setSwarmInterface(SwarmInterface swarmInterface)
	{
		shouldSave = true;
		this.swarmInterface = swarmInterface;
	}

	public static long getGoldLifes()
	{
		return data.goldLifes[0];
	}

	public static void setGoldLifes(long goldLifes)
	{
		shouldSave = true;
		data.goldLifes[0] = goldLifes;
	}

	public static long getMoneyToReclaim()
	{
		return data.moneyToReclaim[0];
	}

	public static void setMoneyToReclaim(long moneyToReclaim)
	{
		shouldSave = true;
		data.moneyToReclaim[0] = moneyToReclaim;
	}

	public static long getNumberOfLostGamesToShowAdv()
	{
		return data.numberOfLostGamesToShowAdv[0];
	}

	public static void setNumberOfLostGamesToShowAdv(long numberOfLostGamesToShowAdv)
	{
		shouldSave = true;
		data.numberOfLostGamesToShowAdv[0] = numberOfLostGamesToShowAdv;
	}

	public static void addMoneyToReclaim(int score)
	{
		shouldSave = true;
		data.moneyToReclaim[0] += 50;
	}

	public AdMobInterface getAdMobInterface()
	{
		return adMobInterface;
	}

	public static byte getGamemode()
	{
		return (byte) data.gamemode[0];
	}

	public static void setGamemode(byte gamemode)
	{
		shouldSave = true;
		data.gamemode[0] = gamemode;

		switch (getGamemode()) {
			case FastClicker.MODE_SIMPLE:
				levelToPower = levelToPower_easy;
				break;
			case FastClicker.MODE_MIXED:
				levelToPower = levelToPower_medium;
				break;
			case FastClicker.MODE_FULL:
				levelToPower = levelToPower_hard;
				break;
		}
	}

}
