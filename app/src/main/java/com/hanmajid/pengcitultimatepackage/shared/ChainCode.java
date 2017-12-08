package com.hanmajid.pengcitultimatepackage.shared;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 * <p>
 * Created on 12/6/2017.
 */

public class ChainCode {
    Integer[] chain;
    int x;
    int y;
    int minX;
    int maxX;
    int minY;
    int maxY;

    public ChainCode() {
    }

    public ChainCode(Integer[] chain, int x, int y) {
        this.chain = chain;
        this.x = x;
        this.y = y;
        setMinMax();
    }

    public Integer[] getChain() {
        return chain;
    }

    public void setChain(Integer[] chain) {
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

    public boolean isInside(int x, int y, MyImage src) {
        if(x >= minX && x <= maxX && y >= minY && y <= maxY) {
            boolean inside = false;
            boolean inBlack = false;
            for(int i=0; i<src.getWidth()-1; i++) {
                if(src.getColor(i, y) == MyColor.RED && src.getColor(i+1, y) == MyColor.WHITE) {
                    inBlack = false;
                }
                if(src.getColor(i, y) == MyColor.RED && src.getColor(i+1, y) == MyColor.BLACK) {
                    inBlack = true;
                }
                if(i == x && inBlack) {
                    inside = true;
                }
            }
            return inside;
        }
        return false;
    }

    public void setMinMax() {
        int currentX = getDirX(chain[0], x);
        int currentY = getDirY(chain[0], y);
        minX = currentX;
        maxX = currentX;
        minY = currentY;
        maxY = currentY;
        for(int i=1; i<chain.length; i++) {
            currentX = getDirX(chain[i], currentX);
            currentY = getDirY(chain[i], currentY);
            if(currentX < minX)
                minX = currentX;
            if(currentX > maxX)
                maxX = currentX;
            if(currentY < minY)
                minY = currentY;
            if(currentY > maxY)
                maxY = currentY;
        }
    }

    private int getDirX(int dir, int x) {
        if(dir == 3 || dir == 4 || dir == 5) {
            return x-1;
        }
        else if(dir == 1 || dir == 0 || dir == 7) {
            return x+1;
        }
        return x;
    }

    private int getDirY(int dir, int y) {
        if(dir == 1 || dir == 2 || dir == 3) {
            return y-1;
        }
        else if(dir == 5 || dir == 6 || dir == 7) {
            return y+1;
        }
        return y;
    }
}
