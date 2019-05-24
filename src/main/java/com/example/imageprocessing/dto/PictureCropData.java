package com.example.imageprocessing.dto;

//POJO - bez interefjsów, bez dziedziczenia bez adnotacji, zmienne prywatne, publiczna klasa, publiczne gettery i settery, konstruktor domyślny
public class PictureCropData extends PictureSize {

    private int x;
    private int y;

    public PictureCropData(int x, int y, int h, int w) {
        super(h, w);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
