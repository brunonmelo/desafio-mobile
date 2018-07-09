package brunonm.conductor.mobile.desafio.desafiomobile.cards;

import android.app.Activity;
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
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.PortadorAtual;
import brunonm.conductor.mobile.desafio.desafiomobile.util.StringUtils;

public class VisaoGeralCard implements RequestComplete{

    private final ProgressBar progressBar;
    private final LinearLayout layoutConteudo;
    private final PortadorAtual portadorAtual;
    private final Activity activity;
    private final TextView textErroMsg;
    private final TextView textSaldoAtual;

    public VisaoGeralCard(Activity activity) {
        this.activity = activity;
        Calendar dataAtual = Calendar.getInstance();
        portadorAtual = PortadorAtual.getInstance();

        TextView overviewTextView = activity.findViewById(R.id.TextView_current_balance);
        textSaldoAtual = activity.findViewById(R.id.text_saldo_atual);
        textErroMsg = activity.findViewById(R.id.text_erro_msg);
        progressBar = activity.findViewById(R.id.progress_bar);
        layoutConteudo = activity.findViewById(R.id.layout_conteudo);

        overviewTextView.setText(StringUtils.getOverviewText(activity, dataAtual.get(Calendar.MONTH)));
    }

    public void onResume() {
        if (portadorAtual.getPortadorAtual() == null) {
            RequestUtils.updatePortador(this);
        } else {
            onSuccess();
        }
    }

    @Override
    public void onSuccess() {
        progressBar.setVisibility(View.GONE);
        layoutConteudo.setVisibility(View.VISIBLE);
        updateSaldo();
        new CreditCardCard(activity, portadorAtual.getPortadorAtual());
    }

    @Override
    public void onFail() {
        progressBar.setVisibility(View.GONE);
        if (portadorAtual.getPortadorAtual() == null) {
            textErroMsg.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(activity, activity.getText(R.string.saldo_update_error), Toast.LENGTH_SHORT).show();
            updateSaldo();
            new CreditCardCard(activity, portadorAtual.getPortadorAtual());
        }

    }

    private void updateSaldo() {
        String valorEmReais = NumberFormat.getCurrencyInstance().format(portadorAtual.getPortadorAtual().getSaldo());
        textSaldoAtual.setText(valorEmReais);
    }
}
