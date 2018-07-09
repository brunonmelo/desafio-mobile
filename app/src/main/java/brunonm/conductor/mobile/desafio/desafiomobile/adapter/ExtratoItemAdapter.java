package brunonm.conductor.mobile.desafio.desafiomobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.models.Compras;
import brunonm.conductor.mobile.desafio.desafiomobile.util.ColorsUtil;

public class ExtratoItemAdapter extends RecyclerView.Adapter<ExtratoItemAdapter.ViewHolder> {
    private final List<Compras> extratoList;
    private final LayoutInflater mLayoutInflater;
    private final Activity activity;

    public ExtratoItemAdapter(Activity activity, List<Compras> extratoList) {
        this.activity = activity;
        this.extratoList = extratoList;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_extrato, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Compras compras = extratoList.get(position);
        DateFormat df = new SimpleDateFormat("dd MMM", Locale.getDefault()); // Just the year, with 2 digits

        holder.textData.setText(df.format(compras.getDate().getTime()));
        holder.textDescricao.setText(compras.getDescricao());
        holder.textLoja.setText(compras.getLoja());
        holder.textValor.setText(String.valueOf(NumberFormat
                .getCurrencyInstance()
                .format(compras.getValue())));
        holder.marcador.setBackgroundColor(ColorsUtil.getMarcadorColor(activity));
    }

    @Override
    public int getItemCount() {
        return extratoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textData;
        private TextView textDescricao;
        private TextView textLoja;
        private TextView textValor;
        private View marcador;

        ViewHolder(View itemView) {
            super(itemView);

            textData = itemView.findViewById(R.id.text_data);
            textDescricao = itemView.findViewById(R.id.text_descricao);
            textLoja = itemView.findViewById(R.id.text_loja);
            textValor = itemView.findViewById(R.id.text_valor);
            marcador = itemView.findViewById(R.id.view_marcador);
        }
    }

}
