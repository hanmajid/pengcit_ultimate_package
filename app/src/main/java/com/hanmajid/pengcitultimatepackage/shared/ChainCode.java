package com.hanmajid.pengcitultimatepackage.shared;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 * <p>
 * Created on 12/6/2017.
 */

public class ChainCode {
    int[] chain;
    int x;
    int y;

    public ChainCode() {
    }

    public ChainCode(int[] chain, int x, int y) {
        this.chain = chain;
        this.x = x;
        this.y = y;
    }

    public int[] getChain() {
        return chain;
    }

    public void setChain(int[] chain) {
        this.chain = chain;
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

    public boolean isInside(int x, int y) {
        
        return false;
    }
}
