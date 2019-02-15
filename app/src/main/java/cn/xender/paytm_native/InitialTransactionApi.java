package cn.xender.paytm_native;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;

public class InitialTransactionApi {
    /**
     * Rayn
     *        Post
     *
     * Staging
     * https://securegw-stage.Paytm.in/theia/api/v1/initiateTransaction?mid=<mid>&orderId=<orderId>
     *
     * Production
     * https://securegw.Paytm.in/theia/api/v1/initiateTransaction?mid=<mid>&orderId=<orderId>
     */

    private static InitialTransactionApi instance;
    public static InitialTransactionApi getInstance() {
        if (instance == null) {
            instance = new InitialTransactionApi();
        }
        return instance;
    }

    public void getJson(String mid,String orderId) {
        final String initialTransactionUrl = "https://securegw-stage.Paytm.in/theia/api/v1/initiateTransaction?mid="+mid+"&orderId="+orderId;

        final JSONObject body = getBody(mid,orderId);
        final JSONObject head = new JSONObject();

        requestChecksum(body.toString(), new OkhttpUtilsCallback() {
            @Override
            public void success(String checksum) {
                try {
                    head.put("clientId","C11");
                    head.put("Version","v1");
                    head.put("requestTimestamp",System.currentTimeMillis());
                    head.put("channelId","APP"); //APP WAP
                    head.put("Signature",checksum);

                    JSONObject json = new JSONObject();
                    json.put("head",head);
                    json.put("body",body);

                    System.out.println("---Rayn json before Initial:"+json);

                    requestInitialTransactionApi(json,initialTransactionUrl);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestInitialTransactionApi(JSONObject json, String url){
        try {

            StringCallback callback = new StringCallback() {
                @Override
                public void onError(okhttp3.Call call, Exception e, int id) {
                    System.out.println("---Rayn InitialTransactionApi onError");
                }
                @Override
                public void onResponse(String response, int id) {
                    System.out.println("---Rayn InitialTransactionApi onResponse:"+response);

                }
            };

            OkHttpUtils
                    .postString()
                    .url(url)
                    .mediaType(MediaType.parse("application/json"))
                    .content(json.toString())
                    .build()
                    .execute(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestChecksum(String body, final OkhttpUtilsCallback okhttpUtilsCallback){
        try {
            String url = "http://192.168.10.91:9999/native";

            StringCallback callback = new StringCallback() {
                @Override
                public void onError(okhttp3.Call call, Exception e, int id) {
                    System.out.println("---Rayn requestChecksum onError");
                }
                @Override
                public void onResponse(String response, int id) {
                    System.out.println("---Rayn requestChecksum onResponse:"+response);
                    okhttpUtilsCallback.success(response);
                }
            };
            OkHttpUtils
                    .post()
                    .addParams("body",body)
                    .url(url)
                    .build()
                    .execute(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject getBody(String mid,String orderId) {
        try {
            JSONObject body = new JSONObject();
            body.put("requestType","Payment");
            body.put("mid",mid);
            body.put("orderId",orderId);
            body.put("websiteName","APPSTAGING");
            body.put("txnAmount",getTxnAmountJson());
            body.put("userInfo",getUserInfoJson());
//            body.put("PaytmSsoToken","");
            body.put("enablePaymentMode",null);
            body.put("disablepaymentmode",getDisablePaymentModeJson());
            body.put("promoCode","");
            body.put("callbackUrl","http://192.168.10.91:9999/callback");
            body.put("goods",getGoodsJson());
            body.put("shippingInfo",getShippingInfoJson());
            body.put("extendInfo",getExtendInfo());
            return body;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private JSONObject getTxnAmountJson(){
        try {
            JSONObject json = new JSONObject();
            json.put("value",2.5);//float
            json.put("currency","INR");//
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private JSONObject getUserInfoJson(){
        try {
            JSONObject json = new JSONObject();
            json.put("custId","cid");//
            json.put("Mobile","9990263484");//
            json.put("Email","abc@gmail.com");
            json.put("firstName","ajay");
            json.put("lastName","tomar");
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray getEnablePaymentModeJson(){
        return null;
    }

    private JSONArray getDisablePaymentModeJson(){
        JSONArray array = new JSONArray();

        try {
            JSONObject json0 = new JSONObject();
            json0.put("mode","");
            json0.put("channels",new JSONArray());
            array.put(json0);
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    private JSONArray getGoodsJson(){
        JSONArray array = new JSONArray();

        try {
            JSONObject json0 = new JSONObject();

            json0.put("merchantGoodsId","24525635625623");
            json0.put("merchantShippingId","564314314574327545");

            json0.put("snapshotUrl","[http://snap.url.com]");

            json0.put("description","Women Summer Dress New White Lace Sleeveless");
            json0.put("category","travelling/subway");
            json0.put("quantity","3.2");
            json0.put("unit","Kg");

            JSONObject priceJson = new JSONObject();
            priceJson.put("currency","INR");
            priceJson.put("value","1");
            json0.put("price",priceJson);

            JSONObject extendInfoJson = new JSONObject();
            extendInfoJson.put("udf1","ajay");
            extendInfoJson.put("udf2","ajay");
            extendInfoJson.put("udf3","ajay");
            extendInfoJson.put("udf4","ajay");
            extendInfoJson.put("udf5","ajay");
            json0.put("extendInfo",extendInfoJson);

            array.put(json0);
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }
    private JSONArray getShippingInfoJson(){
        JSONArray array = new JSONArray();

        try {
            JSONObject json0 = new JSONObject();

            json0.put("merchantShippingId","564314314574327545");
            json0.put("trackingNo","646431431322332133");
            json0.put("carrier","Federal Express");

            JSONObject chargeAmountJson = new JSONObject();
            chargeAmountJson.put("currency","INR");
            chargeAmountJson.put("value","1");
            json0.put("chargeAmount",chargeAmountJson);

            json0.put("countryName","JP");
            json0.put("stateName","GA");
            json0.put("cityName","Atlanta");
            json0.put("address1","137 W San Bernardino");
            json0.put("address2","4114 Sepulveda");
            json0.put("firstName","Jim");
            json0.put("lastName","Li");
            json0.put("mobileNo","13765443223");
            json0.put("zipCode","310001");
            json0.put("email","abc@gmail.com");

            array.put(json0);
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }
    private JSONObject getExtendInfo(){
        try {
            JSONObject json = new JSONObject();
            json.put("udf1","vivek1");
            json.put("udf2","vivek2");
            json.put("udf3","vivek3");
            json.put("mercUnqRef","sangram");
            json.put("comments","vivek5");

            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
