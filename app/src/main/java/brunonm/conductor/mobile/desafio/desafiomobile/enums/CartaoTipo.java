package brunonm.conductor.mobile.desafio.desafiomobile.enums;

import android.util.SparseArray;

public enum CartaoTipo {
    BLUE_CARD(0, "BlueCard"),
    GREEN_CARD(1, "GreenCard");

    private final int id;
    private final String descricao;
    private static SparseArray<CartaoTipo> map = new SparseArray<>();

    static {
        for (CartaoTipo ct : CartaoTipo.values()) {
            map.put(ct.getId(), ct);
        }
    }

    CartaoTipo(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CartaoTipo getCartaoTipoPorCodigo(int cod) {
        return map.get(cod);
    }

}
