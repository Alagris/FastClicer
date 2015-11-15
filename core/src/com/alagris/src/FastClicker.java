package com.alagris.src;

import java.io.IOException;

import com.alagris.src.specific.AdMobEventListenerInteger;
import com.alagris.src.specific.AdMobInterface;
import com.alagris.src.specific.SwarmInterface;
import com.alagris.src.states.GameStates;
import com.alagris.src.states.GameStates.StateOfGame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public final class FastClicker extends ApplicationAdapter
{

	//////////////////////////////
	///////// VARIABLES //////////
	//////////////////////////////

	public static int WIDTH;
	public static int HEIGHT;
	private GameStates states;
	private static int touchX, touchY;
	private GlyphLayout errorGlyphs = null;
	private static int money, moneyToReclaim, numberOfLostGamesToShowAdv;
	private static int lifes, goldLifes, easyModeStartingLevel, mediumModeStartingLevel, hardModeStartingLevel;
	private static int best;
	private static FileHandle settingsFile;
	private static boolean shouldSave = false;
	private SwarmInterface swarmInterface;
	private final AdMobInterface adMobInterface;
	private TimerWithListener connectionTimer;
	private boolean adFailedToLoad = false;
	public static final Color goldHeartColor = Color.YELLOW;
	public static final byte MODE_SIMPLE = 0, MODE_MIXED = 1, MODE_FULL = 2;
	public static final double levelToPower_easy = 1.2, levelToPower_medium = 1.3, levelToPower_hard = 1.4;
	public static double levelToPower = levelToPower_easy;
	private static byte gamemode = MODE_MIXED;
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

	private static void saveData()
	{
		saveData(best, money, lifes, goldLifes, moneyToReclaim, numberOfLostGamesToShowAdv, gamemode,
				easyModeStartingLevel, mediumModeStartingLevel, hardModeStartingLevel);
	}

	private static void saveData(Object... data)
	{
		if (shouldSave)
		{
			String s = "";
			for (Object object : data)
			{
				s += object + "m";
			}
			settingsFile.writeString(s, false);
			shouldSave = false;
		}
	}

	private static void readSavedData()
	{
		if (settingsFile == null)
		{
			settingsFile = Gdx.files.local("settings");
		}
		if (!settingsFile.exists())
		{
			try
			{
				settingsFile.file().createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			setDefaultSettings();
		}
		else
		{
			String[] strings = settingsFile.readString().split("m");
			if (strings.length == 10)
			{
				try
				{
					best = Integer.parseInt(strings[0]);
					money = Integer.parseInt(strings[1]);
					lifes = Integer.parseInt(strings[2]);
					goldLifes = Integer.parseInt(strings[3]);
					moneyToReclaim = Integer.parseInt(strings[4]);
					numberOfLostGamesToShowAdv = Integer.parseInt(strings[5]);
					setGamemode(Byte.parseByte(strings[6]));
					easyModeStartingLevel = Integer.parseInt(strings[7]);
					mediumModeStartingLevel = Integer.parseInt(strings[8]);
					hardModeStartingLevel = Integer.parseInt(strings[9]);
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
					setDefaultSettings();
				}
			}
			else
			{
				setDefaultSettings();
			}
		}
	}

	private static void setDefaultSettings()
	{
		shouldSave = true;
		best = 0;
		money = 0;
		lifes = 1;
		goldLifes = 0;
		moneyToReclaim = 0;
		numberOfLostGamesToShowAdv = 3;
		setGamemode(MODE_SIMPLE);
		easyModeStartingLevel = 0;
		mediumModeStartingLevel = 0;
		hardModeStartingLevel = 0;
	}

	public void setCurrentSate(StateOfGame state)
	{
		states.setCurrentState(state);
	}

	@Override
	public void create()
	{
		readSavedData();
		Gdx.gl.glClearColor(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, 1);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		RectangleRenderer.initialize();
		StaticBitmapFont.initialize(WIDTH, HEIGHT);
		states = new GameStates(this, StateOfGame.MENU);
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

	public static int getTouchX()
	{
		return touchX;
	}

	public static int getTouchY()
	{
		return touchY;
	}

	public void setError(String string)
	{
		if (string == null)
		{
			errorGlyphs = null;
		}
		else
		{
			BitmapFont f = StaticBitmapFont.buildNewBitmapFontObject();
			f.setColor(Color.RED);
			errorGlyphs = new GlyphLayout(f, string);
		}
	}

	public static int getMoney()
	{
		return money;
	}

	public static void setMoney(int money)
	{
		shouldSave = true;
		FastClicker.money = money;
	}

	public static int getLifes()
	{
		return lifes;
	}

	public static void setLifes(int lifes)
	{
		shouldSave = true;
		FastClicker.lifes = lifes;
	}

	public static int getBest()
	{
		return best;
	}

	public static void setBest(int best)
	{
		shouldSave = true;
		FastClicker.best = best;
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

	public static int getGoldLifes()
	{
		return goldLifes;
	}

	public static void setGoldLifes(int goldLifes)
	{
		shouldSave = true;
		FastClicker.goldLifes = goldLifes;
	}

	public static int getMoneyToReclaim()
	{
		return moneyToReclaim;
	}

	public static void setMoneyToReclaim(int moneyToReclaim)
	{
		shouldSave = true;
		FastClicker.moneyToReclaim = moneyToReclaim;
	}

	public static int getNumberOfLostGamesToShowAdv()
	{
		return numberOfLostGamesToShowAdv;
	}

	public static void setNumberOfLostGamesToShowAdv(int numberOfLostGamesToShowAdv)
	{
		FastClicker.numberOfLostGamesToShowAdv = numberOfLostGamesToShowAdv;
	}

	public static void addMoneyToReclaim(int score)
	{
		moneyToReclaim += 50;
	}

	public AdMobInterface getAdMobInterface()
	{
		return adMobInterface;
	}

	public static int getEasyModeStartingLevel()
	{
		return easyModeStartingLevel;
	}

	public static void setEasyModeStartingLevel(int easyModeStartingLevel)
	{
		FastClicker.easyModeStartingLevel = easyModeStartingLevel;
	}

	public static int getMediumModeStartingLevel()
	{
		return mediumModeStartingLevel;
	}

	public static void setMediumModeStartingLevel(int mediumModeStartingLevel)
	{
		FastClicker.mediumModeStartingLevel = mediumModeStartingLevel;
	}

	public static int getHardModeStartingLevel()
	{
		return hardModeStartingLevel;
	}

	public static void setHardModeStartingLevel(int hardModeStartingLevel)
	{
		FastClicker.hardModeStartingLevel = hardModeStartingLevel;
	}

	public static byte getGamemode()
	{
		return gamemode;
	}

	public static void setGamemode(byte gamemode)
	{
		FastClicker.gamemode = gamemode;

		switch (FastClicker.gamemode) {
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
