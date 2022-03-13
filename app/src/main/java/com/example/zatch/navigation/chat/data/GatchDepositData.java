package com.example.zatch.navigation.chat.data;

public class GatchDepositData {

    private String bankName;
    private int bankAccount;
    private String accountOwner;
    private int pricePerPeson;
    private String moreInfo;

    public GatchDepositData(String bankName, int bankAccount, String accountOwner, int pricePerPeson, String moreInfo) {
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.accountOwner = accountOwner;
        this.pricePerPeson = pricePerPeson;
        this.moreInfo = moreInfo;
    }

    public GatchDepositData(String bankName, String bankAccount, String accountOwner, String pricePerPeson, String moreInfo) {
        this.bankName = bankName;
        this.bankAccount = Integer.parseInt(bankAccount);
        this.accountOwner = accountOwner;
        this.pricePerPeson = Integer.parseInt(pricePerPeson);
        this.moreInfo = moreInfo;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAccount() {
        return String.valueOf(bankAccount);
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public String getPricePerPeson() {
        return String.valueOf(pricePerPeson);
    }

    public String getMoreInfo() {
        return moreInfo;
    }
}
