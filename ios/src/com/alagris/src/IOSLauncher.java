package com.alagris.src;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.alagris.src.FastClicker;
import com.alagris.src.specific.AdMobInterface;

public class IOSLauncher extends IOSApplication.Delegate
{
	@Override
	protected IOSApplication createApplication()
	{
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		return new IOSApplication(new FastClicker(new IosSwarmImplementation(), interface1), config);
	}

	public static void main(String[] argv)
	{
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, IOSLauncher.class);
		pool.close();
	}

	private AdMobInterface interface1 = new AdMobInterface()
	{

		@Override
		public void showAd()
		{

		}

		@Override
		public void requestNewInterstitial()
		{

		}

	};
}