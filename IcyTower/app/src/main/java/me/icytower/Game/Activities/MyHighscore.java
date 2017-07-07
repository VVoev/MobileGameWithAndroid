package me.icytower.Game.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.icytower.Game.Db.DbManager;
import me.icytower.Game.Db.Scores;
import me.icytower.R;

public class MyHighscore extends AppCompatActivity {
    private TextView highscoreTextView;
    private DbManager dbManager;
    private EditText userText;

    private Button addToDb;
    private Button deleteFromDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_highscore);

        highscoreTextView = (TextView) findViewById(R.id.highScoreTextView);
        userText = (EditText)findViewById(R.id.userText);
        addToDb = (Button)findViewById(R.id.addToDb);
        deleteFromDb = (Button)findViewById(R.id.deleteFromDb);

        dbManager = new DbManager(this, null, null, 1);
        printDatabase();
    }

    public void addButtonClicked(View v){
        Scores score = new Scores(userText.getText().toString());
        dbManager.addNewScore(score);;
        printDatabase();
    }

    private void printDatabase() {
        String databaseString = dbManager.databaseToString();
        highscoreTextView.setText(databaseString);
    }

}
