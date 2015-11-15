package com.alagris.src.specific;

public class AdMobListener
{

	private AdMobEventListener onAdOpenedListener, onAdLoadedListener, onAdLeftApplicationListener, onAdClosedListener;
	private AdMobEventListenerInteger onAdFailedToLoadListener;

	public void onAdOpened()
	{
		if (onAdOpenedListener != null) onAdOpenedListener.evenOccured();
	}

	public void onAdLoaded()
	{
		if (onAdLoadedListener != null) onAdLoadedListener.evenOccured();
	}

	public void onAdLeftApplication()
	{
		if (onAdLeftApplicationListener != null) onAdLeftApplicationListener.evenOccured();
	}

	public void onAdFailedToLoad(int errorCode)
	{
		if (onAdFailedToLoadListener != null) onAdFailedToLoadListener.evenOccured(errorCode);
		System.out.println("/////FAILED TO LOAD ADVERTISEMENT error code: " + errorCode);
	}

	public void onAdClosed()
	{
		if (onAdClosedListener != null) onAdClosedListener.evenOccured();
	}

	////////////////////////////
	//// GETTERS & SETTERS///////
	////////////////////////////

	public AdMobEventListener getOnAdOpenedListener()
	{
		return onAdOpenedListener;
	}

	public void setOnAdOpenedListener(AdMobEventListener onAdOpenedListener)
	{
		this.onAdOpenedListener = onAdOpenedListener;
	}

	public AdMobEventListener getOnAdLoadedListener()
	{
		return onAdLoadedListener;
	}

	public void setOnAdLoadedListener(AdMobEventListener onAdLoadedListener)
	{
		this.onAdLoadedListener = onAdLoadedListener;
	}

	public AdMobEventListener getOnAdLeftApplicationListener()
	{
		return onAdLeftApplicationListener;
	}

	public void setOnAdLeftApplicationListener(AdMobEventListener onAdLeftApplicationListener)
	{
		this.onAdLeftApplicationListener = onAdLeftApplicationListener;
	}

	public AdMobEventListenerInteger getOnAdFailedToLoadListener()
	{
		return onAdFailedToLoadListener;
	}

	public void setOnAdFailedToLoadListener(AdMobEventListenerInteger onAdFailedToLoadListener)
	{
		this.onAdFailedToLoadListener = onAdFailedToLoadListener;
	}

	public AdMobEventListener getOnAdClosedListener()
	{
		return onAdClosedListener;
	}

	public void setOnAdClosedListener(AdMobEventListener onAdClosedListener)
	{
		this.onAdClosedListener = onAdClosedListener;
	}
}
