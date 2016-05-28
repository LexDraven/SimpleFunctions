package Net;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class NetSpider {
    private LinkedList<String> linksInDomain;
    private LinkedList <String> brokenLinks;
    private HashSet<String> checkedLinks;
    private HtmlUnitDriver browser;
    private static String mainDomen;


    public NetSpider(String mainDomen) {
        this.mainDomen = mainDomen;
        browser = new HtmlUnitDriver(false);
        linksInDomain = new LinkedList<String>();
        brokenLinks = new LinkedList<String>();
        checkedLinks = new HashSet<String>();
    }

    public void checkLinksOnPage(){
        linksInDomain.add(mainDomen);
        checkedLinks.add(mainDomen);
        while (!linksInDomain.isEmpty()){
            String link = linksInDomain.poll();
            browser.get(link);
            System.out.println("Go to page "+link);
            getAllLinks();
        }
        System.out.println("Checked links: "+checkedLinks.size());
        System.out.println("Broken links: "+brokenLinks.size());
        if (brokenLinks.size()>0){
            System.out.println("Broken links are:");
            for (String name :brokenLinks) {
                System.out.println(name);
            }
        }
    }

    private void getAllLinks(){
        List<WebElement> linksOnPage = browser.findElements(By.tagName("a"));
        if (linksOnPage.size()>0){
            System.out.println("Links on page - "+linksOnPage.size());
            for (WebElement element:linksOnPage){
                if (element != null) {
                    String url = element.getAttribute("href");
                    if (url != null && !url.contains("javascript")) {
                        if (!url.startsWith("mailto")) {
                            if ((!checkedLinks.contains(url)) & (!brokenLinks.contains(url)) & (!linksInDomain.contains(url))) {
                                checkedLinks.add(url);
                                try {
                                    if (!(checkLinks(url))) {
                                        brokenLinks.add(url);
                                    }
                                    else {
                                        if (url.startsWith(mainDomen)){
                                            linksInDomain.add(url);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkLinks(String URL){
        HttpResponse response = null;
        try {
            RequestConfig config= RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setSocketTimeout(7000).build();
            HttpClient client= HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet request = new HttpGet(URL);
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response.getStatusLine().getStatusCode()==200;
    }

}
