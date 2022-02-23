package com.example.zatch.navigation.my_zatch;

public class Data {

    private String myitem;
    private String yours;
    private int resId;

    public String getMyitem(){
        return myitem;
    }
    public void setMyitem(String myitem) {
        this.myitem = myitem;
    }

    public String getYours(){ return yours; }
    public void setYours(String yours) {this.yours = yours;}

    public int getResId() { return resId; }
    public void setResId(int resId){ this.resId = resId;}


    private String gatch_item;
    private String current;
    private String target;
    private int price_per_person;

    public String getGatch_item() {return gatch_item;}
    public void setGatch_item(String gatch_item) {this.gatch_item = gatch_item; }

    public String getCurrent() {return current;}
    public void setCurrent(String current) {this.current = current;}

    public String getTarget() {return target;}
    public void setTarget(String target) {this.target = target;}

    public int getPrice_per_person() {return price_per_person;}
    public void setPrice_per_person(Integer price_per_person) {this.price_per_person = price_per_person;}
}
