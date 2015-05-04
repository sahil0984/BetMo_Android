package com.salabs.betmo;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.salabs.betmo.Models.Bet;
import com.salabs.betmo.Models.User;

/**
 * Created by Sahil on 4/27/2015.
 */
public class BetMoApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        BetMoApplication.context = this;

        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Bet.class);


        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "HLJvIWanJTZ4VmdUarEbbUPxpR9eYsaUNFiyodKe", "4umyNwPmXgax1yNsvEWizzsaOt7C5ryuHvIzI4Zu");

        //FacebookSdk.sdkInitialize(context);
        ParseFacebookUtils.initialize(context);
    }
}
