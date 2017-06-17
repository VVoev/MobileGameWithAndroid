package me.icytower.Game.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import me.icytower.Game.Core.Constants;
import me.icytower.Game.Db.DbManager;
import me.icytower.Game.Db.Scores;
import me.icytower.R;

public class Result extends AppCompatActivity {

    private DbManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        this.dbManager = DbManager.getInstance(Constants.CONTEXT);


        TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView)findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE",0);
        String playerName = Constants.PLAYER_NAME;
        scoreLabel.setText(score + "");

        Scores playerDetails = new Scores(playerName,score);
        dbManager.addNewScore(playerDetails);

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGHSCORE",0);

        if(score>highScore){
            highScoreLabel.setText("Highscore : "+score);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGHSCORE",score);
            editor.commit();
        }else{
            highScoreLabel.setText("HighScore: "+highScore);
        }

    }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),NewGame.class));
    }


}
