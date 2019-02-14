package cn.xender.paytm_native.bean;

public class InitialTransactionJson {


    @Override
    public String toString() {
        return "{" +
                "\"head\":" + head +
                ", \"body\":" + body +
                "}";
    }

    private Head head;
    private Body body;
    public void setHead(Head head) {
        this.head = head;
    }
    public Head getHead() {
        return head;
    }

    public void setBody(Body body) {
        this.body = body;
    }
    public Body getBody() {
        return body;
    }

    public class Head {
        @Override
        public String toString() {
            return "{" +
                    "\"responseTimestamp\":\"" + responseTimestamp + "\"" +
                    ", \"version\":\"" + version + "\"" +
                    ", \"clientId\":\"" + clientId + "\"" +
                    ", \"signature\":\"" + signature + "\"" +
                    "}";
        }

        private String responseTimestamp;
        private String version;
        private String clientId;
        private String signature;
        public void setResponseTimestamp(String responseTimestamp) {
            this.responseTimestamp = responseTimestamp;
        }
        public String getResponseTimestamp() {
            return responseTimestamp;
        }

        public void setVersion(String version) {
            this.version = version;
        }
        public String getVersion() {
            return version;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
        public String getClientId() {
            return clientId;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
        public String getSignature() {
            return signature;
        }

    }

    public class Body {
        @Override
        public String toString() {
            return "{" +
                    "\"resultInfo\":" + resultInfo +
                    ", \"txnToken\":\"" + txnToken + "\"" +
                    ", \"isPromoCodeValid\":" + isPromoCodeValid +
                    ", \"authenticated\":" + authenticated +
                    "}";
        }

        private ResultInfo resultInfo;
        private String txnToken;
        private boolean isPromoCodeValid;
        private boolean authenticated;
        public void setResultInfo(ResultInfo resultInfo) {
            this.resultInfo = resultInfo;
        }
        public ResultInfo getResultInfo() {
            return resultInfo;
        }

        public void setTxnToken(String txnToken) {
            this.txnToken = txnToken;
        }
        public String getTxnToken() {
            return txnToken;
        }

        public void setIsPromoCodeValid(boolean isPromoCodeValid) {
            this.isPromoCodeValid = isPromoCodeValid;
        }
        public boolean getIsPromoCodeValid() {
            return isPromoCodeValid;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }
        public boolean getAuthenticated() {
            return authenticated;
        }

        public class ResultInfo {
            @Override
            public String toString() {
                return "{" +
                        "\"resultStatus\":\"" + resultStatus + "\"" +
                        ", \"resultCode\":\"" + resultCode + "\"" +
                        ", \"resultMsg\":\"" + resultMsg + "\"" +
                        "}";
            }

            private String resultStatus;
            private String resultCode;
            private String resultMsg;
            public void setResultStatus(String resultStatus) {
                this.resultStatus = resultStatus;
            }
            public String getResultStatus() {
                return resultStatus;
            }

            public void setResultCode(String resultCode) {
                this.resultCode = resultCode;
            }
            public String getResultCode() {
                return resultCode;
            }

            public void setResultMsg(String resultMsg) {
                this.resultMsg = resultMsg;
            }
            public String getResultMsg() {
                return resultMsg;
            }

        }

    }

}
