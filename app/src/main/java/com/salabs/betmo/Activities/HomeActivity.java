package com.salabs.betmo.Activities;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import com.salabs.betmo.Fragments.BetsFeedFragment;
import com.salabs.betmo.Models.Bet;
import com.salabs.betmo.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements BetsFeedFragment.OnBetsFeedFragmentListener {

    @InjectView(R.id.feedPlaceholderFrame) FrameLayout feedPlaceholderFrame;
    @InjectView(R.id.homeTab) LinearLayout homeTab;
    @InjectView(R.id.discoverTab) LinearLayout discoverTab;
    @InjectView(R.id.newBetTab) LinearLayout newBetTab;
    @InjectView(R.id.requestsTab) LinearLayout requestsTab;
    @InjectView(R.id.profileTab) LinearLayout profileTab;

    private ArrayList<Bet> allBets;

    private ArrayList<BetsFeedFragment> allFeedFragments;
    private BetsFeedFragment homeFeedFragment;
    private BetsFeedFragment requestsFeedFragment;
    private BetsFeedFragment profileFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        loadUserData();

        if (savedInstanceState == null) {
            homeFeedFragment = BetsFeedFragment.newInstance(R.id.homeTab);
            requestsFeedFragment = BetsFeedFragment.newInstance(R.id.requestsTab);
            profileFeedFragment = BetsFeedFragment.newInstance(R.id.profileTab);
            allFeedFragments = new ArrayList<>();
            allFeedFragments.add(homeFeedFragment);
            allFeedFragments.add(requestsFeedFragment);
            allFeedFragments.add(profileFeedFragment);
            displayFragment(homeFeedFragment);
        }

    }

    @OnClick({R.id.homeTab, R.id.discoverTab, R.id.newBetTab, R.id.requestsTab, R.id.profileTab})
    public void onTabClick (View view) {
        switch (view.getId()) {
            case R.id.homeTab:
                displayFragment(homeFeedFragment);
                break;
            case R.id.discoverTab:
                //ft.add(R.id.feedPlaceholderFrame, new BetsFeedFragment().newInstance(R.id.discoverTab));
                //ft.commit();
                break;
            case R.id.newBetTab:
                //ft.add(R.id.feedPlaceholderFrame, new BetsFeedFragment().newInstance(R.id.newBetTab));
                //ft.commit();
                break;
            case R.id.requestsTab:
                displayFragment(requestsFeedFragment);
                break;
            case R.id.profileTab:
                displayFragment(profileFeedFragment);
                break;
        }
    }

    protected void displayFragment(BetsFeedFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) { // if the fragment is already in container
            ft.show(fragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.feedPlaceholderFrame, fragment, "m"+fragment);
        }

        //Hide all other fragments
        for (BetsFeedFragment frag : allFeedFragments) {
            if (!frag.equals(fragment) && frag.isAdded()) {
                ft.hide(frag);
            }
        }
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    //BOZO: Need to figure out a better place to do this in order to slim down this activity
    private void loadUserData() {
        GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            // callback after Graph API response with user object
            @Override
            public void onCompleted(JSONObject user, GraphResponse response) {
                if (user != null) {
                    ParseUser.getCurrentUser().put("fbId", user.optString("id"));
                    ParseUser.getCurrentUser().put("firstName", user.optString("first_name"));
                    ParseUser.getCurrentUser().put("lastName", user.optString("last_name"));
                    ParseUser.getCurrentUser().put("email", user.optString("email"));
                    String fbProfileImageUrl = "https://graph.facebook.com/" + user.optString("id") + "/picture?type=large&return_ssl_resources=1";
                    ParseUser.getCurrentUser().put("profileImageUrl", fbProfileImageUrl);

                    ParseUser.getCurrentUser().put("searchName", user.optString("first_name").toLowerCase(Locale.ENGLISH) + user.optString("last_name").toLowerCase(Locale.ENGLISH));

                    //BOZO: Enable this
                    //if (ParseUser.getCurrentUser().getLastOpenBetAt() == null) {
                        //Set a date twenty years from current date
                    //}

                    String URL = "https://graph.facebook.com/" + user.optString("id") + "?fields=cover&access_token=" + AccessToken.getCurrentAccessToken().getToken();
                    Log.d("FBJSON", URL);
                    ParseUser.getCurrentUser().saveInBackground();
                }
                else {
                    Log.d("SA ERROR:", "Could not get user on meRequest");
                }
            }
        }).executeAsync();

        // Associate the device with a user
        ParseInstallation.getCurrentInstallation().put("user", ParseUser.getCurrentUser());
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }



    @Override
    public void onBetsFeedFragment(Uri uri) {

    }
}
