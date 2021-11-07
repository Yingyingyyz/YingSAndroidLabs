package algonquin.cst2335.zhao0211;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import  android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    private boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
    /** This holds the text at the center of the screen  */
    private TextView tv = null;
    /** This holds the edittext the user input */
    private EditText et = null;
    /** This holds the button the login */
    private Button btn = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        et = findViewById(R.id.edittext);
        tv = findViewById(R.id.textview);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();
            boolean complexity = checkPasswordComplexity(password);
            if (complexity){
                tv.setText("Your password meets the requirements");
            }else {
                tv.setText("You shall not pass!");
            }
        });
    }

    /**
     * @param pw The String object that we are checking
     * @return Return true password is complexity false password is simple
     */
    private boolean checkPasswordComplexity(String pw) {
        for (int i = 0; i < pw.length(); i++) {
            char character = pw.charAt(i);
            boolean foundNumber = Character.isDigit(character);
            if (foundNumber) {
                this.foundNumber = foundNumber;
            }
            boolean foundUpperCase = Character.isUpperCase(character);
            if (foundUpperCase) {
                this.foundUpperCase = foundUpperCase;
            }
            boolean foundLowerCase = Character.isLowerCase(character);
            if (foundLowerCase) {
                this.foundLowerCase = foundLowerCase;
            }
            boolean foundSpecial = isSpecialCharacter(character);
            if (foundSpecial) {
                this.foundSpecial = foundSpecial;
            }
        }

        if (!foundUpperCase) {
            Toast.makeText(this, "missing an upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "missing a lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "missing a number letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "missing a special Character letter", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param c Type the characters
     * @return Return true  SpecialCharacter yes false SpecialCharacter no
     */
    private boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '?':
            case '*':
                return true;
            default:
                return false;
        }
    }
}
