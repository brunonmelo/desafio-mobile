package brunonm.conductor.mobile.desafio.desafiomobile.util;

import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.ExtratoData;

public class BarChartFactory {
    private final Activity activity;
    private BarChart mChart;
    private List<BarEntry> entries = new ArrayList<>();

    public BarChartFactory(Activity activity) {
        this.activity = activity;
        mChart = activity.findViewById(R.id.chart);
//        new BarEntry()
    }

    public void updateData() {
        ExtratoData extratoData = ExtratoData.getInstance();
        Map<String, Double> mapValues = extratoData.getMapValues();
        SparseArray<String> xList = new SparseArray<>();
        int i = 1;
        entries.clear();
        for (Map.Entry<String, Double> entry : mapValues.entrySet()) {
            entries.add(new BarEntry(i, entry.getValue().floatValue()));
            xList.put(i, entry.getKey());
            i++;
        }

        setXValue(xList);

        BarDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        } else {
            String[] monthArray = activity.getResources().getStringArray(R.array.months);
            String label = monthArray[extratoData.getCurrentMes() - 1] +
                    " - " + extratoData.getCurrentAno();
            set1 = new BarDataSet(entries, label);
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            mChart.setData(data);

            mChart.setFitBars(true); // make the x-axis fit exactly all bars
            mChart.invalidate();
        }
    }

    private void setXValue(SparseArray<String> xList) {
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(xList);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);
    }

    private class DayAxisValueFormatter implements IAxisValueFormatter {
        private SparseArray<String> data;

        DayAxisValueFormatter(SparseArray<String> data) {
            this.data = data;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return data.get((int) value);
        }
    }
}
