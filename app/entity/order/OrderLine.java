package entity.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品
 * Created by tiffany on 15/12/17.
 */
public class OrderLine implements Serializable {

    private Long lineId;
    private Long orderId;       //订单Id
    private Long skuId;         //sku ID
    private Long itemId;        //商品Id
    private Integer amount;     //购买数量
    private BigDecimal price;   //购买价格
    private String skuTitle;    //sku标题
    private String skuImg;      //sku图片url
    private Long splitId;       //子订单编号
    private String skuSize;     //商品尺寸
    private String skuColor;    //商品颜色

    public OrderLine() {
    }

    public OrderLine(Long lineId, Long orderId, Long skuId, Long itemId, Integer amount, BigDecimal price, String skuTitle, String skuImg, Long splitId, String skuSize, String skuColor) {
        this.lineId = lineId;
        this.orderId = orderId;
        this.skuId = skuId;
        this.itemId = itemId;
        this.amount = amount;
        this.price = price;
        this.skuTitle = skuTitle;
        this.skuImg = skuImg;
        this.splitId = splitId;
        this.skuSize = skuSize;
        this.skuColor = skuColor;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "lineId=" + lineId +
                ", orderId=" + orderId +
                ", skuId=" + skuId +
                ", itemId=" + itemId +
                ", amount=" + amount +
                ", price=" + price +
                ", skuTitle='" + skuTitle + '\'' +
                ", skuImg='" + skuImg + '\'' +
                ", splitId=" + splitId +
                ", skuSize='" + skuSize + '\'' +
                ", skuColor='" + skuColor + '\'' +
                '}';
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public Long getSplitId() {
        return splitId;
    }

    public void setSplitId(Long splitId) {
        this.splitId = splitId;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }
}
