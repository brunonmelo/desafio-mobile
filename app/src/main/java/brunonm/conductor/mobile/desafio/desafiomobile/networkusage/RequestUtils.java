package brunonm.conductor.mobile.desafio.desafiomobile.networkusage;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brunonm.conductor.mobile.desafio.desafiomobile.interfaces.RequestComplete;
import brunonm.conductor.mobile.desafio.desafiomobile.models.Compras;
import brunonm.conductor.mobile.desafio.desafiomobile.models.Portador;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.Extrato;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.PortadorAtual;
import brunonm.conductor.mobile.desafio.desafiomobile.util.StringUtils;

public class RequestUtils {
    private static final String PORTADOR_URI = "https://2hm1e5siv9.execute-api.us-east-1.amazonaws.com/dev/users/profile";
    private static final String SALDO_URI = "https://2hm1e5siv9.execute-api.us-east-1.amazonaws.com/dev/resume";
    private static final String EXTRATO_URI = "https://2hm1e5siv9.execute-api.us-east-1.amazonaws.com/dev/card-statement";

    public static void updatePortador(RequestComplete requestComplete) {
        new RequestPortadorESaldoAsyncTask(requestComplete).execute();
    }

    public static void updateExtrato(RequestComplete requestComplete, String mes, String ano, int page) {
        Extrato extratoInstance = Extrato.getInstance();
        extratoInstance.setCurrentMes(mes);
        extratoInstance.setCurrentAno(ano);
        new RequestUpdateExtrato(requestComplete, page, mes, ano).execute();
    }

    private static class RequestPortadorESaldoAsyncTask extends AsyncTask<Void, Void, String[]> {
        private RequestComplete requestCompleteCallback;

        RequestPortadorESaldoAsyncTask(RequestComplete requestComplete) {
            this.requestCompleteCallback = requestComplete;
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            String[] result = new String[2];
            RequestPackage rpPortador = new RequestPackage();
            RequestPackage rpSaldo = new RequestPackage();

            rpPortador.setUri(PORTADOR_URI);
            rpSaldo.setUri(SALDO_URI);

            result[0] = HttpManager.getData(rpPortador);
            result[1] = HttpManager.getData(rpSaldo);
            return result;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            if (isRequestError(result)) {
                requestCompleteCallback.onFail();
            } else {
                try {
                    JSONObject portadorJsonObject = new JSONObject(result[0]);
                    JSONObject saldoJsonObject = new JSONObject(result[1]);

                    Portador portador = new Portador(
                            portadorJsonObject.getString("name"),
                            portadorJsonObject.getString("cardNumber"),
                            portadorJsonObject.getString("expirationDate"),
                            saldoJsonObject.getDouble("balance")
                    );

                    PortadorAtual.getInstance().setPortadorAtual(portador);
                    requestCompleteCallback.onSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                    requestCompleteCallback.onFail();
                }
            }
        }

        private static boolean isRequestError(String[] results) {
            for (String result : results) {
                if (result.equals("ERROR")) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class RequestUpdateExtrato extends AsyncTask<Void, Void, String> {
        private RequestComplete requestCompleteCallback;
        private int requestsPage;
        private final String mes;
        private final String ano;

        RequestUpdateExtrato(RequestComplete requestComplete,
                             int requestsPages,
                             String mes,
                             String ano) {
            this.requestCompleteCallback = requestComplete;
            this.requestsPage = requestsPages;
            this.mes = mes;
            this.ano = ano;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestPackage rpExtrato = new RequestPackage();
            rpExtrato.setUri(EXTRATO_URI);
            rpExtrato.setParams("month", mes);
            rpExtrato.setParams("year", ano);
            rpExtrato.setParams("page", StringUtils.preencheZeros(String.valueOf(requestsPage)));

            return HttpManager.getData(rpExtrato);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("ERROR")) {
                requestCompleteCallback.onFail();
            } else {
                try {
                    Extrato extratoInstance = Extrato.getInstance();
                    JSONObject extratoJsonObject = new JSONObject(result);
                    extratoInstance.setPagesNumber(Integer.parseInt(extratoJsonObject
                            .getString("lastPage")));

                    JSONArray extratoJsonArray = extratoJsonObject.getJSONArray("purchases");

                    List<Compras> compras = new ArrayList<>();
                    for (int z = 0; z < extratoJsonArray.length(); z++) {
                        JSONObject jsonExtratoObject = extratoJsonArray.getJSONObject(z);
                        Compras compra = new Compras(
                                jsonExtratoObject.getString("date"),
                                jsonExtratoObject.getString("store"),
                                jsonExtratoObject.getString("description"),
                                jsonExtratoObject.getDouble("value")
                        );

                        compras.add(compra);
                    }
                    extratoInstance.updateComprasSet(compras, requestsPage);
                    requestCompleteCallback.onSuccess();
                } catch (JSONException e) {
                    requestCompleteCallback.onFail();
                    e.printStackTrace();
                }
            }
        }
    }

}
