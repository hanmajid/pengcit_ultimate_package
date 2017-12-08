package com.hanmajid.pengcitultimatepackage.shared;

/**
 * Project: pengcit_ultimate_package
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/8/2017.
 */

public class ValueMapping {
    int[] oldValue;
    int[] newValue;
    int length;

    public ValueMapping(int[] oldValue, int[] newValue, int length) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.length = length;
    }

    public ValueMapping() {
    }

    public int[] getOldValue() {
        return oldValue;
    }

    public void setOldValue(int[] oldValue) {
        this.oldValue = oldValue;
    }

    public int[] getNewValue() {
        return newValue;
    }

    public void setNewValue(int[] newValue) {
        this.newValue = newValue;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getOldValue(int i) {
        return oldValue[i];
    }

    public void setOldValue(int i, int value) {
        this.oldValue[i] = value;
    }

    public int getNewValue(int i) {
        return newValue[i];
    }

    public void setNewValue(int i, int value) {
        this.newValue[i] = value;
    }
}
