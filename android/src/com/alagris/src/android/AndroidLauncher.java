package com.alagris.src.android;

import com.alagris.src.FastClicker;
import com.alagris.src.specific.AdMobInterface;
import com.alagris.src.specific.SwarmInterface;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.swarmconnect.Swarm;

import android.os.Bundle;

public class AndroidLauncher extends AndroidApplication
{

	AndroidLauncher mainActivity;
	InterstitialAd interstitialAd;
	AdMobInterface adMobGDX = new AdMobInterface()
	{
		private boolean b;

		public boolean isLoaded()
		{
			mainActivity.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					b = interstitialAd.isLoaded();
				}
			});
			return b;
		};

		@Override
		public void requestNewInterstitial()
		{
			mainActivity.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					//TODO:Remove test device 
					AdRequest adRequest = new AdRequest.Builder().addTestDevice("8BFB75BE5D040A282C06D3AC6F3C58E3")
							.build();
					interstitialAd.loadAd(adRequest);
				}
			});
		}

		@Override
		public void showAd()
		{
			mainActivity.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					if (interstitialAd.isLoaded())
					{
						interstitialAd.show();
					}
				}
			});
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mainActivity = this;
		// ============AD VIEW==========VVV
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		interstitialAd.setAdListener(adListener);
		// ============AD VIEW==========^^^

		// ============SWARM==========VVV
		SwarmInterface swarmInterface = new AndroidSwarmImplementation();
		// ============SWARM==========^^^

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// ============GAME VIEW==========VVV
		initialize(new FastClicker(swarmInterface, adMobGDX), config);
		// ============GAME VIEW==========^^^
		adMobGDX.requestNewInterstitial();
		// ============SWARM==========VVV
		Swarm.setAllowGuests(true);
		Swarm.setActive(this);
		// ============SWARM==========^^^
	}

	// Add everything below here too
	public void onResume()
	{
		super.onResume();
		// ============SWARM==========VVV
		Swarm.setActive(this);

		// Replace MY_APP_ID with your App ID from the Swarm Admin Panel
		// Replace MY_APP_KEY with your string App Key from the Swarm Admin
		// Panel
		Swarm.init(this, 18657, "47099aacc1cd24a267bf868accc965d2");
		// ============SWARM==========^^^
	}

	public void onPause()
	{
		super.onPause();
		// ============SWARM==========VVV
		Swarm.setInactive(this);
		// ============SWARM==========^^^
	}

	private AdListener adListener = new AdListener()
	{
		@Override
		public void onAdClosed()
		{
			adMobGDX.getAdMobListener().onAdClosed();
			super.onAdClosed();
		}

		@Override
		public void onAdFailedToLoad(int errorCode)
		{
			adMobGDX.getAdMobListener().onAdFailedToLoad(errorCode);
			super.onAdFailedToLoad(errorCode);
		}

		@Override
		public void onAdLeftApplication()
		{
			adMobGDX.getAdMobListener().onAdLeftApplication();
			super.onAdLeftApplication();
		}

		@Override
		public void onAdLoaded()
		{
			adMobGDX.getAdMobListener().onAdLoaded();
			super.onAdLoaded();
		}

		@Override
		public void onAdOpened()
		{
			adMobGDX.getAdMobListener().onAdOpened();
			super.onAdOpened();
		}

	};

}
