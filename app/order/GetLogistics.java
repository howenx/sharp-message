package order;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by tiffany on 15/12/17.
 */
public class GetLogistics {

    public static String sendGet(String nu,String show,String order){
        /*企业版*/
//        String id = "20425796724eeca6b3";  //授权key
//        String com = "yunda";              //快递公司代码
//        String nu = "203100996353743";     //快递单号
//        String show = "2";                 //返回类型  0：返回json字符串，1：返回xml对象，2：返回html对象，3：返回text文本。 默认返回json字符串。
//        String muti = "1";                 //返回信息数量：1:返回多行完整的信息，0:只返回一行信息。默认返回多行。
//        String order = "desc";             //排序：desc：按时间由新到旧排列，asc：按时间由旧到新排列。不填默认返回倒序（大小写不敏感）
//        String url = "http://api.kuaidi100.com/api?id=" + id + "&com="+ com +"&nu="+ nu +"&show="+ show +"&muti="+ muti +"&order=" + order;   //快递100 API 地址

        String url = "http://api.kuaidi100.com/api";
        Map<String,String> params = new HashMap<>();
        params.put("id","425796724eeca6b3");         //授权Key
        params.put("com","yunda");                   //快递公司代码
        params.put("nu",nu);                         //快递单号
        params.put("valicode","");                   //已弃用,无意义
        params.put("show",show);                     //返回类型  0：返回json字符串，1：返回xml对象，2：返回html对象，3：返回text文本。 默认返回json字符串。
        params.put("muti","1");                      //返回信息数量：1:返回多行完整的信息，0:只返回一行信息。默认返回多行。
        params.put("order",order);                   //排序：desc：按时间由新到旧排列，asc：按时间由旧到新排列。不填默认返回倒序（大小写不敏感）
        JsonNode jsonParams = Json.toJson(params);

        String result = "";
        try{
            WSResponse response = WS.url(url).post(jsonParams).get(1000L);
            //WSResponse response = WS.url(url).get().get(1000L);
            //result = new String(response.getBody().getBytes("ISO8859-1"),"utf-8");
            //int endIndex = result.indexOf("|");
            //result = result.substring(1,endIndex);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
