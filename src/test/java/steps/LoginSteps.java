package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.LoginPage;
import utilities.Config;
import utilities.Driver;

public class LoginSteps {
    LoginPage loginpage = new LoginPage();

    @Given("the user is on the url")
    public void the_user_is_on_the_url() {
        Driver.getDriver().get(Config.getProperties("saucedemourl"));
    }
    @When("user enters a correct username")
    public void user_enters_a_correct_username() {
        loginpage.username.sendKeys(Config.getProperties("username"));
    }
    @Then("user enters a correct password")
    public void user_enters_a_correct_password() {
       loginpage.password.sendKeys(Config.getProperties("password"));
    }
    @Then("user clicks on login button")
    public void user_clicks_on_login_button() {
        loginpage.loginButton.click();
    }
    @Then("verify the user is logged in")
    public void verify_the_user_is_logged_in() {
        Assert.assertTrue(loginpage.products.isDisplayed());
    }


}
