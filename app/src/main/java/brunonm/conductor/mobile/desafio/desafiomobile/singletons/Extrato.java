package brunonm.conductor.mobile.desafio.desafiomobile.singletons;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import brunonm.conductor.mobile.desafio.desafiomobile.models.Compras;

public class Extrato {
    private static final Extrato ourInstance = new Extrato();
    private SparseArray<List<Compras>> comprasMap = new SparseArray<>();
    private int currentMes = -1;
    private int currentAno = -1;
    private int pagesNumber;

    public static Extrato getInstance() {
        return ourInstance;
    }


    public void updateComprasSet(Collection<Compras> compras, int page){
        List<Compras> comprasList = comprasMap.get(page);
        if (comprasList == null) comprasList = new ArrayList<>();
        comprasList.clear();
        comprasList.addAll(compras);
        comprasMap.put(page, comprasList);
    }

    public List<Compras> getComprasSet(int page) {
        return comprasMap.get(page);
    }

    public boolean isToUpdate(int mes, int ano) {
        return mes != currentMes || ano != currentAno;
    }

    public void setCurrentMes(String currentMes) {
        int mesInt = Integer.parseInt(currentMes);
        if(mesInt != this.currentMes) comprasMap.clear();
        this.currentMes = mesInt;
    }

    public void setCurrentAno(String currentAno) {
        int anoInt = Integer.parseInt(currentAno);
        if(anoInt != this.currentAno) comprasMap.clear();
        this.currentAno = anoInt;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }
}
