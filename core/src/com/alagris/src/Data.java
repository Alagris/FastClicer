package com.alagris.src;

import com.alagris.src.specific.SwarmInterface;

public class Data
{

	// I use this obscure way simple because Java provides no pointers for
	// primitive values
	// (and Long is immutable so using it doesn't change anything since I still
	// have to replace pointer to it every time I change it)
	long[] money = { 0 };
	long[] moneyToReclaim = { 0 };
	long[] numberOfLostGamesToShowAdv = { 0 };
	long[] lifes = { 0 }, goldLifes = { 0 };
	long[] best = { 0 };
	long[] gamemode = { FastClicker.MODE_MIXED };
	long[] idOfUserThisDataBelongsTo = { 0 }, mediumModeStartingLevel = { 0 }, hardModeStartingLevel = { 0 }; // unused
	long[][] array = { best, money, lifes, goldLifes, moneyToReclaim, numberOfLostGamesToShowAdv, gamemode,
			idOfUserThisDataBelongsTo, mediumModeStartingLevel, hardModeStartingLevel };
	private static String swarmMainCloudKey = "d";
	/** True if local data did exist and was impossible to parse */

	/**
	 * The logic is that: <br>
	 * -local data is more important than cloud data <br>
	 * -local data is encrypted <br>
	 * -if someone cracks encryption then he/she's lucky. I don't really care
	 * <br>
	 * -validation mechanism prevents could only from receiving corrupted data
	 * <br>
	 * -could will receive data even if it's cheated (but not corrupted)<br>
	 * -if local data is corrupted it will be synchronised with cloud <br>
	 * -if local data is corrupted and cloud is empty game will use defaults
	 * (fresh start)<br>
	 * -if local data does not exist and cloud is empty game will use defaults
	 * (fresh start) <br>
	 */
	public void uploadDataToCloud(SwarmInterface i)
	{
		i.uploadDataToCloud(swarmMainCloudKey, encrypt(pack(array)));
	}

	public void readSavedData(SwarmInterface arg)
	{

		byte[] result = arg.downloadDataFromCloud(swarmMainCloudKey);
		if (result == null)
		{
			setDefaultSettings();
		}
		else
		{
			long[] data = unpack(decrypt(result));
			if (data.length == array.length)
			{
				for (int i = 0; i < data.length; i++)
				{
					array[i][0] = data[i];
				}
			}
			else
			{
				setDefaultSettings();
			}
		}

	}

	private static final int LONG_SIZE_IN_BYTES = 8;

	/**
	 * @param offset
	 *            - starts from index 0 inclusive
	 */
	private void longToBytes(byte[] destination, Long source, int offset)
	{
		for (int i = 0; i < LONG_SIZE_IN_BYTES; i++)
			destination[offset + i] = (byte) (source >>> (LONG_SIZE_IN_BYTES - i - 1) * 8);
	}

	/**
	 * @param offset
	 *            - starts from index 0 inclusive
	 * @return
	 */
	private long bytesToLong(byte[] source, int offset)
	{
		long result = 0;
		long shift = 1;
		for (int i = 1; i <= LONG_SIZE_IN_BYTES; i++, shift *= 256)
			result += (long) source[offset + LONG_SIZE_IN_BYTES - i] * shift;
		return result;
	}

	private byte[] pack(long[][] data)
	{
		byte[] bytes = new byte[data.length * LONG_SIZE_IN_BYTES];
		for (int i = 0; i < data.length; i++)
		{
			longToBytes(bytes, data[i][0], i * LONG_SIZE_IN_BYTES);
		}
		return bytes;
	}

	private long[] unpack(byte[] data)
	{
		if (data.length % LONG_SIZE_IN_BYTES != 0) throw new IllegalArgumentException();
		long[] result = new long[data.length / LONG_SIZE_IN_BYTES];
		for (int i = 0; i < result.length; i++)
		{
			result[i] = bytesToLong(data, i * LONG_SIZE_IN_BYTES);
		}
		return result;
	}

	private byte[] encrypt(byte[] input)
	{
		return input;
	}

	private byte[] decrypt(byte[] input)
	{
		return input;
	}

	private void setDefaultSettings()
	{
		best[0] = 0;
		money[0] = 0;
		lifes[0] = 1;
		goldLifes[0] = 0;
		moneyToReclaim[0] = 0;
		numberOfLostGamesToShowAdv[0] = 3;
		FastClicker.setGamemode(FastClicker.MODE_SIMPLE);
		idOfUserThisDataBelongsTo[0] = 0;
		mediumModeStartingLevel[0] = 0;
		hardModeStartingLevel[0] = 0;
	}
}
