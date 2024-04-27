package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;



public class IndeedPage {
    private final Page page;

    public IndeedPage(Page page) {
        this.page = page;
    }

    public void execute() {
        page.navigate("https://uk.indeed.com");
        page.getByPlaceholder("Job title").fill("Software Engineer");
        page.getByPlaceholder("City or postcode").fill("");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Find jobs"))
                .click();
    }

    public void extractData (int index) {

            String title = null, pay = null, location = null, description = "";
            int shortTimeout = 1000;
            Locator jobCard = page.locator("ul.css-zu9cdh > li.css-5lfssm").nth(index);
            Locator link = jobCard.locator("a");

            if (link.count() > 0) {
            link.first().click();
            }
            else {return;}


            Locator titleLocator = page.getByTestId("jobsearch-JobInfoHeader-title");
           try {
               titleLocator.waitFor(new Locator.WaitForOptions().setTimeout(shortTimeout));
                   title = titleLocator.innerText();
           }
           catch (PlaywrightException e) {
               title = "not available";
            }

            Locator payLocator = page.getByTestId("obsearch-OtherJobDetailsContainer");
           try {
               payLocator.waitFor(new Locator.WaitForOptions().setTimeout(shortTimeout));
                   pay = payLocator.innerText();
           }catch (PlaywrightException e) {
               pay = "not available";
           }

            try {
                Locator locationLocator = page.getByTestId("jobsearch-JobInfoHeader-companyLocation");
                 locationLocator.waitFor(new Locator.WaitForOptions().setTimeout(shortTimeout));
                 location = locationLocator.innerText();

            } catch (PlaywrightException e) {
                location = "Location not available";
            }

            Locator paragraphs = page.locator("#jobDescriptionText");
            try {
                paragraphs.waitFor(new Locator.WaitForOptions().setTimeout(shortTimeout));
                int paragraphCount = paragraphs.count();
                if (paragraphCount > 0) {
                    description = paragraphs.nth(0).innerText();
                    // If there's a second paragraph, append it
                    if (paragraphs.count() > 1) {
                        description += "\n" + paragraphs.nth(1).innerText();
                    }
                }
            } catch (PlaywrightException e) {

            }


            location = (location != null) ? location : "Location not available";
            System.out.println("Title: " + title);
            System.out.println("Location: " + location);
            System.out.println("Pay: " + pay);
            System.out.println("Description: " + description);

        };


    public void runExtraction () {
       execute();
       page.waitForSelector("ul.css-zu9cdh > li.css-5lfssm");
        Locator jobCards = page.locator("ul.css-zu9cdh > li.css-5lfssm");
        int count = jobCards.count();

        for (int i = 0; i < count; i++) {
            Locator jobCard = jobCards.nth(i);
            extractData(i);
        }
    }

}
