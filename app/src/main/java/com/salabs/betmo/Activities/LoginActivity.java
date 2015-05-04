package com.salabs.betmo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.salabs.betmo.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.loginButton) LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        onLoginButton(); //BOZO: Remove this

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButton();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void gotoHomeActivity() {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    public void onLoginButton() {

        final List<String> permissions = new ArrayList<String>();
        permissions.add("public_profile");
        //permissions.add("user_status");
        permissions.add("email");
        permissions.add("user_friends");

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {

            @Override
            public void done(ParseUser parseUser, com.parse.ParseException e) {

                if (parseUser == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (parseUser.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    gotoHomeActivity();
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    gotoHomeActivity();
                }
            }
        });
    }

}
