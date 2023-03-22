package com.gammax.ci.gammax.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class Calculator extends Base {

	WebDriver driver;
	Element element = new Element(driver);
	BrowserDriver browserDriver = new BrowserDriver();
	Base base = new Base();
	JSWaiter jSWaiter = new JSWaiter();
	static Logger logger = LogManager.getLogger(Homepage.class.getName());

	@FindBy(id = "Quantity")
	WebElement quantity;
	
	@FindBy(id = "EntryPrice")
	WebElement EntryPrice;
	
	@FindBy(id = "ExitPrice")
	WebElement cross;
	
	@FindBy(id = "Leverage")
	WebElement Leverage;
	
	@FindBy(partialLinkText = "Profit/Loss")
	WebElement profitOrLoss;
	
	@FindBy(partialLinkText = "Target Price")
	WebElement targetPrice;
	
	@FindBy(partialLinkText = "Liquidation Price")
	WebElement liquidationPrice;
	
	@FindBy(xpath = "//label[@for='checkboxSide']/input")
	WebElement longOrshort;
	
	@FindBy(css = "[class='close-icon']")
	WebElement x;

	public void HomePageDriverRef(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 45);
		PageFactory.initElements(factory, this);
		element = new Element(driver);
		JSWaiter.setDriver(driver);
	}
	
	public void enterQuantity(String qty) {
		quantity.sendKeys(qty);
		extentTest.log(LogStatus.INFO, "Enter Quantity :: "+qty);
		takeScreenShot();
	}
	
	public void enterEntryPrice(String price) {
		EntryPrice.sendKeys(price);
		extentTest.log(LogStatus.INFO, "Enter Price :: "+price);
		takeScreenShot();
	}
	
	public void enterCross(String price) {
		cross.sendKeys(price);
		extentTest.log(LogStatus.INFO, "Enter Cross/Exit Price :: "+price);
		takeScreenShot();
	}
	
	public void enterLeverage(String leverage) {
		Leverage.sendKeys(leverage);
		extentTest.log(LogStatus.INFO, "Enter Leverage :: "+leverage);
		takeScreenShot();
	}
	
	public void isProfitorLossDisplayed() {
		profitOrLoss.isDisplayed();
		extentTest.log(LogStatus.INFO, "Profit/Loss is Dsplayed :: "+profitOrLoss.isDisplayed());
		takeScreenShot();
	}
	
	public void istargetPriceDisplayed() {
		targetPrice.isDisplayed();
		extentTest.log(LogStatus.INFO, "Target Price is Dsplayed :: "+targetPrice.isDisplayed());
		takeScreenShot();
	}
	
	public void isliquidationPriceDisplayed() {
		liquidationPrice.isDisplayed();
		extentTest.log(LogStatus.INFO, "Liquidation Price is Dsplayed :: "+liquidationPrice.isDisplayed());
		takeScreenShot();
	}
	
	public void clickX() {
		x.click();
		extentTest.log(LogStatus.INFO, "Click on X in Calculator");
		takeScreenShot();
	}

}
