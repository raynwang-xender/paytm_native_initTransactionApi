package cn.xender.paytm_native.api;

import android.content.SharedPreferences;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.xender.paytm_native.callback.OkhttpUtilsCallback;
import cn.xender.paytm_native.event.InitialTransactionEvent;
import okhttp3.MediaType;

public class FetchPaymentOptionsApi {

    /**
     * Rayn
     *
     * Staging
     * https://securegw-stage.Paytm.in/theia/api/v1/fetchPaymentOptions?mid=<mid>&orderId=<orderId>
     *
     * Production
     * https://securegw.Paytm.in/theia/api/v1/fetchPaymentOptions?mid=<mid>&orderId=<orderId>
     */

    private static FetchPaymentOptionsApi instance;
    public static FetchPaymentOptionsApi getInstance() {
        if (instance == null) {
            instance = new FetchPaymentOptionsApi();
        }
        return instance;
    }

    public void getJson(String mid, String orderId, SharedPreferences sp) {

        final String fetchPaymentOptionsUrl = "https://securegw-stage.Paytm.in/theia/api/v1/fetchPaymentOptions?mid="+mid+"&orderId="+orderId;
        JSONObject json = new JSONObject();
        final JSONObject head = new JSONObject();
        try {
            head.put("Version","v1");
            head.put("requestTimestamp",System.currentTimeMillis());
            head.put("channelId","WAP");
            head.put("txnToken",sp.getString("txnToken",""));

            json.put("head",head);
            json.put("body",new JSONObject());

            System.out.println("---Rayn json before Fetch:"+json.toString());

            requestFetchPaymentOptionsApi(json,fetchPaymentOptionsUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void requestFetchPaymentOptionsApi(JSONObject json, String url){
        try {

            StringCallback callback = new StringCallback() {
                @Override
                public void onError(okhttp3.Call call, Exception e, int id) {
                    System.out.println("---Rayn requestFetchPaymentOptionsApi onError");
                }
                @Override
                public void onResponse(String response, int id) {
                    System.out.println("---Rayn requestFetchPaymentOptionsApi onResponse:"+response);
                }
            };
            OkHttpUtils
                    .postString()
                    .url(url)
                    .mediaType(MediaType.parse("application/json"))  //是否需要charset=utf-8   ？
                    .content(json.toString())
                    .build()
                    .execute(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
