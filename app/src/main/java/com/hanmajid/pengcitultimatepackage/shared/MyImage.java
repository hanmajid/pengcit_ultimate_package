package com.hanmajid.pengcitultimatepackage.shared;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.lang.reflect.Array;
import java.util.Arrays;

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
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public MyImage() {
    }

    public MyImage(int[][] red, int[][] green, int[][] blue, Bitmap bitmap) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.width = red[0].length;
        this.height = red.length;
        this.bitmap = bitmap;
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

    public MyImage clone() {
        MyImage img = new MyImage();
        img.red = new int[this.height][this.width];
        img.green = new int[this.height][this.width];
        img.blue = new int[this.height][this.width];
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                img.red[y][x] = this.red[y][x];
                img.green[y][x] = this.green[y][x];
                img.blue[y][x] = this.blue[y][x];
            }
        }
        img.width = this.width;
        img.height = this.height;
        img.bitmap = this.bitmap.copy(this.bitmap.getConfig(), true);
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

    public int getRed(int x, int y) {
        return red[y][x];
    }

    public int getBlue(int x, int y) {
        return blue[y][x];
    }

    public int getGreen(int x, int y) {
        return green[y][x];
    }
}
