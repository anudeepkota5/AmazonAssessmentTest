package com.gammax.ci.gammax.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.gammax.ci.gammax.core.BrowserDriver;
import com.gammax.ci.gammax.core.Element;
import com.gammax.ci.gammax.testbase.Base;
import com.gammax.ci.gammax.testbase.JSWaiter;
import com.relevantcodes.extentreports.LogStatus;

public class ConfirmYourOrderPage extends Base{

	WebDriver driver;
	Element element = new Element(driver);
	BrowserDriver browserDriver = new BrowserDriver();
	Base base = new Base();
	JSWaiter jSWaiter = new JSWaiter();
	static Logger logger = LogManager.getLogger(Homepage.class.getName());
	
    @FindBy(xpath = "//button[@class='btn btn-primary btn-block ng-star-inserted']")
    private WebElement buysell;
    
    public void ConfirmYourOrderPageDriverRef(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 45);
		PageFactory.initElements(factory, this);

	}

    public void clickBuy(){
    	element.waitClickable(driver, buysell, 10);
    	element.clickByJs(driver, buysell);
    	extentTest.log(LogStatus.INFO, "Click on BUY");
        takeScreenShot();
    }

    public void clickSell() {
    	element.waitClickable(driver, buysell, 10);
    	element.clickByJs(driver, buysell);
    	extentTest.log(LogStatus.INFO, "Click on SELL");
        takeScreenShot();
    }
}
