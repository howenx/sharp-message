package entity.erp;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.domain.ItemInfo;
import com.iwilley.b1ec2.api.request.ItemInfoQueryRequest;
import com.iwilley.b1ec2.api.request.ShopItemPushRequest;
import com.iwilley.b1ec2.api.response.ItemInfoQueryResponse;
import com.iwilley.b1ec2.api.response.ShopItemPushResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu on 16/2/23.
 * kakao china.
 */
public class ShopItemOperate {

    /**
     * 根据条件查询erp中商品资料
     * @param startTime 商品修改开始时间
     * @param endTime 商品修改结束时间
     * @return List of ItemInfo
     * @throws ApiException
     * @throws ParseException
     */
    public List<ItemInfo> ItemInfoQuery(String startTime, String endTime) throws ApiException, ParseException {
        B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY, Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);
        List<ItemInfo> itemInfoList = new ArrayList<>();
        DateFormat format = new SimpleDateFormat(com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);
        int pageSize = 30;
        ItemInfoQueryRequest request = new ItemInfoQueryRequest();
        request.setStartTime(format.parse(startTime));
        request.setEndTime(format.parse(endTime));
        request.setPageSize(pageSize);
        ItemInfoQueryResponse response = client.execute(request);
        if (response.getErrorCode()==0 && response.getTotalResults()>0) {
            //由总条数和每页条数得出总页数
            int totalPages = (int) Math.ceil((double) response.getTotalResults()/pageSize);
            for(int i=1;i<=totalPages;i++) {
                request.setPageNum(i);
                //每页的数据
                response = client.execute(request);
                for(ItemInfo itemInfo : response.getItemInfos()) {
                    //所有数据压入一个集合中
                    itemInfoList.add(itemInfo);
                }
            }
        }
        return itemInfoList;
    }

    /**
     * style商品推送到ERP
     * @param request ShopItemPushRequest
     * @return shopItemCode
     * @throws ApiException
     */
    public String ShopItemPush(ShopItemPushRequest request) throws ApiException {
        B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY, Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);
        ShopItemPushResponse response = client.execute(request);
        return response.getBody();
    }

}
