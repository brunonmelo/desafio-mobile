package brunonm.conductor.mobile.desafio.desafiomobile.cards;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.RequestComplete;
import brunonm.conductor.mobile.desafio.desafiomobile.models.Portador;
import brunonm.conductor.mobile.desafio.desafiomobile.networkusage.ResquestUtils;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.PortadorAtual;
import brunonm.conductor.mobile.desafio.desafiomobile.util.ColorsUtil;
import brunonm.conductor.mobile.desafio.desafiomobile.util.StringUtils;

public class CreditCardCard {

    public CreditCardCard(Activity activity, Portador portador) {
        TextView textCardNumber = activity.findViewById(R.id.text_card_number);
        TextView textCardValidade = activity.findViewById(R.id.text_card_validade);
        TextView textCardName = activity.findViewById(R.id.text_card_name);

        ColorsUtil.setCardColor(activity);

        textCardName.setText(portador.getNome());
        textCardValidade.setText(getDataValidade(portador));
        textCardNumber.setText(getCardNumber(portador));
    }

    private String getDataValidade(Portador portador) {
        DateFormat df = new SimpleDateFormat("MM/yy", Locale.getDefault()); // Just the year, with 2 digits
        Log.d("debug", "getDataValidade: portador " + portador.toString());
        return df.format(portador.getExpirationDate().getTime());
    }

    private String getCardNumber(Portador portador) {
        return "xxxx xxxx xxxx xxxx " + portador
                .getCardNumber()
                .substring(portador.getCardNumber()
                        .length() - 5,
                        portador.getCardNumber().length() - 1);
    }

}
