package Simple;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.logging.Level;

public class URLChecker {

    public URLChecker() {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(Level.OFF);
    }

    public boolean checkLinks(String URL){
        HttpResponse response;
        try {
            RequestConfig config= RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setSocketTimeout(7000).build();
            HttpClient client= HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet request = new HttpGet(URL);
            response = client.execute(request);
        } catch (Exception e) {
            System.err.println(URL + " error " + e.getLocalizedMessage());
            return false;
        }
        return  response.getStatusLine().getStatusCode()==200;
    }

    public boolean isFile (String url){
        if ((url.lastIndexOf(".")==url.length()-4) & (!url.endsWith("/"))) {
            return true;
        }
        if ((url.endsWith(".docx")) | (url.endsWith(".jpeg"))) {
            return true;
        }
        return false;
    }
}
