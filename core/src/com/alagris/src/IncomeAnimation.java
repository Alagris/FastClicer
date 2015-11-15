package com.alagris.src;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IncomeAnimation implements Drawable
{
	private AnimatedText animatedText;
	private int moneyAtTheBeginningOfAnimation, income;
	public int moneyAtThisMomentOfAnimation;

	public IncomeAnimation(BitmapFont font1, float x1, float y1,float width, SpriteBatch batch1, float animationDurationTime1)
	{
		animatedText = new AnimatedText(font1, x1, y1,width, batch1, animationDurationTime1);
	}

	public int moneyFromScoreFunction(int score)
	{
		return (int) Math.pow(score, FastClicker.levelToPower);
	}

	public void setAnimation(int moneyWithoutIncome, int income)
	{
		animatedText.setText("+" + income, com.badlogic.gdx.graphics.Color.RED);
		this.income = income;
		moneyAtThisMomentOfAnimation = moneyWithoutIncome;
		moneyAtTheBeginningOfAnimation = moneyWithoutIncome;
		animatedText.startAnimation();
	}

	@Override
	public void render()
	{
		animatedText.render();
	}

	public void begin()
	{
		animatedText.begin();
	}

	public void end()
	{
		animatedText.end();
	}

	@Override
	public void update()
	{
		animatedText.update();
		if (animatedText.isAnimationInProgress())
		{
			moneyAtThisMomentOfAnimation = moneyValueIncreaseFunction();
		}
	}

	private int moneyValueIncreaseFunction()
	{
		return (int) (moneyAtTheBeginningOfAnimation + income * animatedText.getAnimationProgress());
	}

}
