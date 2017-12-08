package com.hanmajid.pengcitultimatepackage.histogram;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.hanmajid.pengcitultimatepackage.shared.Distribution;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;
import com.hanmajid.pengcitultimatepackage.shared.ValueMapping;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public interface IHistogram {
    public void drawColorHistogram(LineChart chart, Distribution distribution);

    public MyImage equalizeHistogram(MyImage src);

    public Distribution countDistribution(MyImage src);

    public Distribution countCumulativeDistribution(MyImage src);
}
