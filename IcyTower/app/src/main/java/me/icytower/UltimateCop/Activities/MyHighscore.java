package me.icytower.UltimateCop.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.icytower.UltimateCop.GlobalConstants.Constants;
import me.icytower.UltimateCop.Db.DbManager;
import me.icytower.UltimateCop.Db.Scores;
import me.icytower.R;

public class MyHighscore extends AppCompatActivity {
    private TextView highscoreTextView;
    private EditText userText;
    private DbManager dbManager;

    private Button addToDb;
    private Button deleteFromDb;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_highscore);

        highscoreTextView = (TextView) findViewById(R.id.highScoreTextView);
        userText = (EditText)findViewById(R.id.userText);
        addToDb = (Button)findViewById(R.id.addToDb);
        deleteFromDb = (Button)findViewById(R.id.deleteFromDb);
        relativeLayout = (RelativeLayout)findViewById(R.id.highScoreRelativeLayout);
        dbManager = DbManager.getInstance(Constants.CONTEXT);

        addToDb.setVisibility(View.INVISIBLE);
        deleteFromDb.setVisibility(View.INVISIBLE);
        userText.setVisibility(View.INVISIBLE);


        giveMeTopTenPlayers();

        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Debugging purposes ;)
                addToDb.setVisibility(v.VISIBLE);
                deleteFromDb.setVisibility(v.VISIBLE);
                userText.setVisibility(v.VISIBLE);
                return false;
            }
        });
    }

    private void printDatabase() {
        String databaseString = dbManager.databaseToString();
        highscoreTextView.setText(databaseString);
    }

    private void giveMeTopTenPlayers(){
        String databaseString = dbManager.giveMeBestTenPlayers();
        highscoreTextView.setText(databaseString);
    }

    public void addButtonClicked(View v){
        Scores score = new Scores(userText.getText().toString(),0);
        dbManager.addNewScore(score);;
        userText.setText("");
        giveMeTopTenPlayers();
    }

    public void deleteButtonClicked(View v){
        String name = userText.getText().toString();
        dbManager.deleteScore(name);
        userText.setText("");
        giveMeTopTenPlayers();
    }
}
