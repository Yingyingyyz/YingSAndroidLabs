package algonquin.cst2335.zhao0211;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

public class SecondActivity extends AppCompatActivity {

    private ImageView profileImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // get intent data
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("email");
        // set email data
        TextView emailTv = findViewById(R.id.email_tv);
        emailTv.setText("Welcome back" + emailAddress);

        // get number data
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String number = prefs.getString("number", "");
        EditText numberEdit = findViewById(R.id.number_edit);
        numberEdit.setText(number);

        profileImage = findViewById(R.id.profile_image);

        // camera result
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if (data == null) return;
                    Bitmap thumbnail = data.getParcelableExtra("data");
                    profileImage.setImageBitmap(thumbnail);
                }
            }
        });

        // call number click
        findViewById(R.id.call_number).setOnClickListener(v -> {
            // save number to local
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("number", numberEdit.getText().toString());
            editor.apply();


            // call
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + numberEdit.getText().toString()));
            startActivity(call);
        });

        // change picture click
        findViewById(R.id.change_picture).setOnClickListener(v -> {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });
    }
}
