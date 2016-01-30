package com.alagris.src.states;

import com.alagris.src.ButtonSquare;
import com.alagris.src.Data;
import com.alagris.src.FastClicker;
import com.alagris.src.LeftText;
import com.alagris.src.RectangleRenderer;
import com.alagris.src.StaticBitmapFont;
import com.alagris.src.TextBounds;
import com.alagris.src.states.GameStates.StateOfGame;

public class CloudMenuState implements State
{

	private LeftText backupInfo;
	private TextBounds backupInfoBounds;
	private ButtonSquare buttonDownload, buttonUpload, buttonBack;
	private FastClicker mainClass;
	private Data backup;
	private boolean isReadyToDownload = false;

	@Override
	public void render()
	{
		if (buttonBack.checkButton())
		{
			mainClass.setCurrentSate(StateOfGame.MENU_SWARM);
		}

		if (buttonDownload.checkButton())
		{
			if (isReadyToDownload) mainClass.setData(backup);
		}
		if (buttonUpload.checkButton())
		{
			Thread uploading = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					mainClass.uploadDataToCloud();
				}
			});
			uploading.start();
		}
		backupInfo.begin();
		backupInfo.render();
		backupInfo.end();
	}

	@Override
	public void update()
	{

	}

	@Override
	public void dispose()
	{
		buttonBack.dispose();
		buttonUpload.dispose();
		buttonDownload.dispose();
	}

	@Override
	public void createState(FastClicker mainClass)
	{
		this.mainClass = mainClass;
		float buttonWidth = FastClicker.WIDTH * 0.8f;
		float buttonX = FastClicker.WIDTH * 0.1f;
		float buttonHeight = FastClicker.HEIGHT / 9f;
		float buttonHeightHalf = buttonHeight / 2;
		buttonDownload = new ButtonSquare(buttonX, buttonHeight * 3 + buttonHeightHalf, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Load backup");
		buttonUpload = new ButtonSquare(buttonX, buttonHeight * 2, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Make backup");
		buttonBack = new ButtonSquare(buttonX, buttonHeightHalf, buttonWidth, buttonHeight,
				RectangleRenderer.getWHITE_PIXEL(), "Back");
		backupInfo = new LeftText(StaticBitmapFont.getBitmapFont(), StaticBitmapFont.getSpriteBatch());

		/* the same value is in other states */
		float marginY = FastClicker.HEIGHT / 20;
		backupInfoBounds = new TextBounds(0, FastClicker.HEIGHT - marginY, FastClicker.WIDTH);

	}

	private void prepareBackUp()
	{
		if (backup == null)
		{
			backup = new Data();
		}
	}

	@Override
	public void stateResumed()
	{
		if (mainClass.getSwarmInterface().isOnline())
		{
			prepareBackUp();
			backupInfo.setText("Connecting to cloud. \nPlease wait...", backupInfoBounds.getX(),
					backupInfoBounds.getY(), backupInfoBounds.getWidth());
			Thread downloading = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					if (backup.downloadDataFromCloud(mainClass.getSwarmInterface()))
					{
						backupInfo.setText(
								"Backup content:\ngold=" + backup.money + "\nhearts=" + backup.lifes + "\ngold hearts="
										+ backup.goldLifes,
								backupInfoBounds.getX(), backupInfoBounds.getY(), backupInfoBounds.getWidth());
						isReadyToDownload = true;
					}
					else
					{
						backupInfo.setText("Encountered problem \nwith cloud data! \nTry again later.",
								backupInfoBounds.getX(), backupInfoBounds.getY(), backupInfoBounds.getWidth());
					}
				}
			});
			downloading.run();
		}
		else
		{
			backupInfo.setText("Could not connect to \ncloud yet!", backupInfoBounds.getX(), backupInfoBounds.getY(),
					backupInfoBounds.getWidth());
		}

	}

	@Override
	public void statePaused()
	{

	}

}
