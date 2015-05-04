package com.salabs.betmo.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.salabs.betmo.Adapters.BetArrayAdapter;
import com.salabs.betmo.API.BetMoClient;
import com.salabs.betmo.API.BetMoClientInterface;
import com.salabs.betmo.Models.Bet;
import com.salabs.betmo.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class BetsFeedFragment extends android.support.v4.app.Fragment implements BetMoClientInterface {

    @InjectView(R.id.lvBetsFeed) ListView lvBetsFeed;

    private static final String FRAGMENT_BUNDLE_KEY = "com.salabs.betmo.FRAGMENT_BUNDLE_KEY";
    private int tabViewId;

    private ArrayList<Bet> bets;
    private ArrayAdapter<Bet> aBets;

    private OnBetsFeedFragmentListener mListener;

    public static BetsFeedFragment newInstance(int viewId) {
        BetsFeedFragment fragment = new BetsFeedFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_BUNDLE_KEY, viewId);
        fragment.setArguments(args);

        return fragment;
    }

    public BetsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            bets = new ArrayList<Bet>();
            aBets = new BetArrayAdapter(getActivity(), R.layout.bet_item, bets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bets_feed, container, false);
        ButterKnife.inject(this, view);

        lvBetsFeed.setAdapter(aBets);

        if (getArguments() != null) {
            tabViewId = getArguments().getInt(FRAGMENT_BUNDLE_KEY);

            switch (tabViewId) {
                case R.id.homeTab:
                    BetMoClient.getInstance(this).getAllBets();
                    break;
                case R.id.requestsTab:
                    //SAif (BetMoClient.getInstance(this).requestedBets.size() > 0)
                        getAllBetsCallback();
                    break;
                case R.id.profileTab:
                    //SAif (BetMoClient.getInstance(this).profileBets.size() > 0)
                        getAllBetsCallback();
                    break;
            }
        }
        return view;
    }

    @Override
    public void getAllBetsCallback() {
        switch (tabViewId) {
            case R.id.homeTab:
                aBets.clear();
                aBets.addAll(BetMoClient.getInstance(this).feedBets);
                //SALog.d("First Bet", aBets.getItem(0).getOwner().getFirstName());
                break;
            case R.id.requestsTab:
                aBets.clear();
                aBets.addAll(BetMoClient.getInstance(this).requestedBets);
                //SALog.d("First Bet", aBets.getItem(0).getOwner().getFirstName());
                break;
            case R.id.profileTab:
                aBets.clear();
                aBets.addAll(BetMoClient.getInstance(this).profileBets);
                //SALog.d("First Bet", aBets.getItem(0).getOwner().getFirstName());
                break;
        }
        Log.d("BetMo message", "Made it to the callback with " + aBets.getCount() + " bets");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onBetsFeedFragment(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBetsFeedFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBetsFeedFragmentListener {
        // TODO: Update argument type and name
        public void onBetsFeedFragment(Uri uri);
    }

}
