package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.states.GameStates.StateOfGame;

public class MenuState implements State
{

	private ButtonSquare buttonPlay, buttonHowToPlay, buttonSwarm, buttonShop;
	private FastClicker mainClass;

	@Override
	public void render()
	{
		if (buttonPlay.checkButton())
		{
			mainClass.setCurrentSate(StateOfGame.GAME);
		}
		if (buttonHowToPlay.checkButton())
		{
			mainClass.getSwarmInterface().unlock(23531);
			mainClass.setCurrentSate(StateOfGame.HOW_TO_PLAY);
		}
		if (buttonSwarm.checkButton())
		{
			mainClass.setCurrentSate(StateOfGame.MENU_SWARM);
		}
		if (buttonShop.checkButton())
		{
			mainClass.setCurrentSate(StateOfGame.SHOP);
		}
	}

	@Override
	public void update()
	{

	}

	@Override
	public void dispose()
	{
		buttonShop.dispose();
		buttonPlay.dispose();
		buttonHowToPlay.dispose();
		buttonSwarm.dispose();
	}

	@Override
	public void createState(FastClicker mainClass)
	{
		this.mainClass = mainClass;
		float buttonWidth = FastClicker.WIDTH * 0.8f;
		float buttonX = FastClicker.WIDTH * 0.1f;
		float buttonHeight = FastClicker.HEIGHT / 9f;
		float buttonHeightHalf = buttonHeight / 2;
		buttonPlay = new ButtonSquare(buttonX, buttonHeight * 5, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Play");
		buttonHowToPlay = new ButtonSquare(buttonX, buttonHeight * 3 + buttonHeightHalf, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "How to play");
		buttonShop = new ButtonSquare(buttonX, buttonHeight * 2, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Shop");
		buttonSwarm = new ButtonSquare(buttonX, buttonHeightHalf, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Social");
	}

	@Override
	public void stateResumed()
	{
	}

	@Override
	public void statePaused()
	{

	}

}
