package com.gammax.ci.gammax.pages;

import com.gammax.ci.gammax.core.Element;
import com.gammax.ci.gammax.testbase.Base;
import com.gammax.ci.gammax.testbase.JSWaiter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;


public class Loginpage {

	SoftAssert softAssert = new SoftAssert();	
	WebDriver driver;
	Element element = new Element(driver);
	JSWaiter jSWaiter = new JSWaiter();
	Base base = new Base();
	
	static Logger logger = LogManager.getLogger(Homepage.class.getName());

	@FindBy(id = "stateuser")
	WebElement userName;
	
	@FindBy(id = "Password")
	WebElement password;
	
	@FindBy(xpath = "//input[@value='Login']")
	WebElement login;
	
	@FindBy(xpath = "//a[text()[contains(.,'Forgot password')]]")
	WebElement forgotpasswordlink;
	
	@FindBy(xpath = "//h2[text()[contains(.,'Reset password')]]")
	WebElement resetpasswordheader;
	
	@FindBy(xpath ="//a[text()[contains(., 'Return to Sign In')]]")
	WebElement returntosignin;


	public void LoginPageDriverRef(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 45);
		PageFactory.initElements(factory, this);

	}



	public String pageTitle() throws InterruptedException {
		String pageTitle =  driver.getTitle();
		System.out.println("Page title is :: "+ driver.getTitle());
		logger.info("Page title is :: <b>"+ driver.getTitle()+"</b>");
		Base.extentTest.log(LogStatus.PASS,"Page title is :: <b>"+ driver.getTitle()+"</b>");
		Base.takeScreenShot();
		return pageTitle;
	}

}
