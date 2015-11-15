package com.alagris.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class AnimatedText implements Drawable
{
	private BitmapFont font;
	private float x, y,w,glyphX;
	private GlyphLayout glyphLayout;
	private SpriteBatch batch;
	private float timeOfAnimation, animationDurationTime, animationProgress;
	private boolean isAnimationInProgress = false;

	public AnimatedText(BitmapFont font1, float x1, float y1,float width, SpriteBatch batch1, float animationDurationTime1)
	{
		glyphLayout = new GlyphLayout();
		font = font1;
		x = x1;
		y = y1;
		w = width;
		batch = batch1;
		animationDurationTime = animationDurationTime1;
	}

	public void setText(CharSequence text, com.badlogic.gdx.graphics.Color color)
	{
		glyphLayout.setText(font, text, color, 0, Align.left, false);
		if(w < 0){
			glyphX = x;
		}else{
			glyphX = x + (w-glyphLayout.width)/2;
		}
		
	}
	
	public void setWidth(float w){
		this.w = w;
		if(w < 0){
			glyphX = x;
		}else{
			glyphX = x + (w-glyphLayout.width)/2;
		}
	}

	public void startAnimation()
	{
		isAnimationInProgress = true;
		timeOfAnimation = 0;
	}

	@Override
	public void render()
	{
		if (isAnimationInProgress)
		{
			font.draw(batch, glyphLayout, glyphX, y + glyphLayout.height * animationProgress);
		}
	}

	public void renderWithBatch()
	{
		if (isAnimationInProgress)
		{
			begin();
			font.draw(batch, glyphLayout, glyphX, y + glyphLayout.height * animationProgress);
			end();
		}
	}

	public void begin()
	{
		batch.begin();
	}

	public void end()
	{
		batch.end();
	}

	@Override
	public void update()
	{
		if (isAnimationInProgress)
		{

			timeOfAnimation += Gdx.graphics.getDeltaTime();
			animationProgress = timeOfAnimation / animationDurationTime;
			if (timeOfAnimation > animationDurationTime)
			{
				timeOfAnimation = animationDurationTime;
				animationProgress = 0;
				isAnimationInProgress = false;
			}
		}
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getTimeOfAnimation()
	{
		return timeOfAnimation;
	}

	public void setTimeOfAnimation(float timeOfAnimation)
	{
		this.timeOfAnimation = timeOfAnimation;
	}

	public float getAnimationDurationTime()
	{
		return animationDurationTime;
	}

	public float getAnimationProgress()
	{
		return animationProgress;
	}

	public boolean isAnimationInProgress()
	{
		return isAnimationInProgress;
	}

}
