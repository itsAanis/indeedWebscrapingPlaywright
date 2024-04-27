package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import org.example.pages.IndeedPage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PageController {
    private final Browser browser;
    private final int numberOfPages;
    private ConcurrencyHelper concurrencyHelper;
            public PageController(Browser browser, int numberOfPages) {
                this.browser = browser;
                this.numberOfPages = numberOfPages;
                this.concurrencyHelper = new ConcurrencyHelper(numberOfPages);
            }

    public void executeScrapingTasks() {
        for (int i = 0; i < numberOfPages; i++) {
            concurrencyHelper.submitTask(() -> {
                try (Page page = browser.newPage()) {
                    IndeedPage indeedPage = new IndeedPage(page);
                    indeedPage.runExtraction();
                }
            });
        }
        concurrencyHelper.shutdown();
    }

}
