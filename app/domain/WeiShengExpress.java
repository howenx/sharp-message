package domain;

import java.io.Serializable;

/**
 * Created by Sunny Wu on 16/5/11.
 * kakao china.
 */
public class WeiShengExpress implements Serializable {

    private String trackingId;  //威盛快递单号
    private String expressNo;   //国内快递单号
    private Boolean orUse;      //是否使用

    public WeiShengExpress() {
    }

    public WeiShengExpress(String trackingId, String expressNo, Boolean orUse) {
        this.trackingId = trackingId;
        this.expressNo = expressNo;
        this.orUse = orUse;
    }

    @Override
    public String toString() {
        return "WeiShengExpress{" +
                "trackingId='" + trackingId + '\'' +
                ", expressNo='" + expressNo + '\'' +
                ", orUse=" + orUse +
                '}';
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Boolean getOrUse() {
        return orUse;
    }

    public void setOrUse(Boolean orUse) {
        this.orUse = orUse;
    }
}
