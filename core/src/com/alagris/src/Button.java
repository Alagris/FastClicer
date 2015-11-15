package com.alagris.src;


public abstract class Button extends Graphics implements GUIbutton
{
	
	protected String		name;
	protected float			textX			= 0, textY = 0;
	
	/** If is clicked will return true and do renderClick() */
	public boolean checkButton(float mouseX, float mouseY, boolean isClicked)
	{
		
		if (isPointInside(mouseX, mouseY))
		{
			if (isClicked)
			{
				onClick();
				return true;
			}
			else
			{
				renderMouseEnter();
				return false;
			}
		}
		render();
		return false;
	}
	
}
