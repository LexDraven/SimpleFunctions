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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class NetSpider {
    private LinkedList<String> linksInDomain;
    private HashMap<String,String> brokenLinks;
    private HashSet<String> checkedLinks;
    private HtmlUnitDriver browser;
    private String mainDomain;
    private File textfile;
    private PrintWriter writer;

    public NetSpider(String mainDomen) {
        this.mainDomain = mainDomen;
        browser = new HtmlUnitDriver(false);
        linksInDomain = new LinkedList<String>();
        brokenLinks = new HashMap<String, String>();
        checkedLinks = new HashSet<String>();
        textfile = new File("broken.txt");
        try {
            writer = new PrintWriter(textfile);
        } catch (FileNotFoundException e) {
            System.out.println("can't find file!");
            System.exit(11);
        }
    }

    public void setNewDomain (String newDomain){
        mainDomain = newDomain;
        linksInDomain.clear();
        brokenLinks.clear();
        checkedLinks.clear();
    }

    public void checkLinksInDomain(){
        linksInDomain.add(mainDomain);
        checkedLinks.add(mainDomain);
        long begin = System.currentTimeMillis();
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
            for (Map.Entry<String, String> pair: brokenLinks.entrySet()) {
                System.out.println(pair.getKey()+" - on page "+pair.getValue());
            }
        }
        long time = (System.currentTimeMillis() - begin)/1000;
        System.out.println("Total time spent: "+time +" sec");
        writer.flush();
        writer.close();
    }

    private void getAllLinks(){
        List<WebElement> linksOnPage = browser.findElements(By.tagName("a"));
        if (linksOnPage.size()>0){
            System.out.println("Links on page - "+linksOnPage.size());
            for (WebElement element:linksOnPage){
                if (element != null) {
                    String url = element.getAttribute("href");
                    if (url != null && !url.contains("javascript")) {
                        if ((!url.startsWith("mailto")) & (!url.endsWith(".jpg")) & (!url.endsWith(".png")) & (!url.endsWith(".gif"))) {
                            if (!checkedLinks.contains(url)) {
                                checkedLinks.add(url);
                                try {
                                    if (!(checkLinks(url))) {
                                        brokenLinks.put(url, browser.getCurrentUrl());
                                        writer.print(url+" - on page "+browser.getCurrentUrl());
                                        writer.flush();
                                    }
                                    else {
                                        if (url.startsWith(mainDomain)){
                                            linksInDomain.add(url);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error adding links! "+e.getMessage());
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
            RequestConfig config= RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setSocketTimeout(3000).build();
            HttpClient client= HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet request = new HttpGet(URL);
            response = client.execute(request);
        } catch (Exception e) {
            System.out.println(URL + " " + e.getMessage());
            return false;
        }
        return  response.getStatusLine().getStatusCode()==200;
    }

}
