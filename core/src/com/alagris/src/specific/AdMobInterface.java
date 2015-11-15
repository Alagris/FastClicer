package com.alagris.src.specific;

public abstract class AdMobInterface
{
	private AdMobListener listener = new AdMobListener();

	public abstract void requestNewInterstitial();

	public abstract void showAd();

	public boolean isLoaded()
	{
		return false;
	}

	public AdMobListener getAdMobListener()
	{
		return listener;
	}

	public void setAdMobListener(AdMobListener listener)
	{
		this.listener = listener;
	}

}
