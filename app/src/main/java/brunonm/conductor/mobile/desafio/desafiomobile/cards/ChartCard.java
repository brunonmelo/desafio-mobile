package brunonm.conductor.mobile.desafio.desafiomobile.cards;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.RequestComplete;
import brunonm.conductor.mobile.desafio.desafiomobile.networkusage.RequestUtils;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.ExtratoData;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.PortadorAtual;
import brunonm.conductor.mobile.desafio.desafiomobile.util.BarChartFactory;
import brunonm.conductor.mobile.desafio.desafiomobile.util.StringUtils;

public class ChartCard implements RequestComplete{

    private final Activity activity;
    private final ProgressBar progressBar;
    private final LinearLayout layoutConteudo;
    private final TextView textErroMsg;
    private final BarChartFactory barChartFactory;

    public ChartCard(Activity activity) {
        this.activity = activity;
        textErroMsg = activity.findViewById(R.id.text_erro_msg_chart);
        progressBar = activity.findViewById(R.id.progress_bar_chart);
        layoutConteudo = activity.findViewById(R.id.layout_conteudo_chart);

        barChartFactory = new BarChartFactory(activity);
    }

    public void onResume() {
        ExtratoData extratoData = ExtratoData.getInstance();
        if (extratoData.getPagesNumber() != extratoData.getExtratoMapListSize()) {
            RequestUtils.updateExtrato(this, 1, true);
        } else {
            onSuccess();
        }
    }

    @Override
    public void onSuccess() {
        progressBar.setVisibility(View.GONE);
        layoutConteudo.setVisibility(View.VISIBLE);
        barChartFactory.updateData();
    }

    @Override
    public void onFail() {
        progressBar.setVisibility(View.GONE);
        if (ExtratoData.getInstance().getExtratoMapListSize() < 1) {
            textErroMsg.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(activity, activity.getText(R.string.saldo_update_error), Toast.LENGTH_SHORT).show();
            barChartFactory.updateData();
        }

    }

}
