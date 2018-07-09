package brunonm.conductor.mobile.desafio.desafiomobile.singletons;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import brunonm.conductor.mobile.desafio.desafiomobile.models.Compras;

public class ExtratoData {
    private static final ExtratoData ourInstance = new ExtratoData();
    private SparseArray<List<Compras>> comprasMap = new SparseArray<>();
    private int currentMes = -1;
    private int currentAno = -1;
    private int pagesNumber = -1;

    public static ExtratoData getInstance() {
        return ourInstance;
    }


    public void updateComprasSet(Collection<Compras> compras, int page) {
        List<Compras> comprasList = comprasMap.get(page);
        if (comprasList == null) comprasList = new ArrayList<>();
        comprasList.clear();
        comprasList.addAll(compras);
        comprasMap.put(page, comprasList);
    }

    public List<Compras> getComprasList(int page) {
        return comprasMap.get(page);
    }

    public void setCurrentMes(int currentMes) {
        if (currentMes != this.currentMes) {
            limpaDados();
        }
        this.currentMes = currentMes;
    }

    public void setCurrentAno(int currentAno) {
        if (currentAno != this.currentAno) {
            limpaDados();
        }
        this.currentAno = currentAno;
    }

    private void limpaDados() {
        comprasMap.clear();
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public int getCurrentMes() {
        if (currentMes == -1) {
            Calendar calendar = Calendar.getInstance();
            setCurrentMes(calendar.get(Calendar.MONTH) + 1);
        }
        return currentMes;
    }

    public int getCurrentAno() {
        if (currentAno == -1) {
            Calendar calendar = Calendar.getInstance();
            calendar.get(Calendar.YEAR);
        }
        return currentAno;
    }

    public int getExtratoMapListSize() {
        return comprasMap.size();
    }

}
