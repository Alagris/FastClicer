package com.alagris.src.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alagris.src.FastClicker;
import com.alagris.src.specific.AdMobInterface;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "FastClicker";
		config.useGL30 = false;
		int extra = 50;
		config.width = 9 * extra;
		config.height = 16 * extra;
		config.resizable = true;
		new LwjglApplication(new FastClicker(new DesktopSwarmImplementation(), interface1), config);
	}

	private static AdMobInterface interface1 = new AdMobInterface()
	{

		@Override
		public void showAd()
		{
			System.out.println("showAd");
		}

		@Override
		public void requestNewInterstitial()
		{
			System.out.println("requestNewInterstitial");
		}

	};
}
