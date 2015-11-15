package com.alagris.src;

/**
 * Created by Alagris on 17/06/2015.
 */
public abstract class TimerWithListener extends Timer
{
	private double limit;

	public TimerWithListener()
	{

	}

	public TimerWithListener(double limit)
	{
		this.limit = limit;
	}

	public TimerWithListener(double elapsedTime, double limit)
	{
		super(elapsedTime);
		this.limit = limit;
	}

	/** This method goes off when current time >= limit */
	public abstract void whenTimeIsUp(double time, double limit);

	@Override
	public void update()
	{
		super.update();
		if (hasElapsedMoreThan(getLimit()))
		{
			whenTimeIsUp(getElapsedTime(), getLimit());
		}
	}

	public double getLimit()
	{
		return limit;
	}

	public void setLimit(double limit)
	{
		this.limit = limit;
	}

	public double howMuchToAlarm()
	{
		return howMuchTo(getLimit());
	}

	public boolean hasAlarmAlreadyFired()
	{
		return getLimit() <= getElapsedTime();
	}

}
