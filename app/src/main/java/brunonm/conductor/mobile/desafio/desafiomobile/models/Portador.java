package brunonm.conductor.mobile.desafio.desafiomobile.models;

import java.math.BigDecimal;
import java.util.Calendar;

import brunonm.conductor.mobile.desafio.desafiomobile.util.CalendarUtils;

public class Portador {
    private String nome;
    private String cardNumber;
    private String expirationDate;
    private double saldo;

    public Portador(String nome, String cardNumber, String expirationDate, double saldo) {
        this.nome = nome;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Calendar getExpirationDate() {
        return CalendarUtils.getCaledar(expirationDate);
    }

    public BigDecimal getSaldo() {
        return new BigDecimal(saldo);
    }

    @Override
    public String toString() {
        return "Portador{" +
                "nome='" + nome + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
