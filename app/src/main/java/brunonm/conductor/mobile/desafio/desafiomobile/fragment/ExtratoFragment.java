package brunonm.conductor.mobile.desafio.desafiomobile.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.adapter.ExtratoItemAdapter;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.RequestComplete;
import brunonm.conductor.mobile.desafio.desafiomobile.models.Compras;
import brunonm.conductor.mobile.desafio.desafiomobile.networkusage.RequestUtils;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.ExtratoData;

public class ExtratoFragment extends Fragment implements RequestComplete {
    private LinearLayout layoutSpinner;
    private RecyclerView extratoList;
    private TextView textErroMsg;
    private ProgressBar progressBar;
    private RecyclerView.Adapter recyclerViewAdapter;
    private List<Compras> comprasList = new ArrayList<>();
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extrato_layout, container, false);

        layoutSpinner = view.findViewById(R.id.layout_spinner);
        extratoList = view.findViewById(R.id.extrato_list);
        textErroMsg = view.findViewById(R.id.text_erro_msg);
        progressBar = view.findViewById(R.id.progress_bar);

        recyclerViewAdapter = new ExtratoItemAdapter(getActivity(), comprasList);
        extratoList.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        extratoList.setLayoutManager(layoutManager);

        ExtratoData extratoDataInstance = ExtratoData.getInstance();
        if (extratoDataInstance.getComprasList(position) == null) {
            RequestUtils.updateExtrato(this, position);
        } else{
            updateLayout();
        }

        return view;
    }

    @Override
    public void onSuccess() {
        updateLayout();
    }

    private void updateLayout() {
        layoutSpinner.setVisibility(View.GONE);
        extratoList.setVisibility(View.VISIBLE);

        comprasList.clear();
        comprasList.addAll(ExtratoData.getInstance().getComprasList(position));
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFail() {
        progressBar.setVisibility(View.GONE);
        textErroMsg.setVisibility(View.VISIBLE);
    }

    public void setPosition(int position) {
        this.position = position + 1;
    }
}
