package algonquin.cst2335.zhao0211;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText emailEdt = findViewById(R.id.email_edit);

        // get local data
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");
        // set email
        emailEdt.setText(email);

        // login button click
        findViewById(R.id.login_btn).setOnClickListener(v -> {
            // save to local
            String emailStr = emailEdt.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", emailStr);
            editor.apply();


            // to SecondActivity
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("email", emailStr);
            startActivity(nextPage);
        });
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.w( "MainActivity", "In onStart() - The application is now visible on screen" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( "MainActivity", "In onResume() - The application is now responding to user input" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( "MainActivity", "In onPause()- The application no longer responds to user input" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( "MainActivity", "In onStop() - The application is no longer visible" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( "MainActivity", "onDestroy() - Any memory used by the application is freed" );
    }
}