package com.alagris.src.states;

import com.alagris.src.Drawable;
import com.alagris.src.FastClicker;
import com.alagris.src.states.GameState.PlayingStates;
import com.badlogic.gdx.utils.Disposable;

public class GameStates implements Disposable, Drawable
{

	/**
	 * !!!Before publishing!!!
	 */
	/**
	 * -make sure that file settings contains text: "-1"
	 */
	public enum StateOfGame
	{
		GAME(0), MENU(1), HOW_TO_PLAY(2), SHOP(3), MENU_SWARM(4);
		int index;

		private StateOfGame(int index)
		{
			this.index = index;
		}

	}

	private int currentState;

	private State swarm = new SwarmMenuState();
	private GameState game = new GameState(3);
	private State menu = new MenuState();
	private State howToPlay = new HowToPlayState();
	private State shop = new ShopState();

	private State[] states = { game, menu, howToPlay, shop ,swarm};

	public GameStates(FastClicker mainClass, StateOfGame stateAtTheBeginning)
	{
		for (State s : states)
		{
			s.createState(mainClass);
		}
		currentState = stateAtTheBeginning.index;
		states[currentState].stateResumed();
	}

	public void pause()
	{
	}

	public void resume()
	{
	}

	@Override
	public void dispose()
	{
		for (State s : states)
		{
			s.dispose();
		}
	}

	@Override
	public void render()
	{
		states[currentState].render();
	}

	@Override
	public void update()
	{
		states[currentState].update();
	}

	public void setCurrentState(StateOfGame s)
	{
		states[currentState].statePaused();
		this.currentState = s.index;
		states[currentState].stateResumed();
	}

	public void setPlayingState(PlayingStates playingState)
	{
		game.setPlayingState(playingState);
	}

}
