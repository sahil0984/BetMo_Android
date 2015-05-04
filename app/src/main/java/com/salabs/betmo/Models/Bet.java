package com.salabs.betmo.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Bet")
public class Bet extends ParseObject {

    // Ensure that your subclass has a public default constructor
    public Bet() {
        super();
    }
    // Add a constructor that contains core properties
    public Bet(User owner) {
        super();
        setOwner(owner);
    }

    // Getter and Setters for all Bet properties

    public User getOwner() {
        return (User) getParseUser("owner");
    }
    public void setOwner(User owner) {
        put("owner", owner);
    }

    public User getOpponent() {
        return (User) getParseUser("opponent");
    }
    public void setOpponent(User opponent) {
        put("opponent", opponent);
    }

    public User getWinner() {
        return (User) getParseUser("winner");
    }
    public void setWinner(User winner) {
        put("winner", winner);
    }

    public String getDescription() {
        return getString("description");
    }
    public void setDescription(String description) {
        put("description", description);
    }

    public String getAmount() {
        return getString("amount");
    }
    public void setAmount(String amount) {
        put("amount", amount);
    }

    public Boolean getIsAccepted() {
        return getBoolean("isAccepted");
    }
    public void setIsAccepted(String isAccepted) {
        put("isAccepted", isAccepted);
    }


}
