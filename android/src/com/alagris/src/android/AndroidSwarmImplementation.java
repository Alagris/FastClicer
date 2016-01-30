package com.alagris.src.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

import com.alagris.src.specific.SwarmInterface;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
//import com.swarmconnect.Swarm;
//import com.swarmconnect.SwarmAchievement;
//import com.swarmconnect.SwarmActiveUser.GotCloudDataCB;
//import com.swarmconnect.SwarmLeaderboard;

import android.widget.Toast;

public class AndroidSwarmImplementation implements SwarmInterface
{

	private GoogleApiClient googleApiClient;
	private final AndroidApplication activity;

	public AndroidSwarmImplementation(AndroidApplication activity)
	{
		this.activity = activity;
	}

	@Override
	public void showLeaderboards()
	{
		// Swarm.showLeaderboards();
	}

	private static final int REQUEST_LEADERBOARD = 89123;

	@Override
	public void showLeaderboard(String MY_LEADERBOARD_ID)
	{
		// SwarmLeaderboard.showLeaderboard(MY_LEADERBOARD_ID);
		if (isOnline())
			activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(googleApiClient, MY_LEADERBOARD_ID),
					REQUEST_LEADERBOARD);

		else showError("No connetion yet", false);
	}

	@Override
	public void submitScore(String MY_LEADERBOARD_ID, long lastScore)
	{
		// SwarmLeaderboard.submitScore(MY_LEADERBOARD_ID, lastScore);
		if (isOnline()) Games.Leaderboards.submitScore(googleApiClient, MY_LEADERBOARD_ID, 1337);

	}

	@Override
	public void unlock(String MY_ACHIEVEMENT_ID)
	{
		if (isOnline()) Games.Achievements.unlock(googleApiClient, MY_ACHIEVEMENT_ID);
		// SwarmAchievement.unlock(MY_ACHIEVEMENT_ID);
	}

	@Override
	public void showDashboard()
	{
		// will not be implemented in google play services
		// Swarm.showDashboard();
	}

	@Override
	public boolean isOnline()
	{
		return googleApiClient.isConnected();
		// return Swarm.isOnline();
	}

	@Override
	public boolean isLoggedIn()
	{
		return true;// in google play services it's always true
		// return Swarm.isLoggedIn();
	}

	@Override
	public void showLogin()
	{
		// in google play services there is no need for it
		// Swarm.showLogin();
	}

	private static final int REQUEST_ACHIEVEMENTS = 1241;// random

	@Override
	public void showAchievements()
	{
		if (isOnline()) activity.startActivityForResult(Games.Achievements.getAchievementsIntent(googleApiClient),
				REQUEST_ACHIEVEMENTS);
		else showError("No connetion yet", false);
		// Swarm.showAchievements();
	}

	@Override
	public int getUserID()
	{
		return -1;// not necessary in google play services
		// return Swarm.user.userId;
	}

	/** Works also in offline mode */
	@Override
	public void uploadDataToCloud(String key, byte[] data)
	{
		if (!isOnline())
		{
			showError("No connection yet", false);
			return;
		}
		Snapshots.OpenSnapshotResult open = Games.Snapshots.open(googleApiClient, key, true).await();

		int attemptCount = 0;
		whileLoop: while (true)
		{
			if (attemptCount++ < 5)
			{
				switch (open.getStatus().getStatusCode()) {
					case GamesStatusCodes.STATUS_SNAPSHOT_CONFLICT:
						Snapshot snapshot = open.getSnapshot();
						Snapshot conflictSnapshot = open.getConflictingSnapshot();

						Snapshot resolvedSnapshot = snapshot.getMetadata().getLastModifiedTimestamp() < conflictSnapshot
								.getMetadata().getLastModifiedTimestamp() ? conflictSnapshot : snapshot;

						open = Games.Snapshots.resolveConflict(googleApiClient, open.getConflictId(), resolvedSnapshot)
								.await();
						break;
					case GamesStatusCodes.STATUS_OK:
						break whileLoop;
					case GamesStatusCodes.STATUS_SNAPSHOT_CONTENTS_UNAVAILABLE:
						showError("Data saving problem", false);
						break whileLoop;
				}
			}
			else
			{
				showError("ERROR! Your cloud has unresolved conflicts! Your progress cannot be saved!", true);
				return;
			}

		}

		Snapshot snapshot = open.getSnapshot();
		snapshot.getSnapshotContents().writeBytes(data);
		SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder().build();
		Snapshots.CommitSnapshotResult commit = Games.Snapshots
				.commitAndClose(googleApiClient, snapshot, metadataChange).await();
		if (commit.getStatus().isSuccess())
		{
			showError("Uploading finished", false);
		}
		else
		{
			showError("Could not save your data!", false);
		}
		// if (Swarm.isLoggedIn()) {
		// Swarm.user.saveCloudData(key,value);
		// }
	}

	@Override
	public void showError(final String text, final boolean showForLongTime)
	{
		if (areMessagesSupressed)
		{
			messages.add(text);
			messagesDuration.set(messages.size(), showForLongTime);
		}
		else activity.handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(activity.getContext(), text, showForLongTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	//////// THIS IS FOR SWARM/////////////
	// private class MyGotCloudDataCB extends GotCloudDataCB
	// {
	// private String receivedData = null;
	//
	// public void gotData(String data)
	// {
	//
	// receivedData = data;
	// }
	// };
	//
	// MyGotCloudDataCB callback = new MyGotCloudDataCB();

	@Override
	public byte[] downloadDataFromCloud(String key)
	{
		if (!isOnline()) return null;
		// Open the saved game using its name.
		Snapshots.OpenSnapshotResult result = Games.Snapshots.open(googleApiClient, key, true).await();

		// Check the result of the open operation
		if (result.getStatus().isSuccess())
		{
			Snapshot snapshot = result.getSnapshot();
			// Read the byte content of the saved game.
			try
			{
				return snapshot.getSnapshotContents().readFully();
			}
			catch (IOException e)
			{
				showError("Error! Could not read game data!", false);
			}
		}
		else
		{
			showError("Error! Could not load game data!", false);
		}

		return null;

		// if (Swarm.isLoggedIn())
		// {
		// Swarm.user.getCloudData(key, callback);
		// return callback.receivedData;
		// }
		// return null;
	}

	public GoogleApiClient getGoogleApiClient()
	{
		return googleApiClient;
	}

	public void setGoogleApiClient(GoogleApiClient googleApiClient)
	{
		this.googleApiClient = googleApiClient;
	}

	private ArrayList<String> messages = new ArrayList<String>();
	private BitSet messagesDuration = new BitSet();
	private boolean areMessagesSupressed = false;

	@Override
	public void shouldHoldErrorMessages(boolean supressThem)
	{
		areMessagesSupressed = supressThem;
		if (!areMessagesSupressed)
		{
			for (int i = 0; i < messages.size(); i++)
			{
				showError(messages.get(i), messagesDuration.get(i));
			}
			messages.clear();
			messagesDuration.clear();
		}

	}

}
