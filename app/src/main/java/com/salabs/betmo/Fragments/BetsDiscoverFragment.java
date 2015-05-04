package com.salabs.betmo.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salabs.betmo.API.BetMoClient;
import com.salabs.betmo.API.BetMoClientInterface;
import com.salabs.betmo.Models.Bet;
import com.salabs.betmo.R;

import java.util.ArrayList;

public class BetsDiscoverFragment extends Fragment implements BetMoClientInterface {

    private ArrayList<Bet> bets;

    public static BetsDiscoverFragment newInstance() {
        BetsDiscoverFragment fragment = new BetsDiscoverFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    public BetsDiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bets = new ArrayList<Bet>();

        //if (getArguments() != null) {
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bets.addAll(BetMoClient.getInstance(this).feedBets);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bets_discover, container, false);
    }

    @Override
    public void getAllBetsCallback(){
    }

}
