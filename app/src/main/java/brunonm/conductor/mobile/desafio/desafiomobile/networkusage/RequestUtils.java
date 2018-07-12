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
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.ExtratoData;
import brunonm.conductor.mobile.desafio.desafiomobile.singletons.PortadorAtual;
import brunonm.conductor.mobile.desafio.desafiomobile.util.StringUtils;

public class RequestUtils {
    private static final String PORTADOR_URI = "https://2hm1e5siv9.execute-api.us-east-1.amazonaws.com/dev/users/profile";
    private static final String SALDO_URI = "https://2hm1e5siv9.execute-api.us-east-1.amazonaws.com/dev/resume";
    private static final String EXTRATO_URI = "https://2hm1e5siv9.execute-api.us-east-1.amazonaws.com/dev/card-statement";
    private static final String USO_CARTAO_URI = "https://2hm1e5siv9.execute-api.us-east-1.amazonaws.com/dev/card-usage";

    public static void updatePortador(RequestComplete requestComplete) {
        new RequestPortadorESaldoAsyncTask(requestComplete).execute();
    }

    public static void updateExtrato(RequestComplete requestComplete, int page) {
        new RequestUpdateExtrato(requestComplete, page).execute();
    }

    public static void updateUsoCartao(RequestComplete requestComplete) {
        new RequestUsoCartao(requestComplete).execute();
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
        private final int mes;
        private final int ano;

        RequestUpdateExtrato(RequestComplete requestComplete,
                             int requestsPages) {
            this.requestCompleteCallback = requestComplete;
            this.requestsPage = requestsPages;

            ExtratoData extratoDataInstance = ExtratoData.getInstance();
            this.mes = extratoDataInstance.getCurrentMes();
            this.ano = extratoDataInstance.getCurrentAno();
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestPackage rpExtrato = new RequestPackage();
            rpExtrato.setUri(EXTRATO_URI);
            rpExtrato.setParams("month", String.valueOf(mes));
            rpExtrato.setParams("year", String.valueOf(ano));
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
                    ExtratoData extratoDataInstance = ExtratoData.getInstance();
                    JSONObject extratoJsonObject = new JSONObject(result);
                    extratoDataInstance.setPagesNumber(Integer.parseInt(extratoJsonObject
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
                    extratoDataInstance.updateComprasSet(compras, requestsPage);
                    requestCompleteCallback.onSuccess();
                } catch (JSONException e) {
                    requestCompleteCallback.onFail();
                    e.printStackTrace();
                }
            }
        }
    }

    private static class RequestUsoCartao extends AsyncTask<Void, Void, String> {
        private RequestComplete requestCompleteCallback;

        RequestUsoCartao(RequestComplete requestComplete) {
            this.requestCompleteCallback = requestComplete;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestPackage rpExtrato = new RequestPackage();
            rpExtrato.setUri(USO_CARTAO_URI);
            return HttpManager.getData(rpExtrato);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("ERROR")) {
                requestCompleteCallback.onFail();
            } else {
                try {
                    JSONObject extratoJsonObject = new JSONObject(result);
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
//                    extratoDataInstance.updateComprasSet(compras, requestsPage);
                    requestCompleteCallback.onSuccess();
                } catch (JSONException e) {
                    requestCompleteCallback.onFail();
                    e.printStackTrace();
                }
            }
        }
    }


}
