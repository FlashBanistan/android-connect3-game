package com.example.android.connect3game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView currentTurn = (TextView) findViewById(R.id.topMessage);
        currentTurn.setText("Player " + activePlayer + " go!");
    }

    /* Player 1 equals 1. Player 2 equals 2 */
    int activePlayer = 1;

    /* 3 means that the box is un-played. 2 means it belongs to player 2. 1 means it belongs to player 1 */
    int[] gameState = {3, 3, 3, 3, 3, 3, 3, 3, 3};

    int[][] winningPositions = {{0, 1, 2},{3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};


    public void dropIn (View view) {
        TextView currentTurn = (TextView) findViewById(R.id.topMessage);
        ImageView counter = (ImageView) view; //Set counter variable to the view clicked on
        int imageTag = Integer.parseInt(counter.getTag().toString());

        //Check if box has already been played.
        if(gameState[imageTag] != 3) return;
        //Check if game has already been won.
        if(activePlayer == 3) return;

        if(activePlayer == 1) {
            counter.setImageResource(R.drawable.red);
            gameState[imageTag] = activePlayer;
            activePlayer = 2;
        } else {
            counter.setImageResource(R.drawable.yellow);
            gameState[imageTag] = activePlayer;
            activePlayer = 1;
        }

        //Check if anyone has won
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 3) {

                currentTurn.setText("Player " + gameState[winningPosition[0]] + " wins!");
                Button resetGame = (Button)findViewById(R.id.play_again_btn);
                resetGame.setVisibility(View.VISIBLE);
                activePlayer = 3; //3 means game is over and no further plays are allowed.
                return;

            }
        }


        currentTurn.setText("Player " + activePlayer + " go!");

    }

    public void resetGame (View view) {

        /* Reset active player */
        activePlayer = 1;

        /* Reset game state */
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 3;
        }

        /* Make images invisible */
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

        /* Hide the button */
        view.setVisibility(View.INVISIBLE);

        /* Change top text to indicate whose turn it is */
        TextView currentTurn = (TextView) findViewById(R.id.topMessage);
        currentTurn.setText("Player " + activePlayer + " go!");
    }
}
