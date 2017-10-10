package me.icytower.UltimateCop.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import me.icytower.UltimateCop.Core.Constants;
import me.icytower.R;

public class YourDetails extends AppCompatActivity {

    private Button cameraButton;
    private ImageView iv;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_details);

        cameraButton = (Button) findViewById(R.id.yourDetails);
        iv = (ImageView) findViewById(R.id.yourPicture);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        iv.setImageBitmap(bitmap);
    }

    public void saveName(View view){
        EditText userInput = (EditText)findViewById(R.id.yourName);
        String yourName = userInput.getText().toString();
        userInput.setText("");
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("NAME",yourName);
        Constants.PLAYER_NAME = yourName;
        startActivity(intent);
    }
}
