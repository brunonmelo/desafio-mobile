package brunonm.conductor.mobile.desafio.desafiomobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import brunonm.conductor.mobile.desafio.desafiomobile.fragment.ExtratoFragment;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.ExtratoData;
import brunonm.conductor.mobile.desafio.desafiomobile.util.SmartFragmentStatePagerAdapter;

public class ExtratoPagerAdapter extends SmartFragmentStatePagerAdapter {
    public ExtratoPagerAdapter(AppCompatActivity activity) {
        super(activity.getSupportFragmentManager());
    }

    @Override
    public Fragment getItem(int position) {
        ExtratoFragment extratoFragment = new ExtratoFragment();
        extratoFragment.setPosition(position);
        return extratoFragment;
    }

    @Override
    public int getCount() {
        return ExtratoData.getInstance().getPagesNumber();
    }
}
