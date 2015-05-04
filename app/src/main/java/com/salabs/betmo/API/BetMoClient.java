package com.salabs.betmo.API;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.salabs.betmo.Models.Bet;
import com.salabs.betmo.Models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BetMoClient {

    private static BetMoClient instance = null;
    //private Context mContext;

    private BetMoClientInterface mainClass;


    private BetMoClient() {
        // Exists only to defeat instantiation.
    }

    private BetMoClient(BetMoClientInterface mClass) {
        //this.mContext = context;
        this.mainClass = mClass;
    }

    public static BetMoClient getInstance(BetMoClientInterface mClass) {
        if (instance == null) {
            instance = new BetMoClient(mClass);
        }
        return instance;
    }

    public ArrayList<Bet> allBets = new ArrayList<>();
    public ArrayList<Bet> requestedBets = new ArrayList<>();
    public ArrayList<Bet> feedBets = new ArrayList<>();
    public ArrayList<Bet> openBets = new ArrayList<>();
    public ArrayList<Bet> profileBets = new ArrayList<>();

    public void getAllBets() {

        ParseQuery<Bet> queryBets = ParseQuery.getQuery(Bet.class);
        queryBets.include("owner");
        queryBets.include("opponent");
        queryBets.include("winner");
        queryBets.orderByDescending("updatedAt");
        queryBets.findInBackground(new FindCallback<Bet>() {
            @Override
            public void done(List<Bet> bets, ParseException e) {
                if (e == null) {
                    allBets.clear();
                    allBets.addAll(bets);
                    //Update all the feeds
                    updateAllFeeds();
                    //Call the callback interface function
                    mainClass.getAllBetsCallback();
                    //Log.d("First Bet", allBets.get(0).getOwner().getFirstName());
                } else {
                    Log.d("BetMoError", "Parse Exception while fetching all bets." + e.getMessage());
                }
            }
        });
    }

    private void updateAllFeeds()
    {
        User currentUser = (User) ParseUser.getCurrentUser();
        resetAllCachedBets();
        for (Bet bet : allBets) {
            User owner = bet.getOwner();
            User opponent = bet.getOpponent();
            User winner = bet.getWinner();
            //----------1. Update Requests Feed-------------
            // Requests to Current User
            if (opponent != null && opponent.getFbId().equals(currentUser.getFbId()) && !bet.getIsAccepted()) {
                requestedBets.add(bet);
            }
            // Request from Current User (if there is no opponent or if the opponent hasn't yet accepted)
            if (owner.getFbId().equals(currentUser.getFbId()) && (/*opponent == null ||*/ (opponent != null && !bet.getIsAccepted()))) {
                //BOZO: In the iOS version opponent==null is done. Check with Amoli, why.
                requestedBets.add(bet);
            }

            //----------2. Update Home Feed-------------
            if (winner != null) {
                feedBets.add(bet);
            }

            //----------3. Update Discover Feed-------------
            if (!owner.getFbId().equals(currentUser.getFbId()) && opponent == null) {
                Date lastOpenBetAt = currentUser.getLastOpenBetAt();
                Date betCreatedDate = bet.getCreatedAt();
                if (lastOpenBetAt.before(betCreatedDate)) {
                    //Last open bet action is older than this bet creation
                    openBets.add(bet);
                }
            }

            //----------4. Update Profile Feed-------------
            if (bet.getIsAccepted()) {
                if (owner.getFbId().equals(currentUser.getFbId()) || (opponent != null && opponent.getFbId().equals(currentUser.getFbId()))) {
                    profileBets.add(bet);
                }
            }
        }
    }

    public void resetAllCachedBets() {
        requestedBets.clear();
        feedBets.clear();
        openBets.clear();
        profileBets.clear();
    }


}
