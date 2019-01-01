package com.rameshcodeworks.ac_twitterclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SendTweetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTweet;

    private ListView viewTweetsListView;
    private Button btnViewTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        edtTweet = findViewById(R.id.edtSendTweet);

        viewTweetsListView = findViewById(R.id.viewTweetsListView);
        btnViewTweets = findViewById(R.id.btnSendTweet);
        btnViewTweets.setOnClickListener(this);

//        HashMap<String, Integer> numbers = new HashMap<>();
//        numbers.put("Number1", 1);
//        numbers.put("Number2", 2);
//
//        FancyToast.makeText(this, numbers.get("Number1") + "", Toast.LENGTH_LONG, FancyToast.WARNING, true).show();

    }

    public void sendTweet(View view) {

        ParseObject parseObject = new ParseObject("MyTweet");
        parseObject.put("tweet", edtTweet.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    FancyToast.makeText(SendTweetActivity.this, ParseUser.getCurrentUser().getUsername() + " 's Tweet" + "(" + edtTweet.getText().toString() + ")" + " is Saved!!!",
                            Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                } else {

                    FancyToast.makeText(SendTweetActivity.this, e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR,
                            true).show();
                }

                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
