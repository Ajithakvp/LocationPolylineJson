package com.example.locationpolylinejson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GoogleLocationResponse {


    @SerializedName("location")
    private ArrayList<GoogleLocationModel>googleLocationModels=new ArrayList<>();

    public ArrayList<GoogleLocationModel> getGoogleLocationModels() {
        return googleLocationModels;
    }

    public void setGoogleLocationModels(ArrayList<GoogleLocationModel> googleLocationModels) {
        this.googleLocationModels = googleLocationModels;
    }
}
