package com.gtr.dices;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView leftDice;
    ImageView rightDice;
    Button newGameButton;
    Button rollButton ;
    Button holdButton;
    TextView mPlayer1_ScoreTextView;
    TextView mPlayer2_ScoreTextView;
    TextView mPlayer1_RoundScore;
    TextView mPlayer2_RoundScore;
    int scoresP1;
    int scoresP2;
    int roundScores;
    int activePlayer;

    final int[] diceArray = {R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
        {
            scoresP1 = savedInstanceState.getInt("Player1ScoreKey");
            scoresP2 = savedInstanceState.getInt("Player2ScoreKey");
            roundScores = savedInstanceState.getInt("RoundScoreKey");
            activePlayer = savedInstanceState.getInt("ActivePlayerKey");
        }
        else
        {
            scoresP1 = 0;
            scoresP2 = 0;
            roundScores = 0;
            activePlayer = 0;
        }

        newGameButton = findViewById(R.id.button_NewGame);
        rollButton  = findViewById(R.id.button_Roll);
        holdButton = findViewById(R.id.button_Hold);
        mPlayer1_ScoreTextView = findViewById(R.id.textView_currentScorePlayer1);
        mPlayer2_ScoreTextView = findViewById(R.id.textView_currentScorePlayer2);
        mPlayer1_RoundScore = findViewById(R.id.textView_RoundScorePlayer1);
        mPlayer2_RoundScore = findViewById(R.id.textView_RoundScorePlayer2);

        leftDice = findViewById(R.id.image_leftDice);
        rightDice = findViewById(R.id.image_rightDice);

        mPlayer1_RoundScore.setText(Integer.toString(scoresP1));
        mPlayer2_RoundScore.setText(Integer.toString(scoresP2));

        if(activePlayer == 0) {
            mPlayer1_ScoreTextView.setText(Integer.toString(roundScores));
        } else {
            mPlayer2_ScoreTextView.setText(Integer.toString(roundScores));
        }

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random randomNumberGenerator = new Random();

                int number = randomNumberGenerator.nextInt(6);
                leftDice.setImageResource(diceArray[number]);

                int number1 = randomNumberGenerator.nextInt(6);
                rightDice.setImageResource(diceArray[number1]);

                roundScores +=2+ number + number1;
                if(number == 0 || number1 == 0)
                {
                    Toast.makeText(getApplicationContext(),"You Hit 1 END TURN",Toast.LENGTH_SHORT).show();
                    roundScores = 0;
                    mPlayer1_ScoreTextView.setText("0");
                    mPlayer2_ScoreTextView.setText("0");
                    if(activePlayer == 0)
                    {
                        activePlayer = 1;
                    }
                    else
                    {
                        activePlayer = 0;
                    }
                }

                if(activePlayer == 0) {
                    mPlayer1_ScoreTextView.setText(Integer.toString(roundScores));
                } else {
                    mPlayer2_ScoreTextView.setText(Integer.toString(roundScores));
                }

            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(activePlayer == 0)
                {
                    scoresP1 += roundScores;
                    mPlayer1_RoundScore.setText(Integer.toString(scoresP1));
                    mPlayer1_ScoreTextView.setText(Integer.toString(0));
                    roundScores = 0;
                    if(scoresP1 > 100)
                    {
                        Winner();
                    }
                    else
                        {
                            activePlayer = 1;

                        }
                }
                else
                {
                    scoresP2 += roundScores;
                    mPlayer2_RoundScore.setText(Integer.toString(scoresP2));
                    mPlayer2_ScoreTextView.setText(Integer.toString(0));
                    roundScores = 0;

                    if(scoresP2 > 100)
                    {
                        Winner();
                    }
                    else
                    {
                        activePlayer = 0;
                    }
                }
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTurn();
            }
        });

    }

    public void Winner()
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("WINNER");
        alert.setMessage("Player " + (activePlayer + 1) );
        alert.setCancelable(false);
        alert.setPositiveButton("END GAME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newTurn();
            }
        });
        alert.show();
    }
    public void newTurn()
    {
        activePlayer = 0;
        scoresP1 = 0;
        scoresP2 = 0;
        roundScores = 0;
        mPlayer1_ScoreTextView.setText("0");
        mPlayer2_ScoreTextView.setText("0");
        mPlayer1_RoundScore.setText("0");
        mPlayer2_RoundScore.setText("0");
        leftDice.setImageResource(diceArray[5]);
        rightDice.setImageResource(diceArray[5]);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("RoundScoreKey", roundScores);
        outState.putInt("Player1ScoreKey", scoresP1);
        outState.putInt("Player2ScoreKey", scoresP2);
        outState.putInt("ActivePlayerKey", activePlayer);
    }
}

