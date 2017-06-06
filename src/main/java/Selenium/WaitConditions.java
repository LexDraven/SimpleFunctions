package Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.function.Function;

public enum WaitConditions {
    visible(ExpectedConditions::visibilityOfElementLocated),
    exist(ExpectedConditions::presenceOfElementLocated),
    clickable(ExpectedConditions::elementToBeClickable),
    invisible(WaitConditions::invisibleElement);

    WaitConditions(Function<By, ExpectedCondition<WebElement>> type) {
        this.type = type;
    }

    public Function<By, ExpectedCondition<WebElement>> getType() {
        return type;
    }

    private final Function<By, ExpectedCondition<WebElement>> type;

    private static ExpectedCondition<WebElement> invisibleElement(By locator) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                if (driver.findElements(locator).size() > 0) {
                    return null;
                } else {
                    return driver.findElement(By.cssSelector("html"));
                }
            }
        };
    }

}
