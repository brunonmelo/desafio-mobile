package brunonm.conductor.mobile.desafio.desafiomobile.models;

import java.util.Calendar;

import brunonm.conductor.mobile.desafio.desafiomobile.util.CalendarUtils;

public class Compras {

    private final String date;
    private final String loja;
    private final String descricao;
    private final double value;

    public Compras(String date, String loja, String descricao, double value){
        this.date = date;
        this.loja = loja;
        this.descricao = descricao;
        this.value = value;
    }

    public Calendar getDate() {
        return CalendarUtils.getCaledar(date);
    }

    public String getLoja() {
        return loja;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Compras{" +
                "date='" + date + '\'' +
                ", loja='" + loja + '\'' +
                ", descricao='" + descricao + '\'' +
                ", value=" + value +
                '}';
    }
}
