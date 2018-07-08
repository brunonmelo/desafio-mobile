package brunonm.conductor.mobile.desafio.desafiomobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import brunonm.conductor.mobile.desafio.desafiomobile.util.LayoutLoader;
import brunonm.conductor.mobile.desafio.desafiomobile.util.NavegationDrawerUtil;

public class MainActivity extends AppCompatActivity {

    private NavegationDrawerUtil navegationDrawerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        LayoutLoader.load(this, R.layout.app_bar_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navegationDrawerUtil = new NavegationDrawerUtil(this, toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Seleciona a cor do background de acordo com o tipo de cartão selecionado
        navegationDrawerUtil.onResume();
    }

    @Override
    public void onBackPressed() {
        if(navegationDrawerUtil.onBackPressed()) {
            super.onBackPressed();
        }
    }

}
