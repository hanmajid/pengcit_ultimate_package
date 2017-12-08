package com.hanmajid.pengcitultimatepackage.shared;

/**
 * Project: pengcit_ultimate_package
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/8/2017.
 */

public class Distribution {
    int length;
    int[] red;
    int[] green;
    int[] blue;
    int[] intensity;

    public Distribution(int length, int[] red, int[] green, int[] blue, int[] intensity) {
        this.length = length;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.intensity = intensity;
    }

    public Distribution() {
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getRed() {
        return red;
    }

    public void setRed(int[] red) {
        this.red = red;
    }

    public int[] getGreen() {
        return green;
    }

    public void setGreen(int[] green) {
        this.green = green;
    }

    public int[] getBlue() {
        return blue;
    }

    public void setBlue(int[] blue) {
        this.blue = blue;
    }

    public int[] getIntensity() {
        return intensity;
    }

    public void setIntensity(int[] intensity) {
        this.intensity = intensity;
    }

    public int getRed(int i) {
        return red[i];
    }

    public int getGreen(int i) {
        return green[i];
    }

    public int getBlue(int i) {
        return blue[i];
    }

    public int getIntensity(int i) {
        return intensity[i];
    }

    public void setRed(int i, int r) {
        red[i] = r;
    }

    public void setGreen(int i, int g) {
        green[i] = g;
    }

    public void setBlue(int i, int b) {
        blue[i] = b;
    }

    public void setIntensity(int i, int intens) {
        intensity[i] = intens;
    }
}
