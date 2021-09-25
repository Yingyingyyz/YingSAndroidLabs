package algonquin.cst2335.zhao0211;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import  android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declare the text view
        TextView topView = findViewById(R.id.helloTextview);
        //declare the button
        Button btn = findViewById(R.id.button);
        //declare the editText
        EditText myedit = findViewById(R.id.myedittext);


//        btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                String abc = myedit.getText().toString();
//                myedit.setText("Your edit text has:" + abc);
//            }
//        });

        btn.setOnClickListener(    (View v) -> {
            String abc = myedit.getText().toString();
            myedit.setText("Your edit text has:" + abc);
        }   );

        CheckBox cb1 = findViewById(R.id.checkboxButton1);
        cb1.setOnCheckedChangeListener((checkbox, isChecked) -> {
            Toast.makeText(this, "You clicked on the CheckBox and it is now: " + isChecked, Toast.LENGTH_LONG).show();
        });

        CheckBox cb2 = findViewById(R.id.checkboxButton2);
        cb2.setOnCheckedChangeListener((checkbox, isChecked) -> {
            Toast.makeText(this, "You clicked on the CheckBox and it is now: " + isChecked, Toast.LENGTH_LONG).show();
        });

        CheckBox cb3 = findViewById(R.id.checkboxButton3);
        cb3.setOnCheckedChangeListener((checkbox, isChecked) -> {
            Toast.makeText(this, "You clicked on the CheckBox and it is now: " + isChecked, Toast.LENGTH_LONG).show();
        });

        Switch sw1 = findViewById(R.id.switchButton1);
        sw1.setOnCheckedChangeListener((switch1, isChecked) -> {
            Toast.makeText(this, "You clicked on the Switch and it is now: " + isChecked, Toast.LENGTH_SHORT).show();
        });

        Switch sw2 = findViewById(R.id.switchButton2);
        sw2.setOnCheckedChangeListener((switch2, isChecked) -> {
            Toast.makeText(this, "You clicked on the Switch and it is now: " + isChecked, Toast.LENGTH_SHORT).show();
        });

        RadioButton radio1 = findViewById(R.id.radioButton1);
        radio1.setOnCheckedChangeListener((radiobutton, isChecked) -> {
            Toast.makeText(this, "You clicked on the RadioButton and it is now: " + isChecked, Toast.LENGTH_SHORT).show();
        });

        RadioButton radio2 = findViewById(R.id.radioButton2);
        radio2.setOnCheckedChangeListener((radiobutton, isChecked) -> {
            Toast.makeText(this, "You clicked on the RadioButton and it is now: " + isChecked, Toast.LENGTH_SHORT).show();
        });

        ImageView img = findViewById(R.id.logo_algonquin);
        ImageButton imgBt = findViewById(R.id.myimagebutton);
//        ibtn.setOnClickListener( (vw) -> {
//            int width = ibtn.getWidth();
//            int height = vw.getHeight();
//        });
        imgBt.setOnClickListener(vw -> {
            Toast.makeText(this, "The width = " + vw.getWidth() + " and height = " + vw.getHeight(), Toast.LENGTH_SHORT).show();
        });












    }
}