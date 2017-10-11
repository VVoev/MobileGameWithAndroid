package me.icytower.UltimateCop.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import me.icytower.R;

public class Instructions extends AppCompatActivity {
    final String showInst = "Show Instruction";
    final String hideInst = "Hide Instructions";
    final String debug = "Debugging";

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
                String text = (String) btn.getText();
                if (text == showInst) {
                    btn.setText(hideInst);
                    iv.setBackgroundResource(R.drawable.rules);
                } else {
                    btn.setText(showInst);
                    iv.setBackgroundResource(0);
                }
            }
        });

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            //Debugging purposes ;)
            public boolean onLongClick(View v) {
                txt.setText(debug);
                return false;
            }
        });

    }
}
