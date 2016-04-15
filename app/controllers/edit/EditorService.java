package controllers.edit;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import play.Logger;
import play.Play;
import play.libs.Json;
import play.mvc.Http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EditorService {
	
	public static final String STATIC_URL = Play.application().configuration().getString("photoUrl");
	
	private static volatile EditorService editorService = null;

	public static EditorService getInstance(){
		EditorService result = editorService;
		if(result == null) {
			synchronized (EditorService.class) {
				result = editorService;
				if(result == null) {
					editorService = result = new EditorService();
				}
			}
		}
		return result;
	}
	
	public String uploadImage(Http.MultipartFormData.FilePart picture, String typeName) {
		String imageUrl = "";
//		 if(picture != null){
//			 if (picture.getContentType().startsWith("image")) {
//				 EditorOSSFile oss_file = new EditorOSSFile();
//	                oss_file.prefix = typeName;
//	                oss_file.content_type = picture.getContentType();
//	                oss_file.file = picture.getFile();
//	                oss_file.name = picture.getFilename();
//	                String key = oss_file.get_key();
//	                oss_file.save(key);
//	                imageUrl = "/"+key;
//		        }
//		}
		return imageUrl;
	}
	
	/**
	 * 得到上传文件所对应的各个参数,数组结构
	 * array(
	 *     "state" => "",          //上传状态，上传成功时必须返回"SUCCESS"
	 *     "url" => "",            //返回的地址
	 *     "title" => "",          //新文件名
	 *     "original" => "",       //原始文件名
	 *     "type" => ""            //文件类型
	 *     "size" => "",           //文件大小
	 * )
	 * picture提交表单中的file typeName 上传oss后文件夹名称
	 */
	
	public HashMap<String,String> editorUploadFile(Http.MultipartFormData.FilePart picture, String typeName) throws Exception{

//		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//		   Reader scriptReader = new InputStreamReader(EditorService.class.getResourceAsStream("/public/js/edit_img_upload.js"));
//		if (engine != null) {
//			try {
//				// JS引擎解析文件
//				engine.eval(scriptReader);
//				if (engine instanceof Invocable) {
//					Invocable invocable = (Invocable) engine;
//					// JS引擎调用方法
//					Object result = invocable.invokeFunction("upload", scriptReader, "D");
//					System.out.println("The result is: " + result);
//				}
//			} catch (ScriptException e) {
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} finally {
//				scriptReader.close();
//			}
//		} else {
//			System.out.println("ScriptEngine create error!");
//		}
		HashMap<String,String> map = new HashMap<String,String>();
		String url = "";
		if(picture != null){
			EditorOSSFile oss_file = new EditorOSSFile();
            oss_file.prefix = typeName;
            oss_file.content_type = picture.getContentType();
            oss_file.file = picture.getFile();
            oss_file.name = picture.getFilename();
//
            Random rnd = new Random();
            int n = 100 + rnd.nextInt(900);
            String filename = "" + System.currentTimeMillis() + n;
            String extension = picture.getFilename().replaceFirst("^[^.]*","");

//            String key = oss_file.getKey(filename+extension);
//            oss_file.save(key);
//            imageUrl = "/"+key;
            map.put("state", "SUCCESS");
    		map.put("url", url);
    		map.put("title", filename+extension);
    		map.put("original", picture.getFilename());
    		map.put("type", picture.getContentType());
    		map.put("size", String.valueOf(picture.getFile().length()));
    		return map;
		}else{
			return null;
		}
	}
	/**
	 * 得到上传文件所对应的各个参数,数组结构
	 * array(
	 *     "state" => "",          //上传状态，上传成功时必须返回"SUCCESS"
	 *     "url" => "",            //返回的地址
	 *     "title" => "",          //新文件名
	 *     "original" => "",       //原始文件名
	 *     "type" => ""            //文件类型
	 *     "size" => "",           //文件大小
	 * )
	 * String:editor插件上传的string typeName 上传oss后文件夹名称
	 */
	public HashMap<String,Object> editorUploadBase64File(String str,String typeName) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		String imageUrl = "";
		try {
			byte[] deCode = Base64.decodeBase64(str.getBytes());
			InputStream is = new ByteArrayInputStream(deCode);
			EditorOSSFile oss_file = new EditorOSSFile();
            oss_file.prefix = typeName;
            oss_file.content_type = "image/png";
            oss_file.is = is;
            oss_file.name = "";//name

            Random rnd = new Random();
            int n = 100 + rnd.nextInt(900);
            String filename = "" + System.currentTimeMillis() + n;
            String extension = ".png";
            String key = oss_file.getKey(filename+extension);
//            oss_file.saveInputStreamLenth(key,deCode.length);
            is.close();

            imageUrl = "/"+key;
            map.put("state", "SUCCESS");
    		map.put("url", STATIC_URL+imageUrl);
    		map.put("title", filename+extension);
    		map.put("original", "");// old name
    		map.put("type", "image/png");
    		map.put("size", deCode.length);
    		return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到上传文件所对应的各个参数,数组结构
	 array_push($list, array(
	        "state" => $info["state"],
	        "url" => $info["url"],
	        "size" => $info["size"],
	        "title" => htmlspecialchars($info["title"]),
	        "original" => htmlspecialchars($info["original"]),
	        "source" => htmlspecialchars($imgUrl)
	    ));
	 * String:editor插件上传的string typeName 上传oss后文件夹名称
	 */
	

	public ArrayList<JsonNode> catchImage(String[] listPath, String typeName){
		ArrayList<JsonNode> list = new ArrayList<JsonNode>();
		for(int i =0;i<listPath.length;i++){
			String path = listPath[i];
			Logger.info("远程文件路径："+path);
			HashMap<String,Object> map = new HashMap<String,Object>();
			try {
				HttpURLConnection conn = null;
	            InputStream is = null;
	            URL picUrl = new URL(path);
	            conn = (HttpURLConnection) picUrl.openConnection();
	            conn.setConnectTimeout(2000);
	            conn.setReadTimeout(2000);
	            conn.connect();
	            int lenth = conn.getContentLength();
	            is = conn.getInputStream();
				EditorOSSFile oss_file = new EditorOSSFile();
		        oss_file.prefix = typeName;
		        oss_file.content_type = "image/png";
		        oss_file.is = is;
		        oss_file.name = "";//name

		        Random rnd = new Random();
		        int n = 100 + rnd.nextInt(900);
		        String filename = "" + System.currentTimeMillis() + n;
		        String extension = ".png";

		        String key = oss_file.getKey(filename+extension);
//		        oss_file.saveInputStreamLenth(key,lenth);
		        is.close();
		        String imageUrl = "/"+key;
		        map.put("state", "SUCCESS");
				map.put("url", STATIC_URL+imageUrl);
				map.put("size", lenth);
				map.put("title", filename+extension);
				map.put("original", "");// old name
				map.put("source", StringEscapeUtils.unescapeJava(path));
				Logger.info(STATIC_URL+imageUrl);
				list.add(Json.toJson(map));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return list;

	}
	
	/* 返回数据 */
//	$result = json_encode(array(
//	    "state" => "SUCCESS",
//	    "list" => $list,
//	    "start" => $start,
//	    "total" => count($files)
//	));
	
//	public HashMap<String,Object> listImage(String path,int start,int end){
//		HashMap<String,Object> map = new HashMap<String,Object>();
//		ArrayList<JsonNode> list = listObjects(path);
//		if(end>list.size()){
//			end = list.size();
//		}
//		map.put("state", "SUCCESS");
//		map.put("list", list.subList(start, end));
//		map.put("start", start);
//		map.put("total", list.size());
//		return map;
//	}
	
//	获取指定目录下边的文件
//	public  ArrayList<JsonNode> listObjects(String path) {
//        // 初始化OSSClient
//    	if(OSSPlugin.oss_client == null) {
//            Logger.error("Could not delete because OSS client was null");
//            throw new RuntimeException("Could not delete");
//        }
//    	ArrayList<JsonNode> list = new ArrayList<JsonNode>();
//    	// 构造ListObjectsRequest请求
//    	ListObjectsRequest listObjectsRequest = new ListObjectsRequest(OSSPlugin.bucket);
//
//    	// 递归列出path目录下的所有文件
//    	listObjectsRequest.setPrefix(path);
//
//    	ObjectListing listing = OSSPlugin.oss_client.listObjects(listObjectsRequest);
//
//    	// 遍历所有Object
//    	for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
//    		HashMap<String,String> map = new HashMap<String,String> ();
//    		map.put("url", STATIC_URL+"/"+objectSummary.getKey());
//    		map.put("mtime", String.valueOf(objectSummary.getLastModified().getTime()).substring(0,10));
//    	    list.add(Json.toJson(map));
//    	}
//        return list;
//    }
	
	
	public String inputStreamString(InputStream in) {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		try {
			for (int n; (n = in.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}
	

}
