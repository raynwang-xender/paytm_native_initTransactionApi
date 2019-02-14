package cn.xender.paytm_native.event;

public class InitialTransactionEvent {
    private String json;
    public InitialTransactionEvent(String json){
        this.json = json;
    }

    public String getJson() {
        return json;
    }


}
