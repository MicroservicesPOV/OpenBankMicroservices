package com.OpenBank.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {

@SerializedName("currency")
@Expose
private String currency;
@SerializedName("amount")
@Expose
private String amount;

public String getCurrency() {
return currency;
}

public void setCurrency(String currency) {
this.currency = currency;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

}