package cn.xender.paytm_native.api;

public class ProcessTransactionApi {

    /**
     * Rayn
     *
     * Staging
     * https://securegw-stage.Paytm.in/theia/api/v1/processTransaction?mid=<mid>&orderId=<orderId>
     *
     * Production
     * https://securegw.Paytm.in/theia/api/v1/processTransaction?mid=<mid>&orderId=<orderId>
     */


    private static ProcessTransactionApi instance;
    public static ProcessTransactionApi getInstance() {
        if (instance == null) {
            instance = new ProcessTransactionApi();
        }
        return instance;
    }
}
