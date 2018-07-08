package brunonm.conductor.mobile.desafio.desafiomobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navegationDrawerUtil.onBackPressed();
    }
}
