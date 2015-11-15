package com.alagris.src.states;

import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.specific.AdMobEventListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class GameState implements State
{

	public enum PlayingStates
	{
		PLAYING, GAME_OVER, ADVERTISEMENT, PAUSED
	}

	private FastClicker mainClass;
	public static final int numberOfLostGameToDisplayAdv = 3;
	private PlayingStates playingState = PlayingStates.PLAYING;
	private GameStateMainPanel mainPanel;
	private GameStateGaveOverPanel gameOverPanel;
	private GameStatePausedPanel pausedPanel;
	private int gamesLostSoFar = 0;
	private boolean isGoingToBePaused = false;

	public GameState( int lifes)
	{
		mainPanel = new GameStateMainPanel( this);
		int margin = (int) ((FastClicker.WIDTH > FastClicker.HEIGHT ? FastClicker.HEIGHT : FastClicker.WIDTH) * 0.15f);
		gameOverPanel = new GameStateGaveOverPanel(margin, margin * 2, FastClicker.WIDTH - margin * 2,
				FastClicker.HEIGHT - margin * 3, this);
		pausedPanel = new GameStatePausedPanel(0, 0, FastClicker.WIDTH, FastClicker.HEIGHT, this);
	}

	@Override
	public void stateResumed()
	{
		Gdx.input.setCatchBackKey(true);
		mainPanel.startGame();
	}

	@Override
	public void statePaused()
	{
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void render()
	{
		switch (playingState) {
			case GAME_OVER:
				mainPanel.render();
				renderBlackGlassOverBackground();
				gameOverPanel.render();
				break;
			case ADVERTISEMENT:
				mainPanel.render();
				renderBlackGlassOverBackground();
				break;
			case PLAYING:
				mainPanel.render();
				break;
			case PAUSED:
				pausedPanel.render();
				break;

		}
	}

	@Override
	public void update()
	{

		switch (playingState) {
			case PLAYING:
				// TODO:remove backspace
				if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE))
				{
					setGoingToBePaused(true);
				}
				mainPanel.update();
				break;
			case GAME_OVER:
				gameOverPanel.update();
				break;
			case ADVERTISEMENT:
				// TODO:remove backspace
				if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE))
				{
					setPlayingStateWithoutPrompt(PlayingStates.GAME_OVER);
				}
				break;
			case PAUSED:
				pausedPanel.update();
				break;

		}
	}

	private void renderBlackGlassOverBackground()
	{
		RectangleRenderer.begin();
		RectangleRenderer.setBounds(0, 0, FastClicker.WIDTH, FastClicker.HEIGHT);
		RectangleRenderer.setColor(0, 0, 0, 0.5f);
		RectangleRenderer.renderRect();
		RectangleRenderer.end();
	}

	@Override
	public void dispose()
	{
		mainPanel.dispose();
		gameOverPanel.dispose();
		pausedPanel.dispose();

	}

	@Override
	public void createState(FastClicker mainClass)
	{
		mainPanel.create();
		this.mainClass = mainClass;
		mainClass.getAdMobInterface().getAdMobListener().setOnAdClosedListener(onAdClosedListener);
		mainClass.getAdMobInterface().getAdMobListener().setOnAdLoadedListener(onAdLoadedListener);
	}

	public PlayingStates getPlayingState()
	{
		return playingState;
	}

	private AdMobEventListener onAdClosedListener = new AdMobEventListener()
	{
		@Override
		public void evenOccured()
		{
			// TODO:Remove this
			System.out.println("//////Ad closed");
			mainClass.getAdMobInterface().requestNewInterstitial();
			setPlayingStateWithoutPrompt(PlayingStates.GAME_OVER);
		}
	};
	private AdMobEventListener onAdLoadedListener = new AdMobEventListener()
	{
		@Override
		public void evenOccured()
		{
			// TODO:Remove this
			System.out.println("///////AdMob finally loaded and ready to show");
		}
	};

	public void setPlayingState(PlayingStates playingState)
	{
		this.playingState = playingState;
		switch (playingState) {
			case GAME_OVER:
				gameOverPanel.setScore(mainPanel.getLevel());
				gamesLostSoFar++;
				if (areAdsEnabled() && isItTimeForAdv() && mainClass.getSwarmInterface().isOnline()
						&& mainClass.getAdMobInterface().isLoaded())
				{
					mainClass.getAdMobInterface().showAd();
					setPlayingStateWithoutPrompt(PlayingStates.ADVERTISEMENT);
					FastClicker.addMoneyToReclaim(mainPanel.getLevel());
					gamesLostSoFar = 0;
				}
				break;
			case ADVERTISEMENT:
				break;
			case PLAYING:
				mainPanel.startGame();
				break;
			case PAUSED:
				setGoingToBePaused(false);
				break;
		}
	}

	private boolean isItTimeForAdv()
	{
		return gamesLostSoFar >= FastClicker.getNumberOfLostGamesToShowAdv();
	}

	private boolean areAdsEnabled()
	{
		return FastClicker.getNumberOfLostGamesToShowAdv() < ShopStateGoldFree.minAdsFrequency;
	}

	public void setPlayingStateWithoutPrompt(PlayingStates playingState)
	{
		this.playingState = playingState;
	}

	public void resetCountOfLostGames()
	{
		gamesLostSoFar = 0;
	}

	public FastClicker getMainClass()
	{
		return mainClass;
	}

	public boolean isGoingToBePaused()
	{
		return isGoingToBePaused;
	}

	public void setGoingToBePaused(boolean isGoingToBePaused)
	{
		this.isGoingToBePaused = isGoingToBePaused;
	}

	public void finishGameSesion()
	{
		mainPanel.gameOver();
	}

	public void finishGameSesionWithoutShowingAdv()
	{
		mainPanel.setGameOver(true);
		gameOverPanel.setScore(mainPanel.getLevel());
		gamesLostSoFar++;
		setPlayingStateWithoutPrompt(PlayingStates.GAME_OVER);
	}

}
