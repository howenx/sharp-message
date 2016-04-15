package entity.erp;


import com.iwilley.b1ec2.api.B1EC2Request;
import com.iwilley.b1ec2.api.internal.util.B1EC2HashMap;
import com.iwilley.b1ec2.api.response.SalesOrderQueryResponse;
import java.util.Date;
import java.util.Map;

public class SalesOrderQueryRequestVo implements B1EC2Request<SalesOrderQueryResponse> {
    public Integer whsId;
    public Integer shopId;
    public Integer timeType;
    public Integer status;
    public String orderNo;
    public Date startTime;
    public Date endTime;
    public Integer pageNum;
    public Integer pageSize;
    public String userDefinedField1;
    public String userDefinedField2;
    public String shopOrderNo;

    public SalesOrderQueryRequestVo() {
    }

    public String getApiMethodName() {
        return "B1EC2.SalesOrder.Query";
    }

    public Map<String, String> GetParameters() {
        B1EC2HashMap parameters = new B1EC2HashMap();
        parameters.put("WhsId", this.whsId);
        parameters.put("TimeType", this.timeType);
        parameters.put("OrderNo", this.orderNo);
        parameters.put("ShopId", this.shopId);
        parameters.put("Status", this.status);
        parameters.put("StartTime", this.startTime);
        parameters.put("EndTime", this.endTime);
        parameters.put("PageNum", this.pageNum);
        parameters.put("PageSize", this.pageSize);
        parameters.put("UserDefinedField1", this.userDefinedField1);
        parameters.put("UserDefinedField2", this.userDefinedField2);
        parameters.put("ShopOrderNo", this.shopOrderNo);
        return parameters;
    }

    public String getShopOrderNo() {
        return shopOrderNo;
    }

    public void setShopOrderNo(String shopOrderNo) {
        this.shopOrderNo = shopOrderNo;
    }

    public Class<SalesOrderQueryResponse> getResponseClass() {
        return SalesOrderQueryResponse.class;
    }

    public Integer getWhsId() {
        return this.whsId;
    }

    public void setWhsId(Integer whsId) {
        this.whsId = whsId;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserDefinedField1() {
        return this.userDefinedField1;
    }

    public void setUserDefinedField1(String userDefinedField1) {
        this.userDefinedField1 = userDefinedField1;
    }

    public String getUserDefinedField2() {
        return this.userDefinedField2;
    }

    public void setUserDefinedField2(String userDefinedField2) {
        this.userDefinedField2 = userDefinedField2;
    }

    public Integer getTimeType() {
        return this.timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }
}
