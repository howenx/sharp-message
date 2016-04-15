package controllers.edit;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by hxdhan on 15/2/16.
 * change by tony on 15/10/20
 * daumkakao china
 */
public class EditorOSSFile {

    private String bucket;

    public String prefix = "";

    public String content_type;

    public String name;

    public File file;
    
    public InputStream is;

    private static DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

    public String get_key() {
        if(prefix == null) {
            prefix = "";
        }
        Random rnd = new Random();
        int n = 100 + rnd.nextInt(900);
        String filename = "" + System.currentTimeMillis() + n;
        String extension = name.replaceFirst("^[^.]*","");
        DateTime dt = new DateTime();
        String directory = fmt.print(dt);
        //createDirectory(prefix + "/" + directory + "/");
        return prefix + "/" + directory + "/" + filename + extension;
    }
    
    public String getKey(String filename) {
        if(prefix == null) {
            prefix = "";
        }
        DateTime dt = new DateTime();
        String directory = fmt.print(dt);
        return prefix + "/" + directory + "/" + filename;
    }

    /**
     * 好像不需要创建目录，就可以直接创建路径
    private void  createDirectory(String directory) {
        if(OSSPlugin.oss_client == null) {
            Logger.error("Could not create directory because OSS client was null");
            throw new RuntimeException("Could not create directory");
        }
        else {
            this.bucket = OSSPlugin.bucket;
            //if(OSSPlugin.oss_client.getObject(bucket, directory) == null) {
            byte[] buffer = new byte[0];
            try (
                    ByteArrayInputStream in = new ByteArrayInputStream(buffer);
            ){
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentLength(0);
                PutObjectResult result = OSSPlugin.oss_client.putObject(bucket, directory, in, meta);

            }catch(IOException e) {
                Logger.debug(e.toString());
            }
            // }
        }


    }
     **/

    public void save (String key) {
//        if(OSSPlugin.oss_client == null) {
//            Logger.error("Could not save because OSS client was null");
//            throw new RuntimeException("Could not save");
//        }
//        else {
//            this.bucket = OSSPlugin.bucket;
//            try (
//                    InputStream content = new FileInputStream(file);
//            ){
//                ObjectMetadata meta = new ObjectMetadata();
//                meta.setContentLength(file.length());
//                meta.setContentType(content_type);
//                PutObjectResult result = OSSPlugin.oss_client.putObject(bucket, key, content, meta);
//                Logger.debug(result.getETag());
//            }catch(IOException e) {
//                Logger.debug(e.toString());
//            }
//
//        }
    }

    public void saveInputStream (String key) {
//        if(OSSPlugin.oss_client == null) {
//            Logger.error("Could not save because OSS client was null");
//            throw new RuntimeException("Could not save");
//        }
//        else {
//        	 try {
//            this.bucket = OSSPlugin.bucket;
//            ObjectMetadata meta = new ObjectMetadata();
//            meta.setContentLength(is.available());
//            meta.setContentType(content_type);
//            PutObjectResult result = OSSPlugin.oss_client.putObject(bucket, key, is, meta);
//            Logger.debug(result.getETag());
//        	 }catch(Exception e) {
//                 Logger.debug(e.toString());
//             }
//        }
    }

    public void saveInputStreamLenth (String key,int length) {
//        if(OSSPlugin.oss_client == null) {
//            Logger.error("Could not save because OSS client was null");
//            throw new RuntimeException("Could not save");
//        }
//        else {
//        	 try {
//            this.bucket = OSSPlugin.bucket;
//            ObjectMetadata meta = new ObjectMetadata();
//            meta.setContentLength(length);
//            meta.setContentType(content_type);
//            PutObjectResult result = OSSPlugin.oss_client.putObject(bucket, key, is, meta);
//            Logger.debug(result.getETag());
//        	 }catch(Exception e) {
//                 Logger.debug(e.toString());
//             }
//        }
    }

    //TODO 放到具体的下载功能中实现
    //oss_client get object 返回文件流和content_type，实际中具体实现中比较方便
    public void download(String key) {

    }


    public void delete(String key) {
//        if(OSSPlugin.oss_client == null) {
//            Logger.error("Could not delete because OSS client was null");
//            throw new RuntimeException("Could not delete");
//        }
//        else {
//            this.bucket = OSSPlugin.bucket;
//            OSSPlugin.oss_client.deleteObject(bucket,key);
//        }
//
    }
    
    

}
