package com.alagris.src;

import java.util.BitSet;

/**
 * Created by Alagris on 08/06/2015.
 */
public class StripesGenerator extends BitSet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int stripesCount = 0;

    public StripesGenerator(int stripesCount) {
        super(stripesCount);
        this.stripesCount = stripesCount;
    }

    /**
     * @param darkStripesFrequency - value between 0 and 1
     */
    public void generateSet(double darkStripesFrequency) {
        for (int i = 0; i < getStripesCount(); i++) {
            set(i, Math.random() < darkStripesFrequency);
        }
    }

    public int getStripesCount() {
        return stripesCount;
    }

    public void setStripesCount(int stripesCount) {
        this.stripesCount = stripesCount;
    }

    public int[] XNOR(StripesGenerator parentSet0) {
        if (parentSet0.stripesCount != stripesCount) throw new IllegalArgumentException();
        int length = 0;
        for (int i = 0; i < stripesCount; i++) {
            if (parentSet0.get(i) == get(i)) length++;
        }
        if (length == 0) return new int[0];
        int[] result = new int[length];
        length = 0;//program is reusing variable for other purpose
        for (int i = 0; i < stripesCount; i++) {
            if (parentSet0.get(i) == get(i)) result[length++] = i;
        }
        return result;
    }

	public void set(StripesGenerator parentSet0)
	{
		for (int i = 0; i < stripesCount; i++) 
		{
			set(i,parentSet0.get(i));
        }
	}
}
