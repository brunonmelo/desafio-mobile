package brunonm.conductor.mobile.desafio.desafiomobile.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.RequestComplete;
import brunonm.conductor.mobile.desafio.desafiomobile.networkusage.ResquestUtils;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.Extrato;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.PortadorAtual;
import brunonm.conductor.mobile.desafio.desafiomobile.util.LayoutLoader;
import brunonm.conductor.mobile.desafio.desafiomobile.util.NavegationDrawerUtil;

public class ExtratoActivity extends AppCompatActivity {

    private NavegationDrawerUtil navegationDrawerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        LayoutLoader.load(this, R.layout.activity_extrato);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navegationDrawerUtil = new NavegationDrawerUtil(this, toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navegationDrawerUtil.onResume();

        ResquestUtils.updatePortador(new RequestComplete() {
            @Override
            public void onSuccess() {
                Log.d("debug", "onSuccess: updatePortador ");
                Log.d("debug", "onSuccess: portador " + PortadorAtual.getInstance().getPortadorAtual().toString());
            }

            @Override
            public void onFail() {
                Log.d("debug", "onFail: updatePortador ");
            }
        });

        ResquestUtils.updateExtrato(new RequestComplete() {
            @Override
            public void onSuccess() {
                Log.d("debug", "onSuccess: updateExtrato ");
                Log.d("debug", "onSuccess: pages " + Extrato.getInstance().getPagesNumber());
                Log.d("debug", "onSuccess: pages " + Extrato.getInstance().getComprasSet(1));
            }

            @Override
            public void onFail() {
                Log.d("debug", "onFail: updateExtrato ");
            }
        }, "07", "2018");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navegationDrawerUtil.onBackPressed();
    }
}
