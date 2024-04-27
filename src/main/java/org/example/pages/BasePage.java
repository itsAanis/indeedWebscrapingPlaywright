package org.example.pages;
import com.microsoft.playwright.Page;
public abstract class BasePage {

    protected Page page;

    public void setAndConfigurePage(final Page page) {
        this.page = page;

    }
}
