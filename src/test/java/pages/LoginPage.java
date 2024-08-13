package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class LoginPage {
    WebDriver driver;
    public LoginPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@placeholder=\"Username\"]")
    public WebElement username;

    @FindBy(xpath = "//input[@placeholder=\"Password\"]")
    public WebElement password;

    @FindBy (xpath = "//input[@name=\"login-button\"]")
    public WebElement loginButton;

    @FindBy(xpath = "//span[text()=\"Products\"]")
    public WebElement products;
}
