package brunonm.conductor.mobile.desafio.desafiomobile.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import brunonm.conductor.mobile.desafio.desafiomobile.enums.CartaoTipo;

public class Preferencias {
    private static final String CARTAO_TIPO = "CARTAO_TIPO";

    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;

    public Preferencias(Context context) {
        preferencias = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void salvarCartaoTipo(int cartaoCor) {
        editor = preferencias.edit();
        editor.putInt(CARTAO_TIPO, cartaoCor);
        editor.apply();
    }

    public CartaoTipo pegarCartaoTipo() {
        int storedCartaoTipo = preferencias.getInt(CARTAO_TIPO, 0);
        return CartaoTipo.getCartaoTipoPorCodigo(storedCartaoTipo);
    }
}
