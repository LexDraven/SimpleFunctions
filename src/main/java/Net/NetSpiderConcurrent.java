package Net;

import Simple.URLChecker;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class NetSpiderConcurrent {
    private ConcurrentLinkedDeque<String> linksInDomain;
    private ConcurrentLinkedDeque<String> allLinks;
    private ConcurrentHashMap<String, String> brokenLinks;
    private ConcurrentHashSet<String> checkedLinks;
    private HtmlUnitDriver browser;
    private String mainDomain;

    public NetSpiderConcurrent(String mainDomen) {
        this.mainDomain = mainDomen;
        linksInDomain = new ConcurrentLinkedDeque<>();
        brokenLinks = new ConcurrentHashMap<>();
        checkedLinks = new ConcurrentHashSet<>();
        allLinks = new ConcurrentLinkedDeque<>();
    }

    public void setNewDomain(String newDomain) {
        mainDomain = newDomain;
        linksInDomain.clear();
        brokenLinks.clear();
        checkedLinks.clear();
    }

    public void checkLinksInDomain() {
        linksInDomain.add(mainDomain);
        checkedLinks.add(mainDomain);
        browser = new HtmlUnitDriver(false);
        long begin = System.currentTimeMillis();
        CheckRunner runner = new CheckRunner(this);
        CheckRunner runner1 = new CheckRunner(this);
        CheckRunner runner2 = new CheckRunner(this);
        Thread first = new Thread(runner);
        Thread second = new Thread(runner1);
        Thread third = new Thread(runner2);
        first.setDaemon(true);
        second.setDaemon(true);
        third.setDaemon(true);
        first.start();
        second.start();
        third.start();
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
            for (Map.Entry<String, String> pair : brokenLinks.entrySet()) {
                System.out.println(pair.getKey() + " - on page " + pair.getValue());
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
                    if (url != null && !url.contains("javascript")) {
                        if (!url.startsWith("mailto")) {
                            allLinks.add(url);
                        }
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

    public ConcurrentHashMap<String, String> getBrokenLinks() {
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
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sortByGroups() {
        if (!spider.getAllLinksQueue().isEmpty()) {
            String url = spider.getAllLinksQueue().poll();
            if (!spider.getCheckedLinks().contains(url)) {
                spider.getCheckedLinks().add(url);
                try {
                    if (!(checkLinks(url))) {
                        spider.getBrokenLinks().put(url, spider.getBrowser().getCurrentUrl());
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
        return checker.checkLinks(URL);
    }
}
