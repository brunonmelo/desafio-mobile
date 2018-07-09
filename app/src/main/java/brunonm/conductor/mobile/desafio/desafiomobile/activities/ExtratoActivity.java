package brunonm.conductor.mobile.desafio.desafiomobile.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.MessageFormat;

import brunonm.conductor.mobile.desafio.desafiomobile.R;
import brunonm.conductor.mobile.desafio.desafiomobile.adapter.ExtratoPagerAdapter;
import brunonm.conductor.mobile.desafio.desafiomobile.fragment.FiltroDialogFragment;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.AcaoConcluida;
import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.RequestComplete;
import brunonm.conductor.mobile.desafio.desafiomobile.networkusage.RequestUtils;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.ExtratoData;
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
    private ProgressDialog progressDialog;

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
            if (ExtratoData.getInstance().getPagesNumber() > itemAtual) {
                itemAtual++;
                updatePage();
            }
        });
        buttonBack.setOnClickListener(view -> {
            if (itemAtual > 1) {
                itemAtual--;
                updatePage();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_extrato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_carteira:

                return true;
            case R.id.menu_grafico:

                return true;
            case R.id.menu_filtro:
                FiltroDialogFragment filtroDialogFragment =
                        FiltroDialogFragment.newInstance((AcaoConcluida) () -> {
                            showProgressDialog();
                            RequestUtils.updateExtrato(this, 1, false);
                            itemAtual = 1;
                        });
                filtroDialogFragment.show(getSupportFragmentManager(), "filtro_extrato");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        textPaginationData.setText(MessageFormat.format(getString(R.string.paginator_marcador), itemAtual, ExtratoData.getInstance().getPagesNumber()));
        mViewPager.setCurrentItem(itemAtual - 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navegationDrawerUtil.onResume();

        if (ExtratoData.getInstance().getComprasList(itemAtual) == null) {
            RequestUtils.updateExtrato(this,1, false);
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
        dismissProgressDialog();
        setupViewPage();
    }

    @Override
    public void onFail() {
        mViewPager.setVisibility(View.GONE);
        textErroMsg.setVisibility(View.VISIBLE);
        dismissProgressDialog();
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.aguarde));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(getString(R.string.atualizando));
            progressDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
