package brunonm.conductor.mobile.desafio.desafiomobile.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import java.util.HashMap;
import java.util.Map;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.enums.CartaoTipo;

public class ColorsUtil {
    private static final String PRIMARY_COLOR = "PRIMARY_COLOR";
    private static final String SECONDARY_COLOR = "SECONDARY_COLOR";

    public static void setToolbarBackgroundColors(Activity activity, Toolbar toolbar, NavigationView navigationView) {
        Preferencias prefs = new Preferencias(activity);
        CartaoTipo cartaoTipo = prefs.pegarCartaoTipo();
        Map<String, Integer> baseColors = getBaseColor(cartaoTipo, activity);

        toolbar.setBackgroundColor(baseColors.get(PRIMARY_COLOR));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(baseColors.get(SECONDARY_COLOR));
        }
        setNavMenuItemThemeColors(activity, navigationView);
    }

    private static Map<String, Integer> getBaseColor(CartaoTipo cartaoTipo, Activity activity) {
        Map<String, Integer> colorsMap = new HashMap<>();
        switch (cartaoTipo) {
            case GREEN_CARD:
                colorsMap.put(PRIMARY_COLOR,
                        activity.getResources().getColor(R.color.colorGreenPrimary));
                colorsMap.put(SECONDARY_COLOR,
                        activity.getResources().getColor(R.color.colorGreenPrimaryDark));
                break;
            default:
                colorsMap.put(PRIMARY_COLOR,
                        activity.getResources().getColor(R.color.colorBluePrimary));
                colorsMap.put(SECONDARY_COLOR,
                        activity.getResources().getColor(R.color.colorBluePrimaryDark));
                break;
        }
        return colorsMap;
    }

    private static void setNavMenuItemThemeColors(Activity activity, NavigationView navigationView) {
        Preferencias prefs = new Preferencias(activity);
        CartaoTipo cartaoTipo = prefs.pegarCartaoTipo();

        //Setting default colors for menu item Text and Icon
        int navDefaultTextColor = Color.parseColor("#202020");
        int navDefaultIconColor = Color.parseColor("#737373");
        int color = getBaseColor(cartaoTipo, activity).get(PRIMARY_COLOR);

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
