package cn.xender.paytm_native;

import android.content.SharedPreferences;
import android.os.health.SystemHealthManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import net.one97.paytm.nativesdk.PaytmSDK;
import net.one97.paytm.nativesdk.Utils.Server;
import net.one97.paytm.nativesdk.app.PaytmSDKCallbackListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.xender.paytm_native.api.FetchPaymentOptionsApi;
import cn.xender.paytm_native.api.InitialTransactionApi;
import cn.xender.paytm_native.api.TransactionStatusApi;
import cn.xender.paytm_native.bean.InitialTransactionJson;
import cn.xender.paytm_native.event.InitialTransactionEvent;

public class MainActivity extends AppCompatActivity implements PaytmSDKCallbackListener {

    private static final String SP_INITIALTRANSACTIONJSON = "InitialTransactionJson";
    private static final String SP_TXNTOKEN = "txnToken";

    final String mid_stage = "ONONPR28903535505911";  //ONONPR28903535505911
    final String mid = "ONIONm00999354646325";
    final String orderId = "test67890";  //ORDS44408635
//        final String orderId = "Xender"+ System.currentTimeMillis();
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(SP_INITIALTRANSACTIONJSON, MODE_PRIVATE);

    }

    public void GoToPay(View view){

        String json = sp.getString(SP_INITIALTRANSACTIONJSON,"");

        PaytmSDK paytmSDK = new PaytmSDK(this, json, this, 2.5,
                mid_stage, orderId, "test", R.drawable.paytm_wallet, Server.STAGING);

        paytmSDK.startTransaction();

    }

    public void InitialTransaction(View view) {
        InitialTransactionApi.getInstance().getJson(mid_stage,orderId);
    }

    public void FetchPaymentOptionsApi(View view) {
        FetchPaymentOptionsApi.getInstance().getJson(mid_stage,orderId,sp);
    }

    public void TransactionStatusApi(View view) {
        TransactionStatusApi.getInstance().getJson(mid_stage,orderId);
    }

    /**
     * Rayn
     * InitialTransactionApi 请求成功
     */
    @Subscribe
    public void onInitialTransactionEvent(InitialTransactionEvent event) {

//        Toast.makeText(this,"InitialTransactionApi 请求成功",Toast.LENGTH_SHORT).show();

        String json = event.getJson();
        Gson gson = new Gson();
        InitialTransactionJson initialTransactionJson = gson.fromJson(json, InitialTransactionJson.class);
        String txnToken = initialTransactionJson.getBody().getTxnToken();

        SharedPreferences.Editor edit = sp.edit();
        edit.putString(SP_TXNTOKEN,txnToken);
        edit.putString(SP_INITIALTRANSACTIONJSON,json);
        edit.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Rayn
     * IntiateTransactionResponse: Pass the whole JSON response as a string of initiate transaction api which contains:
     *              Transaction Token1, RequestTimestamp2, ResultCode3,authentication4 etc.
     * {
     *     "head":{
     *         "responseTimestamp":"1523967528063",     时间     //222222222
     *         "version":"v1",                          ？
     *         "clientId":"C11",                        用户id ？
     *         "signature":"rpF9oTDqrOYPgM8Ew/V1W4Cxy9kFPI7DHDNdMPiV+ABJfCRW5EiNNFKCJj1JVOwYb3T6C9JAL4K51xbVnqnfBnQdsRaB56ayUnYRJ0Dr9HQ="
     *     },                                           hash ？
     *     "body":{
     *         "resultInfo":{
     *             "resultStatus":"S",
     *             "resultCode":"0000",      //3333333333
     *             "resultMsg":"Success"
     *         },
     *         "txnToken":"f48bddbdb80c4f15b3b4afc49583a12c1523967528058",  //11111111
     *         "authenticated":true      //4444444444
     *     }
     * }
     *
     * PaytmSDKCallbackListener: Callback listener where SDK will give all the results.
     *
     * MerchantName: Pass your organisation name as a string.
     *
     * DrawableLogo: Pass the Drawable integer of your Logo.
     *
     * Server: Pass the Server type of which you want to obtain the instance of.
     * This is Enum contains values: STAGING, PRE_PRODUCTION, PRODUCTION
     */


    /**
     * Rayn
     * Calls when any UI error occurred.
     */
    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        System.out.println("---Rayn someUIErrorOccurred");
    }

    /**
     * Rayn
     * Calls on successful transaction or failure transaction with proper json as input.
     */
    @Override
    public void onTransactionResponse(Bundle inResponse) {
        System.out.println("---Rayn onTransactionResponse");
    }

    /**
     * Rayn
     * Calls when transaction failed due to network connection error.
     */
    @Override
    public void networkError() {
        System.out.println("---Rayn networkError");

    }

    /**
     * Rayn
     * Will be used in near future.
     */
    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        System.out.println("---Rayn clientAuthenticationFailed");

    }

    /**
     * Rayn
     * Calls when any page don’t get load at last on webview.
     */
    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        System.out.println("---Rayn onErrorLoadingWebPage");

    }

    /**
     * Rayn
     * Calls when user cancel the transaction by pressing back press.
     */
    @Override
    public void onBackPressedCancelTransaction() {
        System.out.println("---Rayn onBackPressedCancelTransaction");

    }

    /**
     * Rayn
     * Calls when any server response get invalid due invalid params passed.
     */
    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        System.out.println("---Rayn onTransactionCancel");

    }

    /**
     * Rayn
     * Calls on unknown error.
     */
    @Override
    public void unknownError(String inErrorMessage) {
        System.out.println("---Rayn unknownError");

    }
}
