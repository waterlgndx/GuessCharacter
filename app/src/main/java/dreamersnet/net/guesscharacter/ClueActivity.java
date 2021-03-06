package dreamersnet.net.guesscharacter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ClueActivity extends AppCompatActivity {
    private TextView mHintsLeftTextView;
    private TextView mHintTextView;
    private Button mGuessButton;
    private Button mNextButton;
    private int curCharacter=0;
    private int curHint=1;
    private static final int REQUEST_CODE_CORRECT = 0;
    private static final String KEY_CHARACTER = "character";
    private static final String KEY_HINT = "hint";
    private static final String KEY_NUM_HINTS = "numhints";
    Random rand = new Random();
    private ArrayList<Target> mUsedTargets = new ArrayList<Target>();


    private ArrayList<Target> mTargetBank = new ArrayList<Target>(Arrays.asList(
            new Target(R.string.target_daisy),
            new Target(R.string.target_iggy_koopa),
            new Target(R.string.target_king_boo),
            new Target(R.string.target_king_k_rool),
            new Target(R.string.target_lemmy_koopa),
            new Target(R.string.target_luigi),
            new Target(R.string.target_mario),
            new Target(R.string.target_peach),
            new Target(R.string.target_samus),
            new Target(R.string.target_tatanga),
            new Target(R.string.target_toad),
            new Target(R.string.target_waluigi),
            new Target(R.string.target_wario),
            new Target(R.string.target_zelda)
    ));

    private Character[] mCharacterBank = new Character[] {
            new Character(R.string.target_daisy ),
            new Character(R.string.target_peach),
            new Character(R.string.target_mario),
            new Character(R.string.target_luigi),
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_CHARACTER, curCharacter  );
        savedInstanceState.putInt(KEY_HINT, curHint);
        savedInstanceState.putInt(KEY_NUM_HINTS, mCharacterBank[curCharacter].getNumHints());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_CORRECT) {
            if (data == null)
                return;
            mNextButton.setText(R.string.next_button_game);
            mNextButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    curCharacter = rand.nextInt(4);
                    curHint = 1;
                    mCharacterBank[curCharacter].shuffleHints();
                    updateUI();
                    mNextButton.setText(R.string.next_button);
                    mNextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (curHint < mCharacterBank[curCharacter].getNumHints()) {
                                curHint = curHint +1;
                            }
                            updateUI();
                        }
                    });
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue);

        mCharacterBank[0].addHint(R.string.hint_daisy_1);
        mCharacterBank[0].addHint(R.string.hint_daisy_2);
        mCharacterBank[0].addHint(R.string.hint_daisy_3);
        mCharacterBank[0].addHint(R.string.hint_daisy_4);
        mCharacterBank[0].addHint(R.string.hint_daisy_5);
        mCharacterBank[1].addHint(R.string.hint_peach_1);
        mCharacterBank[1].addHint(R.string.hint_peach_2);
        mCharacterBank[1].addHint(R.string.hint_peach_3);
        mCharacterBank[1].addHint(R.string.hint_peach_4);
        mCharacterBank[1].addHint(R.string.hint_peach_5);
        mCharacterBank[2].addHint(R.string.hint_mario_1);
        mCharacterBank[2].addHint(R.string.hint_mario_2);
        mCharacterBank[2].addHint(R.string.hint_mario_3);
        mCharacterBank[2].addHint(R.string.hint_mario_4);
        mCharacterBank[2].addHint(R.string.hint_mario_5);
        mCharacterBank[3].addHint(R.string.hint_luigi_1);
        mCharacterBank[3].addHint(R.string.hint_luigi_2);
        mCharacterBank[3].addHint(R.string.hint_luigi_3);
        mCharacterBank[3].addHint(R.string.hint_luigi_4);
        mCharacterBank[3].addHint(R.string.hint_luigi_5);
        Target curTarg;
        for (int chbank = 0; chbank < mCharacterBank.length; chbank++) {
            curTarg = new Target(mCharacterBank[chbank].getTextResId());
            mUsedTargets.add(curTarg);
            mTargetBank.remove(curTarg);
            for (int t=0; t<3; t++) {
                curTarg = mTargetBank.get(rand.nextInt(mTargetBank.size()-1));
                mCharacterBank[chbank].addTarget(curTarg);
                mTargetBank.remove(curTarg);
                mUsedTargets.add(curTarg);
            }
            mTargetBank.addAll(mUsedTargets);
            mUsedTargets.clear();
        }

        if (savedInstanceState!=null) {
            curCharacter = savedInstanceState.getInt(KEY_CHARACTER);
            curHint = savedInstanceState.getInt(KEY_HINT);
            mCharacterBank[curCharacter].setNumHints(savedInstanceState.getInt(KEY_NUM_HINTS));
        } else {
            curCharacter = rand.nextInt(4);
            //populate the targets randomly...

            mCharacterBank[curCharacter].shuffleHints();
        }


        mHintsLeftTextView = (TextView) findViewById(R.id.hints_left_text_view);
        mHintTextView = (TextView) findViewById(R.id.hint_text_view);
        mGuessButton = (Button) findViewById(R.id.guess_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curHint < mCharacterBank[curCharacter].getNumHints()) {
                    curHint = curHint +1;
                }
                updateUI();
            }
        });
        mGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] cInfo = mCharacterBank[curCharacter].toArray();
                Intent i = GuessActivity.newIntent(ClueActivity.this,cInfo);
                startActivityForResult(i,REQUEST_CODE_CORRECT);
            }
        });

        updateUI();
    }

    private void updateUI() {
        int hintRes = mCharacterBank[curCharacter].getHintRes(curHint-1);
        int hintsLeft = mCharacterBank[curCharacter].getNumHints() - curHint;
        mHintTextView.setText(hintRes);
        mHintsLeftTextView.setText(Integer.toString(hintsLeft));
    }
}
