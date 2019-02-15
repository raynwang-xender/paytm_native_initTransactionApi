package cn.xender.paytm_native;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    final String mid_stage = "ONONPR28903535505911";
    final String orderId = "test56789013123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void InitialTransaction(View view) {
        InitialTransactionApi.getInstance().getJson(mid_stage,orderId);
    }

}
