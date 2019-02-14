package cn.xender.paytm_native;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import net.one97.paytm.nativesdk.PaytmSDK;
import net.one97.paytm.nativesdk.Utils.Server;
import net.one97.paytm.nativesdk.app.PaytmSDKCallbackListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    final String mid_stage = "ONONPR28903535505911";
    final String orderId = "test567890";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void InitialTransaction(View view) {
        InitialTransactionApi.getInstance().getJson(mid_stage,orderId);
    }

}
