package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.FastClicker;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.states.GameStates.StateOfGame;

public class SwarmMenuState implements State
{

	private ButtonSquare buttonLeaderboards, buttonAchievements, buttonCloud, buttonBack;
	private FastClicker mainClass;

	@Override
	public void render()
	{
		if (buttonBack.checkButton())
		{
			mainClass.setCurrentSate(StateOfGame.MENU);
		}
		if (buttonAchievements.checkButton())
		{
			mainClass.getSwarmInterface().showAchievements();
		}
		if (buttonLeaderboards.checkButton())
		{
			mainClass.getSwarmInterface().showLeaderboard("CgkIvtaPzJQMEAIQDQ");
		}
		if (buttonCloud.checkButton())
		{
			mainClass.setCurrentSate(StateOfGame.MENU_CLOUD);
		}
	}

	@Override
	public void update()
	{

	}

	@Override
	public void dispose()
	{
		buttonBack.dispose();
		buttonCloud.dispose();
		buttonAchievements.dispose();
		buttonLeaderboards.dispose();
	}

	@Override
	public void createState(FastClicker mainClass)
	{
		this.mainClass = mainClass;
		float buttonWidth = FastClicker.WIDTH * 0.8f;
		float buttonX = FastClicker.WIDTH * 0.1f;
		float buttonHeight = FastClicker.HEIGHT / 9f;
		float buttonHeightHalf = buttonHeight / 2;
		buttonAchievements = new ButtonSquare(buttonX, buttonHeight * 5, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Achievements");
		buttonLeaderboards = new ButtonSquare(buttonX, buttonHeight * 3 + buttonHeightHalf, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Records");
		buttonCloud = new ButtonSquare(buttonX, buttonHeight * 2, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Cloud saves");
		buttonBack = new ButtonSquare(buttonX, buttonHeightHalf, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Back");
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
