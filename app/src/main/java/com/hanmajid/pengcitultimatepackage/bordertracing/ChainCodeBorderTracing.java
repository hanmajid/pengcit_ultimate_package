package com.hanmajid.pengcitultimatepackage.bordertracing;

import android.graphics.Color;
import android.util.Log;

import com.hanmajid.pengcitultimatepackage.shared.ChainCode;
import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class ChainCodeBorderTracing implements IBorderTracing {

    private ChainCode[] chainCode;
    private int maxObject;
    private int minChainCodeLength;
    private int maxIteration;

    public ChainCodeBorderTracing(int maxObject, int minChainCodeLength) {
        this.maxObject = maxObject;
        this.minChainCodeLength = minChainCodeLength;
        this.maxIteration = 2000;
    }

    @Override
    public MyImage doBorderTracing(MyImage src) {
        MyImage img = src.clone();

        int width = img.getWidth();
        int height = img.getHeight();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(x == 0 || x == width-1 || y == 0 || y == height-1)
                    img.setColor(MyColor.WHITE, x, y);
            }
        }

        int countObject = 0;
        chainCode = new ChainCode[maxObject];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(countObject < maxObject) {
                    if(img.getColor(x, y) == MyColor.BLACK) {
                        boolean isChecked = false;
                        for(int i = 0; i < countObject; i++) {
                            if(chainCode[i].isInside(x, y, img))
                                isChecked = true;
                        }
                        if(!isChecked) {
                            if(hasNeighbor(img, x, y)) {
                                ChainCode c = generateChainCode(img, x, y);
                                if(c != null)
                                    chainCode[countObject++] = c;
                            }
                        }
                    }
                }
            }
        }

        return img;
    }

    private boolean hasNeighbor(MyImage src, int x, int y) {
        int newX;
        int newY;
        int width = src.getWidth();
        int height = src.getHeight();
        boolean isFound = false;
        for(int dir = 0; dir < 9; dir++) {
            newX = getDirX(dir, x);
            newY = getDirY(dir, y);
            if(newX >= 0 && newX < width && newY >= 0 && newY < height) {
                if(src.getColor(newX, newY) == MyColor.BLACK || src.getColor(newX, newY) == MyColor.RED) {
                    isFound = true;
                }
            }
        }
        return isFound;
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

    private int getNextDir(int dir) {
        int newDir;
        if(dir % 2 == 0) {
            // even
            newDir = (dir + 7) % 8;
        }
        else {
            // odd
            newDir = (dir + 6) % 8;
        }

        return newDir;
    }

    private ChainCode generateChainCode(MyImage img, int x, int y) {
        int width = img.getWidth();
        int height = img.getHeight();
        int currentX = x;
        int currentY = y;

        img.setColor(MyColor.RED, currentX, currentY);

        List<Integer> chains = new ArrayList<>();
        int dir = 7;
        // check neighbour
        dir = getNextDir(dir);
        int count = 1;
        int newX = getDirX(dir, currentX);
        int newY = getDirY(dir, currentY);
        int color = img.getColor(newX, newY);
        while(color != MyColor.BLACK && color != MyColor.RED && count < 8) {
            dir = (dir + 1) % 8;
            newX = getDirX(dir, currentX);
            newY = getDirY(dir, currentY);
            if(newX >= 0 && newX < width && newY >= 0 && newY < height) {
                color = img.getColor(newX, newY);
            }
            count++;
        }
        // found next dir
        currentX = newX;
        currentY = newY;
        chains.add(dir);
        img.setColor(MyColor.RED, currentX, currentY);

        // main loop
        int mainCount = 0;
        while((x != currentX || y != currentY) && mainCount < this.maxIteration) {
            count = 1;
            dir = getNextDir(dir);
            newX = getDirX(dir, currentX);
            newY = getDirY(dir, currentY);
            color = img.getColor(newX, newY);
            while(color != MyColor.BLACK && color != MyColor.RED && count < 8) {
                dir = (dir + 1) % 8;
                newX = getDirX(dir, currentX);
                newY = getDirY(dir, currentY);
                if(newX >= 0 && newX < width && newY >= 0 && newY < height) {
                    color = img.getColor(newX, newY);
                }
                count++;
            }
            // found next dir
            currentX = newX;
            currentY = newY;
            chains.add(dir);
            img.setColor(MyColor.RED, currentX, currentY);
            mainCount++;
        }
        if(chains.size() < this.minChainCodeLength)
            return null;
        Integer[] c = chains.toArray(new Integer[chains.size()]);

        return new ChainCode(c, x, y);
    }
}
