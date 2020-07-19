package com.example.saksham;

public class Model {
    private int image;
    private String title;
    private String descp;


    public Model(int image, String title, String descp) {
        this.image = image;
        this.title = title;
        this.descp = descp;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
