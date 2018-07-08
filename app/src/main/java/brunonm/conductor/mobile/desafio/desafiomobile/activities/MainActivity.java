package brunonm.conductor.mobile.desafio.desafiomobile.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.cards.VisaoGeralCard;
import brunonm.conductor.mobile.desafio.desafiomobile.util.LayoutLoader;
import brunonm.conductor.mobile.desafio.desafiomobile.util.NavegationDrawerUtil;

public class MainActivity extends AppCompatActivity {

    private NavegationDrawerUtil navegationDrawerUtil;
    private VisaoGeralCard visaoGeralCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        LayoutLoader.load(this, R.layout.app_bar_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navegationDrawerUtil = new NavegationDrawerUtil(this, toolbar);

        visaoGeralCard = new VisaoGeralCard(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // atualiza valores no onResume
        visaoGeralCard.onResume();
        navegationDrawerUtil.onResume();
    }

    @Override
    public void onBackPressed() {
        if(navegationDrawerUtil.onBackPressed()) {
            super.onBackPressed();
        }
    }

}
