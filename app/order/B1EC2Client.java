package order;

import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;




/**
 * Created by handy on 15/12/11.
 * kakao china
 */
public class B1EC2Client {

    //TODO 这里应该更换成我们的配置
    private static String Company = "北京可靠";
    private static String LoginName = "manager";
    private static String Password = "iwilley";
    //private static String Method = "Method";
    //private static String Format = "Format";
    //private static String Version = "Version";
    private static String Secret = "lRINGlxXEAiXC2A9umPSboxW8Y1UArdH";


    public static<T> T post (String url, String method, Map<String, Object> params, Class<T> clazz) {

        if(!url.toUpperCase().startsWith("HTTP")) {
            url = "http://" + url;
        }

        if(!url.toUpperCase().endsWith("REST")) {
            url = url + "/REST";
        }

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Company", Base64.encodeBase64String(Company.getBytes()));
        headers.put("LoginName",Base64.encodeBase64String(LoginName.getBytes()));
        headers.put("Password",Base64.encodeBase64String(Password.getBytes()));
        headers.put("Method", method);
        //headers.put("Format",Format);
        headers.put("Version","1.0");
        headers.put("Sign",RestClient.create_sign(params,Secret));

        return RestClient.post(url, headers, params, clazz);


    }



}
