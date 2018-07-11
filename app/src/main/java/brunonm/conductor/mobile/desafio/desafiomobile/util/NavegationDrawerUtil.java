package brunonm.conductor.mobile.desafio.desafiomobile.util;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.activities.ExtratoActivity;
import brunonm.conductor.mobile.desafio.desafiomobile.activities.MainActivity;

public class NavegationDrawerUtil implements NavigationView.OnNavigationItemSelectedListener {

    private final DrawerLayout drawer;
    private final AppCompatActivity activity;
    private final NavigationView navigationView;

    public NavegationDrawerUtil(AppCompatActivity activity, Toolbar toolbar) {
        this.activity = activity;

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

        setNavMenuItemThemeColors();
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
    }

    private boolean isOnMain() {
        return activity.getComponentName().getShortClassName().contains(".MainActivity");
    }

    private boolean isOnExtrato() {
        return activity.getComponentName().getShortClassName().contains(".ExtratoActivity");
    }

    private void setNavMenuItemThemeColors() {

        //Setting default colors for menu item Text and Icon
        int navDefaultTextColor = Color.parseColor("#202020");
        int navDefaultIconColor = Color.parseColor("#737373");
        int color = ActivityCompat.getColor(activity, R.color.colorPrimary);

        //Defining ColorStateList for menu item Text
        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );

        //Defining ColorStateList for menu item Icon
        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor
                }
        );

        navigationView.setItemTextColor(navMenuTextList);
        navigationView.setItemIconTintList(navMenuIconList);
    }
}
