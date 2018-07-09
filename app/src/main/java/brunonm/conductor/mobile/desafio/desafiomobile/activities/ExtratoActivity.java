package brunonm.conductor.mobile.desafio.desafiomobile.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.Calendar;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.adapter.ExtratoPagerAdapter;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.RequestComplete;
import brunonm.conductor.mobile.desafio.desafiomobile.networkusage.RequestUtils;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.Extrato;
import brunonm.conductor.mobile.desafio.desafiomobile.util.LayoutLoader;
import brunonm.conductor.mobile.desafio.desafiomobile.util.NavegationDrawerUtil;

public class ExtratoActivity extends AppCompatActivity implements RequestComplete {

    private NavegationDrawerUtil navegationDrawerUtil;
    private int itemAtual = 1;
    private ViewPager mViewPager;
    private TextView textErroMsg;
    private ImageButton buttonBack;
    private ImageButton buttonForward;
    private TextView textPaginationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        LayoutLoader.load(this, R.layout.activity_extrato);

        Toolbar toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.vp_lista);
        textErroMsg = findViewById(R.id.text_erro_msg);
        buttonBack = findViewById(R.id.button_back);
        buttonForward = findViewById(R.id.button_forward);
        textPaginationData = findViewById(R.id.text_pagination_data);

        setSupportActionBar(toolbar);
        setupButtonsListeners();
        navegationDrawerUtil = new NavegationDrawerUtil(this, toolbar);
        navegationDrawerUtil.onUpdate(this::setupViewPage);
    }

    private void setupButtonsListeners() {
        buttonForward.setOnClickListener(view -> {
            if(Extrato.getInstance().getPagesNumber() > itemAtual){
                itemAtual++;
                updatePage();
            }
        });
        buttonBack.setOnClickListener(view -> {
            if(itemAtual > 1){
                itemAtual--;
                updatePage();
            }
        });
    }

    private void setupViewPage() {
        ExtratoPagerAdapter extratoPagerAdapter = new ExtratoPagerAdapter(this);
        mViewPager.setAdapter(extratoPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                itemAtual = mViewPager.getCurrentItem() + 1;
                updatePage();
            }
        });
        updatePage();
    }

    private void updatePage() {
        textPaginationData.setText(MessageFormat.format(getString(R.string.paginator_marcador), itemAtual, Extrato.getInstance().getPagesNumber()));
        mViewPager.setCurrentItem(itemAtual - 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navegationDrawerUtil.onResume();

//        RequestUtils.updatePortador(new RequestComplete() {
//            @Override
//            public void onSuccess() {
//                Log.d("debug", "onSuccess: updatePortador ");
//                Log.d("debug", "onSuccess: portador " + PortadorAtual.getInstance().getPortadorAtual().toString());
//            }
//
//            @Override
//            public void onFail() {
//                Log.d("debug", "onFail: updatePortador ");
//            }
//        });

        Calendar calendar = Calendar.getInstance();

        if (Extrato.getInstance().getComprasList(itemAtual) == null) {
            RequestUtils.updateExtrato(this,
                    String.valueOf(calendar.get(Calendar.MONTH) + 1),
                    String.valueOf(calendar.get(Calendar.YEAR)),
                    1);
        } else {
            setupViewPage();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navegationDrawerUtil.onBackPressed();
    }

    @Override
    public void onSuccess() {
        setupViewPage();
    }

    @Override
    public void onFail() {
        mViewPager.setVisibility(View.GONE);
        textErroMsg.setVisibility(View.VISIBLE);
    }
}
