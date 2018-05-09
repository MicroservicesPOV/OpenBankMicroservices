package com.OpenBank.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountRouting {

@SerializedName("scheme")
@Expose
private String scheme;
@SerializedName("address")
@Expose
private String address;

public String getScheme() {
return scheme;
}

public void setScheme(String scheme) {
this.scheme = scheme;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

}