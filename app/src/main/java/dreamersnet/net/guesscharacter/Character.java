package dreamersnet.net.guesscharacter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Ben on 9/14/2016.
 */
public class Character {
    private int mTextResId;
    private ArrayList<Integer> mHints;
    private ArrayList<Integer> mTargets;
    private int mNumHints;
    Random rand = new Random();

    public Character(int textResId) {
        mHints = new ArrayList<Integer>();
        mTargets = new ArrayList<Integer>();
        mTargets.add(textResId);
        mTextResId = textResId;
        mNumHints = rand.nextInt(3)+2;
    }

    public Character(int[] charInfo) {
        mTextResId = charInfo[0];
        mTargets = new ArrayList<Integer>();
        mHints = new ArrayList<Integer>();
        for (int i=0; i< charInfo.length; i++)
        {
            if (charInfo[i] == 0)
                Log.d("Character Information", "An error occurred with character " + i + "," + charInfo[i] + " this:" + mTextResId);
            mTargets.add(charInfo[i]);
        }
    }

    public boolean addHint(int textResId) {
        if (mHints.size() < mNumHints) {
            mHints.add(new Integer(textResId));
            return true;
        } else {
            return false;
        }
    }

    public void setNumHints(int numHints) { mNumHints = numHints; }

    public int getHintRes(int index) {
        return mHints.get(index);
    }

    public void shuffleTargets() {
        Collections.shuffle(mTargets);
    }

    public int getTargetRes(int index) {
        return mTargets.get(index);
    }

    public int getNumHints() {
        return mNumHints;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isTargetRight(int resId) {
        return mTextResId == resId;
    }

    public void addTarget(int textResId) {
        mTargets.add(new Integer(textResId));
    }
    public boolean addTarget(Target t) {
        if (mTargets.contains(t) || t.getTextResId()==0)
            return false;
        mTargets.add(new Integer(t.getTextResId()));
        return true;
    }

    public void shuffleHints() {
        Collections.shuffle(mHints);
    }

    public ArrayList<Integer> getTargets() {
        return mTargets;
    }

    public int[] toArray() {
        int[] tmp = new int[4];
        int ct = 1;
        for (int i=0; i<4; i++) {
            int curInt = mTargets.get(i);
            if (curInt==0) {
                Log.d("Character Information", "An error occurred with character " + i + "," + tmp[i] + " this:" + mTextResId);
            }
            tmp[i]=curInt;
        }
        return tmp;
    }

}
