package brunonm.conductor.mobile.desafio.desafiomobile.util;

import android.content.Context;

import brunonm.conductor.mobile.desafio.desafiomobile.R;

public class StringUtils {

    public static String preencheZeros(String target) {
        int zeroNum = 2 - target.length();
        String preZerosNumSerie = "";
        while (zeroNum > 0) {
            preZerosNumSerie = preZerosNumSerie.concat("0");
            zeroNum--;
        }

        return preZerosNumSerie.concat(target);
    }

    public static String getOverviewText(Context context, int month) {
        String[] monthArray = context.getResources().getStringArray(R.array.months);
        return context.getResources().getString(R.string.overview) + " - " + monthArray[month];
    }
}
