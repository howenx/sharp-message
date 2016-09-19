package domain.order;

import java.io.Serializable;

/**
 * 订单申报
 * Created by Sunny Wu on 16/9/19.
 * kakao china.
 */
public class OrderCustoms implements Serializable {

    private Long customsId;         //主键
    private Long orderId;           //订单ID
    private String declaraStatus;   //订单申报状态  N 未申报 (默认) ，S:申报成功，  F:申报失败
    private String declaraResult;   //订单申报返回结果
    private String declaraNo;       //申报备案号
    private String expressStatus;   //物流推送状态  N 未推送 (默认) ，S:推送成功，  F:推送失败
    private String expressResult;   //物流推送返回结果
    private String payStatus;       //支付推送状态  N 未推送(默认) ，S:推送成功，  F:推送失败
    private String payResult;       //支付推送返回结果

    public OrderCustoms() {
    }

    @Override
    public String toString() {
        return "OrderCustoms{" +
                "customsId=" + customsId +
                ", orderId=" + orderId +
                ", declaraStatus='" + declaraStatus + '\'' +
                ", declaraResult='" + declaraResult + '\'' +
                ", declaraNo='" + declaraNo + '\'' +
                ", expressStatus='" + expressStatus + '\'' +
                ", expressResult='" + expressResult + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", payResult='" + payResult + '\'' +
                '}';
    }

    public Long getCustomsId() {
        return customsId;
    }

    public void setCustomsId(Long customsId) {
        this.customsId = customsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDeclaraStatus() {
        return declaraStatus;
    }

    public void setDeclaraStatus(String declaraStatus) {
        this.declaraStatus = declaraStatus;
    }

    public String getDeclaraResult() {
        return declaraResult;
    }

    public void setDeclaraResult(String declaraResult) {
        this.declaraResult = declaraResult;
    }

    public String getDeclaraNo() {
        return declaraNo;
    }

    public void setDeclaraNo(String declaraNo) {
        this.declaraNo = declaraNo;
    }

    public String getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(String expressStatus) {
        this.expressStatus = expressStatus;
    }

    public String getExpressResult() {
        return expressResult;
    }

    public void setExpressResult(String expressResult) {
        this.expressResult = expressResult;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public OrderCustoms(Long customsId, Long orderId, String declaraStatus, String declaraResult, String declaraNo, String expressStatus, String expressResult, String payStatus, String payResult) {

        this.customsId = customsId;
        this.orderId = orderId;
        this.declaraStatus = declaraStatus;
        this.declaraResult = declaraResult;
        this.declaraNo = declaraNo;
        this.expressStatus = expressStatus;
        this.expressResult = expressResult;
        this.payStatus = payStatus;
        this.payResult = payResult;
    }
}
