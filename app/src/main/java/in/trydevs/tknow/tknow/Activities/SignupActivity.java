package in.trydevs.tknow.tknow.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;

import in.trydevs.tknow.tknow.R;

public class SignupActivity extends AppCompatActivity {
    FormEditText formEditText[];
    Button signUp, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
    }

    private void initialize() {
        // Creating Edit text and instantiating them
        formEditText = new FormEditText[4];
        formEditText[0] = (FormEditText) findViewById(R.id.editTextName);
        formEditText[1] = (FormEditText) findViewById(R.id.editTextEmail);
        formEditText[2] = (FormEditText) findViewById(R.id.editTextPassword);
        formEditText[3] = (FormEditText) findViewById(R.id.editTextRollNo);
        // Creating and instantiating Buttons
        signUp = (Button) findViewById(R.id.buttonSignUp);
        reset = (Button) findViewById(R.id.buttonReset);

        // Creating click listeners for buttons
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (FormEditText editText : formEditText) {
                    editText.setText("");
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allValid = true;
                for (FormEditText editText : formEditText) {
                    allValid = editText.testValidity() && allValid;
                }
                if (allValid) {
                    Toast.makeText(SignupActivity.this, "sdadas", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
