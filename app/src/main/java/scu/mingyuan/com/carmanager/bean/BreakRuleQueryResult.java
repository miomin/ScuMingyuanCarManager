package scu.mingyuan.com.carmanager.bean;

import java.io.Serializable;

/**
 * Created by miomin on 16/3/20.
 */
public class BreakRuleQueryResult implements Serializable {

    private String resultcode;
    private String reason;
    private String result;
    private int error_code;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }
}
