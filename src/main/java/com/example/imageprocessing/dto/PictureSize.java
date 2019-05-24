package com.example.imageprocessing.dto;

public class PictureSize {

    private int height;
    private int width;

    public PictureSize(int h, int w) {
        height = h;
        width = w;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
