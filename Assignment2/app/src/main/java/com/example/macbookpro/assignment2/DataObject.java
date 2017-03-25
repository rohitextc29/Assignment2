package com.example.macbookpro.assignment2;

public class DataObject {
    private String mText1;
    private String mText2;

    public String getPic_text() {
        return pic_text;
    }

    public void setPic_text(String pic_text) {
        this.pic_text = pic_text;
    }

    private String pic_text;

    DataObject(String text1, String text2, String pic_text){
        mText1 = text1;
        mText2 = text2;
        this.pic_text = pic_text;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}