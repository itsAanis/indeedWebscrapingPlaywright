package org.example;

import com.microsoft.playwright.*;
import org.example.pages.IndeedPage;

public class Main {
    public static void main(String[] args) {

       try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
           Page page = browser.newPage();
           int numberOfPages = 1;
          IndeedPage indeedPage = new IndeedPage(page);
           indeedPage.runExtraction();
        }
    }
}