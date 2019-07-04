package com.example.uhpdigiital;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyModel {

    @SerializedName("selling_rate")
    @Expose
    private String sellingRate;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("buying_rate")
    @Expose
    private String buyingRate;

    public String getSellingRate() {
        return sellingRate;
    }
    public String getBuyingRate() {
        return buyingRate;
    }

    @Override
    public String toString() {
        return currencyCode ;
    }
}
