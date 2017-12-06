package com.hanmajid.pengcitultimatepackage.shared;

import android.graphics.Color;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class MyImage {
    private int[][] red;
    private int[][] blue;
    private int[][] green;
    private int width;
    private int height;

    public MyImage(int[][] red, int[][] blue, int[][] green) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.width = red[0].length;
        this.height = red.length;
    }

    public MyImage() {
    }

    public int[][] getRed() {
        return red;
    }

    public void setRed(int[][] red) {
        this.red = red;
    }

    public int[][] getBlue() {
        return blue;
    }

    public void setBlue(int[][] blue) {
        this.blue = blue;
    }

    public int[][] getGreen() {
        return green;
    }

    public void setGreen(int[][] green) {
        this.green = green;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public MyImage clone(MyImage src) {
        MyImage img = new MyImage();
        img.red = src.red.clone();
        img.green = src.green.clone();
        img.blue = src.blue.clone();
        return img;
    }

    public int getColor(int x, int y) {
        return Color.rgb(red[y][x], green[y][x], blue[y][x]);
    }

    public void setColor(int color, int x, int y) {
        red[y][x] = (color >> 16) & 0xFF;
        green[y][x] = (color >> 8) & 0xFF;
        blue[y][x] = color & 0xFF;
    }
}
