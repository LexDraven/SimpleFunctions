package Net;

import Simple.URLChecker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.*;

public class NetSpider {
    private LinkedList<String> linksInDomain;
    private HashMap<String, String> brokenLinks;
    private HashSet<String> checkedLinks;
    private HtmlUnitDriver browser;
    private String mainDomain;
    private String link;
    private URLChecker checker;

    public NetSpider(String mainDomain) {
        this.mainDomain = mainDomain;
        linksInDomain = new LinkedList<>();
        brokenLinks = new HashMap<>();
        checkedLinks = new HashSet<>();
        checker = new URLChecker();
    }

    public void setNewDomain(String newDomain) {
        mainDomain = newDomain;
        linksInDomain.clear();
        brokenLinks.clear();
        checkedLinks.clear();
    }

    public void checkLinksInDomain() {
        browser = new HtmlUnitDriver(false);
        linksInDomain.add(mainDomain);
        checkedLinks.add(mainDomain);
        long begin = System.currentTimeMillis();
        while (!linksInDomain.isEmpty()) {
            link = linksInDomain.poll();
            try {
                browser.get(link);
                System.out.println("Go to page " + link);
                getAllLinks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
            linksOnPage.removeAll(checkedLinks);
            for (WebElement element : linksOnPage) {
                if (element != null) {
                    String url = element.getAttribute("href");
                    if (url != null && !url.contains("javascript") && !url.startsWith("mailto")) {
                        if (!checkedLinks.contains(url)) {
                            checkedLinks.add(url);
                            try {
                                if (!(checkLinks(url))) {
                                    brokenLinks.put(url, link);
                                } else {
                                    if ((url.startsWith(mainDomain)) & (!checker.isFile(url))) {
                                        linksInDomain.add(url);
                                    }
                                }
                            } catch (Exception e) {
                                System.err.println("Error adding links! " + e.getMessage());
                            }
                        }

                    }
                }
            }
        }
    }

    private boolean checkLinks(String URL) {
        return checker.isLinkAlive(URL);
    }

}
