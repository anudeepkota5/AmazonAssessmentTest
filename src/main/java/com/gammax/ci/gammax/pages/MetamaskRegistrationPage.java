package com.gammax.ci.gammax.pages;

import com.gammax.ci.gammax.core.Element;
import com.gammax.ci.gammax.testbase.Base;
import com.gammax.ci.gammax.testbase.JSWaiter;
import com.relevantcodes.extentreports.LogStatus;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class MetamaskRegistrationPage extends Base {

    WebDriver driver;

    Element element;

    public void MetamaskDriverRef(WebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 45);
        PageFactory.initElements(factory, this);
        element = new Element(driver);
        JSWaiter.setDriver(driver);
    }

    @FindBy(xpath = "//*[text()='Import an existing wallet']")
    WebElement importAnExistingWallet;
    
    @FindBy(xpath = "//*[text()='I agree']")
    WebElement iAgree;

    @FindBy(css = "[type='password']")
    List<WebElement> password;

    @FindBy(xpath = "//button[text()='Confirm Secret Recovery Phrase']")
    WebElement confirm;

    @FindBy(css = "[type='password']")
    List<WebElement> passwords;

    @FindBy(xpath = "//button[text()='Import my wallet']")
    WebElement importMyWallet;

    @FindBy(xpath = "//input[@type='checkbox']")
    WebElement checkbox;
    
    @FindBy(xpath = "//button[text()='Got it!']")
    WebElement gotIt;

    @FindBy(xpath = "//button[text()='Next']")
    WebElement next;

    @FindBy(xpath = "//button[text()='Done']")
    WebElement done;
    
    @FindBy(xpath = "//button[@class='home__subheader-link--tooltip-content-header-button']")
    WebElement x;

    @FindBy(xpath = "//div[@data-testid='network-display']")
    WebElement network;

    @FindBy(xpath = "//button[text()='Add network']")
    WebElement addNetwork;
    
    @FindBy(xpath = "//div[text()='Advanced']")
    WebElement advanced;

    @FindBy(xpath = "//span[text()='Show test networks']/../../div[2]/div/label/div[1]")
    WebElement showTestNetworks;

    @FindBy(xpath = "//button[text()='Save']")
    WebElement save;

    @FindBy(xpath = "//span[text()='Goerli test network']")
    WebElement GoerliTestNetwork;
    
    By OVER_LAY = By.className("loading-overlay");


	public void waitForOverlaysToDisappear(By overlay) {
		
		List<WebElement> overlays = driver.findElements(overlay);
		System.out.println("OVERLAY SIZE" + overlays.size());
		if (overlays.size() > 0) {
			WebDriverWait wait =  new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfAllElements(overlays));
			System.out.println("OVERLAYS INVISIBLE");
		} else {
			System.out.println("OVERLAY NOT FOUND");
		}
	}
    
    public void clickImportAnExistingWallet() {
	element.waitClickable(driver, importAnExistingWallet, 30);
    	importAnExistingWallet.click();
    	extentTest.log(LogStatus.INFO, "Click on Import an Existing Wallet");
        takeScreenShot();
    }
    
    public void clickIAgree() {
    	extentTest.log(LogStatus.INFO, "Click on I Agree");
        takeScreenShot();
    	iAgree.click();
    }
    
    public void enterScretRecoryPhrase(List<String> passPhrase) {
        for(int i = 0; i< password.size(); i++) {
            password.get(i).clear();
        	password.get(i).sendKeys(passPhrase.get(i));
        	extentTest.log(LogStatus.INFO, "Enter Secret Recovery Phrase :: "+passPhrase.get(i));
            takeScreenShot();
        }
    }
    
    public void clickConfirmSecretPhrase() {
    	confirm.click();
    	extentTest.log(LogStatus.INFO, "Click on Confirm Secret Phrase");
    }
    
    public void enterPassword(String password) {
    	passwords.forEach(e -> e.sendKeys(password));
    	extentTest.log(LogStatus.INFO, "Enter Password :: "+password);
    }
    
    public void clickIUnderstandChkBox() {
    	checkbox.click();
    	extentTest.log(LogStatus.INFO, "Click on I UnderStand");
        takeScreenShot();
    }
    
    public void clickImportMyWallet() {
    	importMyWallet.click();
    	waitForOverlaysToDisappear(OVER_LAY);
    	extentTest.log(LogStatus.INFO, "Click on Import My Wallet");
        takeScreenShot();
    }
    
    public void clickGotIt() {
    	gotIt.click();
    	extentTest.log(LogStatus.INFO, "Click on Got It");
        takeScreenShot();
    }
    
    public void clickNext() {
    	next.click();
    	extentTest.log(LogStatus.INFO, "Click on Next");
        takeScreenShot();
    }
    
    public void clickDone() {
    	done.click();
    	extentTest.log(LogStatus.INFO, "Click on Done");
        takeScreenShot();
    }
    
    public void clickX() {
    	x.click();
    	extentTest.log(LogStatus.INFO, "Click on X");
    	takeScreenShot();
    }
    
    public void clickNetwork() {
    	network.click();
    	extentTest.log(LogStatus.INFO, "Click on Network");
        takeScreenShot();
    }
    
    public void clickAdvanced() {
    	advanced.click();
    	extentTest.log(LogStatus.INFO, "Click on Advanced");
        takeScreenShot();
    }
    
    public void clickShowTestNetworks() {
    	showTestNetworks.click();
    	extentTest.log(LogStatus.INFO, "Click on Show Test Networks");
        takeScreenShot();
    }
    
    public void clickSave() {
    	save.click();
    	extentTest.log(LogStatus.INFO, "Click on Save");
        takeScreenShot();
    }
    
    public void clickGoerliTestNetwork() {
    	GoerliTestNetwork.click();
    	extentTest.log(LogStatus.INFO, "Click on Goerli Test Network");
        takeScreenShot();
    }
    
    public void clickAddNetwork() {
    	addNetwork.click();
    	extentTest.log(LogStatus.INFO, "Click on Add Network");
        takeScreenShot();
    }
    
    public void switchToMetaMask() throws Exception {
    	
		Set<String> windows = driver.getWindowHandles();
		for(String window:windows){
			driver.switchTo().window(window);
			if(driver.getTitle().equals("MetaMask")){
				extentTest.log(LogStatus.INFO, "Switch to Metamask window");
				break;
			}
		}
	}

    public void switchToGammaX() {
    	
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Gammax") || driver.getTitle().contains("GammaX QA")) {
            	extentTest.log(LogStatus.INFO, "Switch to GAMMAX Window");
                takeScreenShot();
                break;
            }
        }
        
    }
}
