package brunonm.conductor.mobile.desafio.desafiomobile.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.AcaoConcluida;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.Extrato;

public class FiltroDialogFragment extends DialogFragment {

    public static FiltroDialogFragment newInstance(AcaoConcluida callback) {
        FiltroDialogFragment f = new FiltroDialogFragment();

        Bundle b = new Bundle();
        b.putSerializable("callback", callback);
        f.setArguments(b);

        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AcaoConcluida callback = (AcaoConcluida) getArguments().getSerializable("callback");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = inflater.inflate(R.layout.dialog_filtro, null);
        builder.setView(view);

        Spinner spinnerMes = view.findViewById(R.id.spinner_mes);
        Spinner spinnerAno = view.findViewById(R.id.spinner_ano);

        setupSpinnerMes(spinnerMes);
        setupSpinnerAno(spinnerAno);

        builder.setTitle(R.string.filtro_dialog_title);
        builder.setMessage(R.string.filtro_dialog_text);
        builder.setPositiveButton(R.string.confirmar, (dialogInterface, i) -> {
            Extrato extratoInstance = Extrato.getInstance();
            extratoInstance.setCurrentAno((Integer) spinnerAno.getSelectedItem());
            extratoInstance.setCurrentMes(spinnerMes.getSelectedItemPosition() + 1);
            callback.acaoConcluidaCallback();
        });
        builder.setNegativeButton(R.string.cancelar, null);

        return builder.create();
    }

    private void setupSpinnerAno(Spinner spinnerAno) {
        List<Integer> listAnos = new ArrayList<>();

        int anoBase = Calendar.getInstance().get(Calendar.YEAR) - 10;
        for(int i = 0; i < 20; i++) {
            listAnos.add(anoBase++);
        }

        ArrayAdapter<Integer> dataAnoAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                listAnos);

        dataAnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAno.setAdapter(dataAnoAdapter);
        int currentAno = Integer.parseInt(Extrato.getInstance().getCurrentAno());
        spinnerAno.setSelection(listAnos.indexOf(currentAno));
    }

    private void setupSpinnerMes(Spinner spinnerMes) {
        String[] monthArray = getContext().getResources().getStringArray(R.array.months);

        ArrayAdapter<String> dataMesAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                monthArray);

        dataMesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMes.setAdapter(dataMesAdapter);

        int currentMes = Integer.parseInt(Extrato.getInstance().getCurrentMes());
        spinnerMes.setSelection(currentMes - 1);
    }
}
