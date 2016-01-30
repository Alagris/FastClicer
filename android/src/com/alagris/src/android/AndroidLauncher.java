package com.alagris.src.android;

import com.alagris.src.FastClicker;
import com.alagris.src.specific.AdMobInterface;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
//import com.swarmconnect.Swarm;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

public class AndroidLauncher extends AndroidApplication
{
	private AndroidSwarmImplementation swarmInterface;
	private AndroidLauncher mainActivity;
	private InterstitialAd interstitialAd;
	private FastClicker fastClicker;
	private AdMobInterface adMobGDX = new AdMobInterface()
	{
		private boolean b;

		@Override
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
					// TODO:Remove test device
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
	private GoogleApiClient googleApiClient;

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
		swarmInterface = new AndroidSwarmImplementation(this);

		// ============SWARM==========^^^
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// ============GAME VIEW==========VVV
		fastClicker = new FastClicker(swarmInterface, adMobGDX);
		initialize(fastClicker, config);
		// ============GAME VIEW==========^^^

		// ============GOOGLE==========VVV
		googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(connectionCallbacks)
				.addOnConnectionFailedListener(connectionFailedListener).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).addApi(Games.API).addScope(Games.SCOPE_GAMES).addApi(Drive.API)
				.addScope(Drive.SCOPE_APPFOLDER).build();
		swarmInterface.setGoogleApiClient(googleApiClient);
		adMobGDX.requestNewInterstitial();
		// ============GOOGLE==========^^^

		// // ============SWARM==========VVV
		// Swarm.setAllowGuests(true);
		// Swarm.setActive(this);
		// // ============SWARM==========^^^

		// =============INTERNET LISTENER
		BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				if (intent == null || intent.getExtras() == null) return;

				ConnectivityManager manager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = manager.getActiveNetworkInfo();

				if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED)
				{
					if (FastClicker.IS_DEBUG_MODE) swarmInterface.showError("Connection available", false);
					googleApiClient.connect();
				}
			}
		};
		registerReceiver(broadcastReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
	}

	// Add everything below here too
	@Override
	public void onResume()
	{
		super.onResume();
		// // ============SWARM==========VVV
		// Swarm.setActive(this);
		//
		// // Replace MY_APP_ID with your App ID from the Swarm Admin Panel
		// // Replace MY_APP_KEY with your string App Key from the Swarm Admin
		// // Panel
		// Swarm.init(this, 18657, "47099aacc1cd24a267bf868accc965d2");
		// // ============SWARM==========^^^
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		googleApiClient.connect();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// // ============SWARM==========VVV
		// Swarm.setInactive(this);
		// // ============SWARM==========^^^
	}

	@Override
	protected void onDestroy()
	{
		googleApiClient.disconnect();
		super.onDestroy();
	}

	private OnConnectionFailedListener connectionFailedListener = new OnConnectionFailedListener()
	{
		@Override
		public void onConnectionFailed(ConnectionResult result)
		{
			if (FastClicker.IS_DEBUG_MODE) swarmInterface.showError("Connection failed", false);
		}
	};

	private ConnectionCallbacks connectionCallbacks = new ConnectionCallbacks()
	{

		@Override
		public void onConnectionSuspended(int cause)
		{
			if (FastClicker.IS_DEBUG_MODE) swarmInterface.showError("Connection suspended", false);
		}

		@Override
		public void onConnected(Bundle connectionHint)
		{
			if (FastClicker.IS_DEBUG_MODE) swarmInterface.showError("Connection successful", false);
		}
	};

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
			if (FastClicker.IS_DEBUG_MODE) swarmInterface.showError("Ad failed to load", false);
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
			if (FastClicker.IS_DEBUG_MODE) swarmInterface.showError("Ad loaded", false);
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
