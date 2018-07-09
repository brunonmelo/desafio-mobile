package brunonm.conductor.mobile.desafio.desafiomobile.cards;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.models.Portador;
import brunonm.conductor.mobile.desafio.desafiomobile.util.ColorsUtil;

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
