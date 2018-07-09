package brunonm.conductor.mobile.desafio.desafiomobile.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.activities.ExtratoActivity;
import brunonm.conductor.mobile.desafio.desafiomobile.activities.MainActivity;
import brunonm.conductor.mobile.desafio.desafiomobile.enums.CartaoTipo;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.AcaoConcluida;

public class NavegationDrawerUtil implements NavigationView.OnNavigationItemSelectedListener {

    private final DrawerLayout drawer;
    private final Preferencias prefs;
    private final AppCompatActivity activity;
    private final Toolbar toolbar;
    private final NavigationView navigationView;
    private AcaoConcluida acaoConcluida;

    public NavegationDrawerUtil(AppCompatActivity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;

        drawer = activity.findViewById(R.id.drawer_layout);

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        prefs = new Preferencias(activity);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_visao_geral:
                if (!isOnMain()) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                }
                break;
            case R.id.nav_extrato:
                if (!isOnExtrato()) {
                    Intent intent = new Intent(activity, ExtratoActivity.class);
                    activity.startActivity(intent);
                }
                break;
            case R.id.nav_bluecard:
                prefs.salvarCartaoTipo(CartaoTipo.BLUE_CARD.getId());
                ColorsUtil.setToolbarBackgroundColors(activity, toolbar, navigationView);
                if(acaoConcluida != null) {
                    acaoConcluida.acaoConcluidaCallback();
                }
                break;
            case R.id.nav_greencard:
                prefs.salvarCartaoTipo(CartaoTipo.GREEN_CARD.getId());
                ColorsUtil.setToolbarBackgroundColors(activity, toolbar, navigationView);
                if(acaoConcluida != null) {
                    acaoConcluida.acaoConcluidaCallback();
                }
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Caso o drawer esteja aberto, fecha o drawer
    public boolean onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
        return true;
    }

    // Chamada no onResume da activity para setar as cores basicas e o item selecionado;
    public void onResume() {
        if (isOnMain()) {
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (isOnExtrato()) {
            navigationView.getMenu().getItem(1).setChecked(true);
        }
        ColorsUtil.setToolbarBackgroundColors(activity, toolbar, navigationView);
    }

    private boolean isOnMain() {
        return activity.getComponentName().getShortClassName().contains(".MainActivity");
    }

    private boolean isOnExtrato() {
        return activity.getComponentName().getShortClassName().contains(".ExtratoActivity");
    }

    public void onUpdate(AcaoConcluida acaoConcluida) {
        this.acaoConcluida = acaoConcluida;
    }
}
