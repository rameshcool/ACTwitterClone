package com.rameshcodeworks.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editEmail, editUsername, editPassword;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        ParseInstallation.getCurrentInstallation().saveInBackground();

        editEmail = findViewById(R.id.editEnterEmail);
        editPassword = findViewById(R.id.editEnterPassword);

        editPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                event.getAction() == KeyEvent.ACTION_DOWN) {

                    onClick(btnSignUp);
                }
                return false;
            }
        });

        editUsername = findViewById(R.id.editUsername);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
           // ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }
    }

    private void transitionToSocialMediaActivity() {

        Intent intent = new Intent(MainActivity.this, TwitterUsers.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignUp:
               if (editEmail.getText().toString().equals("") ||
               editUsername.getText().toString().equals("") ||
               editPassword.getText().toString().equals("")) {

                   FancyToast.makeText(MainActivity.this, "Email, Username, Password is Required!!!",
                           Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

               } else {

                   final ParseUser appUser = new ParseUser();
                   appUser.setEmail(editEmail.getText().toString());
                   appUser.setUsername(editUsername.getText().toString());
                   appUser.setPassword(editPassword.getText().toString());

                   final ProgressDialog progressDialog = new ProgressDialog(this);
                   progressDialog.setMessage("Signing Up " + editUsername.getText().toString());
                   progressDialog.show();

                   appUser.signUpInBackground(new SignUpCallback() {
                       @Override
                       public void done(ParseException e) {

                           if (e == null) {

                               FancyToast.makeText(MainActivity.this, appUser.getUsername() + " is signed up",
                                       Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                               transitionToSocialMediaActivity();

                           } else {

                               FancyToast.makeText(MainActivity.this, "There was an error: " + e.getMessage(),
                                       Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                           }

                           progressDialog.dismiss();
                       }
                   });
               }
               break;

            case R.id.btnLogin:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void rootLayoutTapped(View view) {
        try {

            InputMethodManager inputMethodManager =
                    (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
