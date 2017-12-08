package com.hanmajid.pengcitultimatepackage.histogram;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.hanmajid.pengcitultimatepackage.shared.Distribution;
import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: pengcit_ultimate_package
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/8/2017.
 */

public class MyHistogram implements IHistogram {
    @Override
    public void drawColorHistogram(LineChart chart, Distribution distribution) {
        List<Entry> entriesRed = new ArrayList<Entry>();
        List<Entry> entriesGreen = new ArrayList<Entry>();
        List<Entry> entriesBlue = new ArrayList<Entry>();
        List<Entry> entriesIntensity = new ArrayList<Entry>();

        for (int i = 0; i < distribution.getLength(); i++) {
            // turn your data into Entry objects
            entriesRed.add(new Entry(i, distribution.getRed(i)));
            entriesGreen.add(new Entry(i, distribution.getGreen(i)));
            entriesBlue.add(new Entry(i, distribution.getBlue(i)));
            entriesIntensity.add(new Entry(i, distribution.getIntensity(i)));
        }
        LineDataSet dataSetRed = new LineDataSet(entriesRed, "Red");
        dataSetRed.setColor(MyColor.RED);
        dataSetRed.setDrawCircles(false);
        LineDataSet dataSetGreen = new LineDataSet(entriesGreen, "Green");
        dataSetGreen.setColor(MyColor.GREEN);
        dataSetGreen.setDrawCircles(false);
        LineDataSet dataSetBlue = new LineDataSet(entriesBlue, "Blue");
        dataSetBlue.setColor(MyColor.BLUE);
        dataSetBlue.setDrawCircles(false);
        LineDataSet dataSetIntesity = new LineDataSet(entriesIntensity, "Intesity");
        dataSetIntesity.setColor(MyColor.GRAY);
        dataSetIntesity.setDrawCircles(false);

        LineData data = new LineData();
        data.addDataSet(dataSetRed);
        data.addDataSet(dataSetGreen);
        data.addDataSet(dataSetBlue);
        data.addDataSet(dataSetIntesity);
        chart.setData(data);
        chart.invalidate(); // refresh
    }

    @Override
    public Distribution equalizeHistogram(BarChart chart) {
        return null;
    }

    @Override
    public Distribution countDistribution(MyImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        int[] red = new int[256];
        int[] green = new int[256];
        int[] blue = new int[256];
        int[] intensity = new int[256];
        for(int i = 0; i < 256; i++) {
            red[i] = 0;
            green[i] = 0;
            blue[i] = 0;
            intensity[i] = 0;
        }

        int r, g, b, intens;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                r = src.getRed(x, y);
                g = src.getGreen(x, y);
                b = src.getBlue(x, y);
                intens = (r+g+b) / 3;
                red[r]++;
                green[g]++;
                blue[b]++;
                intensity[intens]++;
            }
        }

        return new Distribution(256, red, green, blue, intensity);
    }

    @Override
    public Distribution countCummulativeDistribution(MyImage src) {
        Distribution distribution = countDistribution(src);

        for(int i = 1; i < distribution.getLength(); i++) {
            distribution.setRed(i, distribution.getRed(i) + distribution.getRed(i-1));
            distribution.setGreen(i, distribution.getGreen(i) + distribution.getGreen(i-1));
            distribution.setBlue(i, distribution.getBlue(i) + distribution.getBlue(i-1));
            distribution.setIntensity(i, distribution.getIntensity(i) + distribution.getIntensity(i-1));
        }

        return distribution;
    }
}
