package filters;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.libs.Codecs;

/**
 *
 * Created by howen on 15/10/23.
 */
public class Global extends GlobalSettings {

    public void onStart(Application app) {//作为系统启动后的第一次请求调用
        try {
            Request request = new Request.Builder().url(play.Play.application().configuration().getString("message.server.url")+"/schedule/"+ Codecs.md5("hmm-100901".getBytes())).build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseUrl = response.body().string();
                Logger.error("启动调用:\n"+responseUrl);
            }else client.newCall(request).execute();
        } catch (Exception ignored) {
        }
    }
}
