package com.gammax.ci.gammax.pages;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.gammax.ci.gammax.core.Element;
import com.gammax.ci.gammax.testbase.Base;
import com.gammax.ci.gammax.testbase.JSWaiter;
import com.relevantcodes.extentreports.LogStatus;

public class MetaMaskNotificationPage extends Base{
	
	WebDriver driver;
	Element element;

    public void MetaMaskNotificationPage(WebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 45);
        PageFactory.initElements(factory, this);
        this.element = new Element(driver);
        JSWaiter.setDriver(driver);
    }

    @FindBy(xpath = "//button[text()='Next']")
    WebElement next;

    @FindBy(xpath = "//button[text()='Connect']")
    WebElement Connect;

    @FindBy(xpath = "//button[text()='Sign']")
    WebElement Sign;
    
    @FindBy(xpath = "//button[text()='Confirm']")
    WebElement confirm;
    
    public void clickNext() {
    	element.click(driver, next);
        extentTest.log(LogStatus.INFO, "Click on NEXT");
        takeScreenShot();
    }

    public void clickConnect() {
        Connect.click();
        extentTest.log(LogStatus.INFO, "Click On Connect");
        takeScreenShot();
    }
    
    public void clickSign() {
    	element.waitClickable(driver, Sign, 10);
    	element.click(driver, Sign);
        extentTest.log(LogStatus.INFO, "Click on SignIN");
    }
    
    public void clickConfirm() {
    	element.click(driver, confirm);
    	extentTest.log(LogStatus.INFO, "Click on Confirm");
    }
    
    public void switchToGammaX(){
		Set<String> windows = driver.getWindowHandles();
		for(String window:windows){
			driver.switchTo().window(window);
			if(driver.getTitle().contains("Gammax") || driver.getTitle().contains("GammaX QA")){
				extentTest.log(LogStatus.INFO, "Switched to "+driver.getTitle());
		        takeScreenShot();
				break;
			}
		}
	}

}
