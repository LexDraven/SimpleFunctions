package Net;

import Simple.URLChecker;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class NetSpiderConcurrent {
    private ConcurrentLinkedDeque<String> linksInDomain;
    private ConcurrentLinkedDeque<String> allLinks;
    private ConcurrentHashSet<String> brokenLinks;
    private ConcurrentHashSet<String> checkedLinks;
    private HtmlUnitDriver browser;
    private String mainDomain;

    public NetSpiderConcurrent(String mainDomen) {
        this.mainDomain = mainDomen;
        linksInDomain = new ConcurrentLinkedDeque<>();
        brokenLinks = new ConcurrentHashSet<>();
        checkedLinks = new ConcurrentHashSet<>();
        allLinks = new ConcurrentLinkedDeque<>();
    }

    public void setNewDomain(String newDomain) {
        mainDomain = newDomain;
        linksInDomain.clear();
        brokenLinks.clear();
        checkedLinks.clear();
    }

    public void startThreads(int number){
        Thread[] pool = new Thread[number];
        for (int i=0; i<number; i++){
            pool[i] = new Thread(new CheckRunner(this));
            pool[i].setDaemon(true);
        }
        for (Thread tred:pool){
            tred.start();
        }
    }

    public void checkLinksInDomain() {
        linksInDomain.add(mainDomain);
        checkedLinks.add(mainDomain);
        browser = new HtmlUnitDriver(false);
        long begin = System.currentTimeMillis();
        startThreads(4);
        while ((!linksInDomain.isEmpty()) | (!allLinks.isEmpty())) {
            if (!linksInDomain.isEmpty()) {
                String link = linksInDomain.poll();
                browser.get(link);
                System.out.println("Go to page " + link);
                getAllLinks();
            }
        }
        CheckRunner.setNumber(0);
        System.out.println("Checked links: " + checkedLinks.size());
        System.out.println("Broken links: " + brokenLinks.size());
        if (brokenLinks.size() > 0) {
            System.out.println("Broken links are:");
            for (String pair : brokenLinks) {
                System.out.println(pair);
            }
        }
        long time = (System.currentTimeMillis() - begin) / 1000;
        System.out.println("Total time spent: " + time + " sec");
        browser.quit();
    }

    private void getAllLinks() {
        List<WebElement> linksOnPage = browser.findElements(By.tagName("a"));
        if (linksOnPage.size() > 0) {
            System.out.println("Links on page - " + linksOnPage.size());
            for (WebElement element : linksOnPage) {
                if (element != null) {
                    String url = element.getAttribute("href");
                    if (url != null && !url.contains("javascript") && !url.startsWith("mailto")) {
                            allLinks.add(url);
                    }
                }
            }
        }
    }

    public ConcurrentLinkedDeque<String> getLinksInDomain() {
        return linksInDomain;
    }

    public ConcurrentLinkedDeque<String> getAllLinksQueue() {
        return allLinks;
    }

    public ConcurrentHashSet<String> getBrokenLinks() {
        return brokenLinks;
    }

    public HtmlUnitDriver getBrowser() {
        return browser;
    }

    public String getMainDomain() {
        return mainDomain;
    }

    public ConcurrentHashSet<String> getCheckedLinks() {
        return checkedLinks;
    }
}

class CheckRunner implements Runnable {
    private NetSpiderConcurrent spider;
    private static volatile int number = 1;
    private URLChecker checker;

    CheckRunner(NetSpiderConcurrent spiderConcurrent) {
        this.spider = spiderConcurrent;
        number++;
        checker = new URLChecker();
    }

    public static void setNumber(int number) {
        CheckRunner.number = number;
    }

    @Override
    public void run() {
        while (number > 0) {
            sortByGroups();
        }
    }

    private void sortByGroups() {
        if (!spider.getAllLinksQueue().isEmpty()) {
            String url = spider.getAllLinksQueue().poll();
            if (!spider.getCheckedLinks().contains(url)) {
                spider.getCheckedLinks().add(url);
                try {
                    if (!(checkLinks(url))) {
                        spider.getBrokenLinks().add(url);
                    } else {
                        if ((url.startsWith(spider.getMainDomain()) & (!checker.isFile(url)))) {
                            spider.getLinksInDomain().add(url);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error adding links! " + e.getMessage());
                }
            }
        }
    }

    private boolean checkLinks(String URL) {
        return checker.isLinkAlive(URL);
    }
}
