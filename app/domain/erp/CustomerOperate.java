package domain.erp;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.request.CustomerCreateRequest;
import com.iwilley.b1ec2.api.response.CustomerCreateResponse;
import util.SysParCom;

/**
 * Created by Sunny Wu on 16/5/10.
 * kakao china.
 * ERP 会员的操作
 */
public class CustomerOperate {

    public String customerCreate(CustomerCreateRequest request) throws ApiException {
        B1EC2Client client = new B1EC2Client(SysParCom.URL, SysParCom.COMPANY, SysParCom.LOGIN_NAME, SysParCom.PASSWORD, SysParCom.SECRET);
        CustomerCreateResponse response = client.execute(request);
        return response.getBody();
    }

}
