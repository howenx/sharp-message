package domain.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单报关
 * Created by tiffany on 15/12/17.
 */
public class OrderSplit implements Serializable {
    private Long splitId;
    private Long orderId;               //订单Id
    private String state;               //报关状态
    private String cbeCode;             //报关单位名称
    private String inspReturnCode;      //国检部门返回Code
    private String inspReturnMsg;       //国检部门返回消息内容
    private String customsReturnCode;   //海关返回码
    private String customsReturnMsg;    //海关返回消息内容
    private BigDecimal totalFee;        //商品总计费用
    private BigDecimal totalPayFee;     //是付费用总计
    private Integer totalAmount;        //购买数量
    private BigDecimal shipFee;         //邮费
    private BigDecimal postalFee;       //行邮税
    private String expressNum;          //快递编号
    private String expressCode;         //快递名称编码
    private String expressNm;           //快递名称

    public OrderSplit() {
    }

    public OrderSplit(Long splitId, Long orderId, String state, String cbeCode, String inspReturnCode, String inspReturnMsg, String customsReturnCode, String customsReturnMsg, BigDecimal totalFee, BigDecimal totalPayFee, Integer totalAmount, BigDecimal shipFee, BigDecimal postalFee, String expressNum, String expressCode, String expressNm) {
        this.splitId = splitId;
        this.orderId = orderId;
        this.state = state;
        this.cbeCode = cbeCode;
        this.inspReturnCode = inspReturnCode;
        this.inspReturnMsg = inspReturnMsg;
        this.customsReturnCode = customsReturnCode;
        this.customsReturnMsg = customsReturnMsg;
        this.totalFee = totalFee;
        this.totalPayFee = totalPayFee;
        this.totalAmount = totalAmount;
        this.shipFee = shipFee;
        this.postalFee = postalFee;
        this.expressNum = expressNum;
        this.expressCode = expressCode;
        this.expressNm = expressNm;
    }

    @Override
    public String toString() {
        return "OrderSplit{" +
                "splitId=" + splitId +
                ", orderId=" + orderId +
                ", state='" + state + '\'' +
                ", cbeCode='" + cbeCode + '\'' +
                ", inspReturnCode='" + inspReturnCode + '\'' +
                ", inspReturnMsg='" + inspReturnMsg + '\'' +
                ", customsReturnCode='" + customsReturnCode + '\'' +
                ", customsReturnMsg='" + customsReturnMsg + '\'' +
                ", totalFee=" + totalFee +
                ", totalPayFee=" + totalPayFee +
                ", totalAmount=" + totalAmount +
                ", shipFee=" + shipFee +
                ", postalFee=" + postalFee +
                ", expressNum='" + expressNum + '\'' +
                ", expressCode='" + expressCode + '\'' +
                ", expressNm='" + expressNm + '\'' +
                '}';
    }

    public Long getSplitId() {
        return splitId;
    }

    public void setSplitId(Long splitId) {
        this.splitId = splitId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCbeCode() {
        return cbeCode;
    }

    public void setCbeCode(String cbeCode) {
        this.cbeCode = cbeCode;
    }

    public String getInspReturnCode() {
        return inspReturnCode;
    }

    public void setInspReturnCode(String inspReturnCode) {
        this.inspReturnCode = inspReturnCode;
    }

    public String getInspReturnMsg() {
        return inspReturnMsg;
    }

    public void setInspReturnMsg(String inspReturnMsg) {
        this.inspReturnMsg = inspReturnMsg;
    }

    public String getCustomsReturnCode() {
        return customsReturnCode;
    }

    public void setCustomsReturnCode(String customsReturnCode) {
        this.customsReturnCode = customsReturnCode;
    }

    public String getCustomsReturnMsg() {
        return customsReturnMsg;
    }

    public void setCustomsReturnMsg(String customsReturnMsg) {
        this.customsReturnMsg = customsReturnMsg;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getTotalPayFee() {
        return totalPayFee;
    }

    public void setTotalPayFee(BigDecimal totalPayFee) {
        this.totalPayFee = totalPayFee;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public BigDecimal getPostalFee() {
        return postalFee;
    }

    public void setPostalFee(BigDecimal postalFee) {
        this.postalFee = postalFee;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressNm() {
        return expressNm;
    }

    public void setExpressNm(String expressNm) {
        this.expressNm = expressNm;
    }
}
