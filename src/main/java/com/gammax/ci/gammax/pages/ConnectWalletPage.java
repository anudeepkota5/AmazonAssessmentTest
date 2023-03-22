package com.gammax.ci.gammax.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.asserts.SoftAssert;

import com.gammax.ci.gammax.core.Element;
import com.gammax.ci.gammax.testbase.Base;
import com.gammax.ci.gammax.testbase.JSWaiter;

public class ConnectWalletPage {

	SoftAssert softAssert = new SoftAssert();	
	WebDriver driver;
	Element element = new Element(driver);
	JSWaiter jSWaiter = new JSWaiter();
	Base base = new Base();

    public void MetaMaskNotificationPage(WebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 45);
        PageFactory.initElements(factory, this);
        Element element = new Element(driver);
        JSWaiter.setDriver(driver);
    }
    
    @FindBy(xpath = "//div[text()=' Get started ']")
    private WebElement connectWallet;

    @FindBy(xpath = "//div[text()='MetaMask']")
    private WebElement metamask;

    public void clickGetStarted(){
        connectWallet.click();
    }

    public void clickMetaMask(){
        metamask.click();
    }

}
