package domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品管理
 * Created by howen on 15/11/11.
 */
public class Item implements Serializable{

    private Long id;        //商品ID
    private Long cateId;    //类别ID
    private Long brandId;   //品牌ID
    private String itemTitle;//商品标题
    private String supplyMerch;//供应商名称
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp onShelvesAt;//上架时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp offShelvesAt;//下架时间
    private String itemMasterImg;   //商品主图
    private String itemDetailImgs;  //商品详细图
    private String itemFeatures;    //商品参数
    private Long themeId;           //主题ID
    private String state;           //状态
    private String shareUrl;        //分享的链接
    private Integer shareCount;     //分享次数
    private Integer collectCount;   //收藏次数
    private Integer browseCount;    //浏览次数
    private String itemNotice;      //商品重要通告
    private Boolean orDestroy;      //是否删除
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp destroyAt;    //删除时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;     //更新时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;     //创建时间
    private Long masterInvId;       //主SKU ID
    private String publicity;       //优惠信息
    private String itemDetail;      //详情页HTML

    //分页,每页多少条
    private Integer pageSize;

    //分页,从第几条开始
    private Integer offset;

    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    private Integer itemNum;

    public Item(){}

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", cateId=" + cateId +
                ", brandId=" + brandId +
                ", itemTitle='" + itemTitle + '\'' +
                ", supplyMerch='" + supplyMerch + '\'' +
                ", onShelvesAt=" + onShelvesAt +
                ", offShelvesAt=" + offShelvesAt +
                ", itemMasterImg='" + itemMasterImg + '\'' +
                ", itemDetailImgs='" + itemDetailImgs + '\'' +
                ", itemFeatures='" + itemFeatures + '\'' +
                ", themeId=" + themeId +
                ", state='" + state + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", shareCount=" + shareCount +
                ", collectCount=" + collectCount +
                ", browseCount=" + browseCount +
                ", itemNotice='" + itemNotice + '\'' +
                ", orDestroy=" + orDestroy +
                ", destroyAt=" + destroyAt +
                ", updateAt=" + updateAt +
                ", createAt=" + createAt +
                ", masterInvId=" + masterInvId +
                ", publicity='" + publicity + '\'' +
                ", itemDetail='" + itemDetail + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", itemNum=" + itemNum +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getSupplyMerch() {
        return supplyMerch;
    }

    public void setSupplyMerch(String supplyMerch) {
        this.supplyMerch = supplyMerch;
    }

    public Timestamp getOnShelvesAt() {
        return onShelvesAt;
    }

    public void setOnShelvesAt(Timestamp onShelvesAt) {
        this.onShelvesAt = onShelvesAt;
    }

    public Timestamp getOffShelvesAt() {
        return offShelvesAt;
    }

    public void setOffShelvesAt(Timestamp offShelvesAt) {
        this.offShelvesAt = offShelvesAt;
    }

    public String getItemMasterImg() {
        return itemMasterImg;
    }

    public void setItemMasterImg(String itemMasterImg) {
        this.itemMasterImg = itemMasterImg;
    }

    public String getItemDetailImgs() {
        return itemDetailImgs;
    }

    public void setItemDetailImgs(String itemDetailImgs) {
        this.itemDetailImgs = itemDetailImgs;
    }

    public String getItemFeatures() {
        return itemFeatures;
    }

    public void setItemFeatures(String itemFeatures) {
        this.itemFeatures = itemFeatures;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public String getItemNotice() {
        return itemNotice;
    }

    public void setItemNotice(String itemNotice) {
        this.itemNotice = itemNotice;
    }

    public Boolean getOrDestroy() {
        return orDestroy;
    }

    public void setOrDestroy(Boolean orDestroy) {
        this.orDestroy = orDestroy;
    }

    public Timestamp getDestroyAt() {
        return destroyAt;
    }

    public void setDestroyAt(Timestamp destroyAt) {
        this.destroyAt = destroyAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Long getMasterInvId() {
        return masterInvId;
    }

    public void setMasterInvId(Long masterInvId) {
        this.masterInvId = masterInvId;
    }

    public String getPublicity() {
        return publicity;
    }

    public void setPublicity(String publicity) {
        this.publicity = publicity;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Item(Long id, Long cateId, Long brandId, String itemTitle, String supplyMerch, Timestamp onShelvesAt, Timestamp offShelvesAt, String itemMasterImg, String itemDetailImgs, String itemFeatures, Long themeId, String state, String shareUrl, Integer shareCount, Integer collectCount, Integer browseCount, String itemNotice, Boolean orDestroy, Timestamp destroyAt, Timestamp updateAt, Timestamp createAt, Long masterInvId, String publicity, String itemDetail, Integer pageSize, Integer offset, String sort, String order, Integer itemNum) {

        this.id = id;
        this.cateId = cateId;
        this.brandId = brandId;
        this.itemTitle = itemTitle;
        this.supplyMerch = supplyMerch;
        this.onShelvesAt = onShelvesAt;
        this.offShelvesAt = offShelvesAt;
        this.itemMasterImg = itemMasterImg;
        this.itemDetailImgs = itemDetailImgs;
        this.itemFeatures = itemFeatures;
        this.themeId = themeId;
        this.state = state;
        this.shareUrl = shareUrl;
        this.shareCount = shareCount;
        this.collectCount = collectCount;
        this.browseCount = browseCount;
        this.itemNotice = itemNotice;
        this.orDestroy = orDestroy;
        this.destroyAt = destroyAt;
        this.updateAt = updateAt;
        this.createAt = createAt;
        this.masterInvId = masterInvId;
        this.publicity = publicity;
        this.itemDetail = itemDetail;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
        this.itemNum = itemNum;
    }
}
