package com.salabs.betmo.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.salabs.betmo.Models.Bet;
import com.salabs.betmo.Models.User;
import com.salabs.betmo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class BetArrayAdapter extends ArrayAdapter {
    private Context context;


    public BetArrayAdapter(Context context, int resource, List<Bet> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bet bet = (Bet) getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bet_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(bet.getOwner().getProfileImageUrl()).into(holder.ivOwnerProfileImage);
        holder.tvOwnerName.setText(bet.getOwner().getName());
        if (bet.getOpponent() != null) {
            Picasso.with(context).load(bet.getOpponent().getProfileImageUrl()).into(holder.ivOpponentProfileImage);
            holder.tvOpponentName.setText(bet.getOpponent().getName());
        }

        holder.tvAmount.setText("$"+bet.getAmount());
        holder.tvDescription.setText(bet.getDescription());

        //Log.d("BetMo", bet.getOwner().getProfileImageUrl());

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.tvOwnerName) TextView tvOwnerName;
        @InjectView(R.id.tvOpponentName) TextView tvOpponentName;

        @InjectView(R.id.ivOwnerProfileImage) ImageView ivOwnerProfileImage;
        @InjectView(R.id.ivOpponentProfileImage) ImageView ivOpponentProfileImage;

        @InjectView(R.id.tvAmount) TextView tvAmount;
        @InjectView(R.id.tvDescription) TextView tvDescription;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}


