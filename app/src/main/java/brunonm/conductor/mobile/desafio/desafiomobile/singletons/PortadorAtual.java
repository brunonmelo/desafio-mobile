package brunonm.conductor.mobile.desafio.desafiomobile.singletons;

import brunonm.conductor.mobile.desafio.desafiomobile.models.Portador;

public class PortadorAtual {
    private static final PortadorAtual ourInstance = new PortadorAtual();
    private Portador portador;

    public static PortadorAtual getInstance() {
        return ourInstance;
    }

    public Portador getPortadorAtual() {
        return portador;
    }

    public void setPortadorAtual(Portador portadorAtual) {
        this.portador = portadorAtual;
    }
}
