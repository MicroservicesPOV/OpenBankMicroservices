package com.OpenBank.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateAccount {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("label")
@Expose
private String label;
@SerializedName("type")
@Expose
private String type;
@SerializedName("balance")
@Expose
private Balance balance;
@SerializedName("branch_id")
@Expose
private String branchId;
@SerializedName("account_routing")
@Expose
private AccountRouting accountRouting;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getLabel() {
return label;
}

public void setLabel(String label) {
this.label = label;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Balance getBalance() {
return balance;
}

public void setBalance(Balance balance) {
this.balance = balance;
}

public String getBranchId() {
return branchId;
}

public void setBranchId(String branchId) {
this.branchId = branchId;
}

public AccountRouting getAccountRouting() {
return accountRouting;
}

public void setAccountRouting(AccountRouting accountRouting) {
this.accountRouting = accountRouting;
}

}