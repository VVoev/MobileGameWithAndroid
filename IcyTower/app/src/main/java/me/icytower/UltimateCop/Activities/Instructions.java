package me.icytower.UltimateCop.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import me.icytower.R;

public class Instructions extends AppCompatActivity {
    Button btn;
    TextView txt;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_instructions);
        btn = (Button) findViewById(R.id.showInstructionsButton);
        txt = (TextView) findViewById(R.id.instructionsLabel);
        iv = (ImageView) findViewById(R.id.instructionsPNG);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String)btn.getText();
                if(text == "Show Instruction"){
                    btn.setText("Hide Instructions");
                    iv.setBackgroundResource(R.drawable.rules);
                }else{
                    btn.setText("Show Instruction");
                    iv.setBackgroundResource(0);
                }
            }

        });

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Debuging purposes ;)
                txt.setText("Hello Vlado,Nice to see you");
                return false;
            }
        });

    }
}
