package cn.xender.paytm_native.api;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

import cn.xender.paytm_native.callback.OkhttpUtilsCallback;
import cn.xender.paytm_native.event.InitialTransactionEvent;
import okhttp3.MediaType;

public class TransactionStatusApi {

    /**
     * Rayn
     *        Post
     *
     * Staging
     * https://securegw-stage.paytm.in/merchant-status/getTxnStatus
     *
     * Production
     * https://securegw.paytm.in/merchant-status/getTxnStatus
     */


    private static TransactionStatusApi instance;
    public static TransactionStatusApi getInstance() {
        if (instance == null) {
            instance = new TransactionStatusApi();
        }
        return instance;
    }

    public void getJson(String mid,String orderId) {
        final String transactionStatusUrl = "https://securegw-stage.paytm.in/merchant-status/getTxnStatus";
//        final String transactionStatusUrl = "https://securegw.paytm.in/merchant-status/getTxnStatus";

        final TreeMap treeMap = new TreeMap();
        treeMap.put("MID",mid);
        treeMap.put("ORDERID",orderId);

        requestChecksum(mid,orderId, new OkhttpUtilsCallback() {
            @Override
            public void success(String checksum) {
                treeMap.put("CHECKSUMHASH",checksum);
                JSONObject obj = new JSONObject(treeMap);
                final String postData = "JsonData=" + obj.toString();

                System.out.println("---Rayn postData:"+postData);
//
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        requestTransactionStatusApi(postData,transactionStatusUrl);
                        aaa(postData,transactionStatusUrl);
                    }
                }).start();

            }
        });
    }

    private String aaa(String postData, String transactionStatusUrl){
        try {

            URL url = new URL(transactionStatusUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("contentType", "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
            requestWriter.writeBytes(postData);
            requestWriter.close();
            String responseData = "";

            int code = connection.getResponseCode();
            System.out.println("---Rayn code:"+code);

            InputStream is = connection.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
            if((responseData = responseReader.readLine()) != null) {
//                System.out.append("Response Json = " + responseData);
            }
//            System.out.append("Requested Json = " + postData + " ");
            responseReader.close();
            System.out.println("---Rayn data:"+responseData);
            return responseData;
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("---Rayn error");
        }

        return null;
    }

    private void requestTransactionStatusApi(String postData, String url) {
        try {

            StringCallback callback = new StringCallback() {
                @Override
                public void onError(okhttp3.Call call, Exception e, int id) {
                    System.out.println("---Rayn TransactionStatusApi onError:"+e);
                }
                @Override
                public void onResponse(String response, int id) {
                    System.out.println("---Rayn TransactionStatusApi onResponse:"+response);

//                    EventBus.getDefault().post(new InitialTransactionEvent(response));
                }
            };
            OkHttpUtils
                    .postString()
                    .url(url)
//                    .mediaType(MediaType.parse("application/json"))  //是否需要charset=utf-8   ？
                    .content(postData)
                    .build()
                    .execute(callback);

//            OkHttpUtils
//                    .post()
//                    .addParams("JsonData",postData)
//                    .url(url)
//                    .build()
//                    .execute(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestChecksum(String mid,String orderId, final OkhttpUtilsCallback okhttpUtilsCallback){
        try {
            String url = "http://192.168.10.91:9999/transactionStatus";

            StringCallback callback = new StringCallback() {
                @Override
                public void onError(okhttp3.Call call, Exception e, int id) {
                    System.out.println("---Rayn TransactionStatus requestChecksum onError:"+e);
                }
                @Override
                public void onResponse(String response, int id) {
                    System.out.println("---Rayn TransactionStatus requestChecksum onResponse:"+response);
                    okhttpUtilsCallback.success(response);
                }
            };
            OkHttpUtils
                    .post()
                    .addParams("mid",mid)
                    .addParams("orderId",orderId)
                    .url(url)
                    .build()
                    .execute(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
