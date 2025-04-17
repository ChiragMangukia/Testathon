package io.github.chiragmangukia.testcases;

import io.github.chiragmangukia.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ECartTest extends BaseTest {

    @Test
    public void addToCartAndVerify() {
        driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");

        WebElement txtEmail = driver.findElement(By.xpath("//input[@id='input-email']"));
        WebElement txtPassword = driver.findElement(By.xpath("//input[@id='input-password']"));
        WebElement btnDoLogin = driver.findElement(By.xpath("//input[@value='Login']"));

        txtEmail.sendKeys("chirag@qtlt.com");
        txtPassword.sendKeys("HelloWorld@123");
        btnDoLogin.click();

        WebElement weHomePage = driver.findElement(By.xpath("//span[normalize-space()='Home']"));
        weHomePage.click();

        WebElement weShopByCategory = driver.findElement(By.xpath("//a[normalize-space()='Shop by Category']"));
        weShopByCategory.click();

        WebElement weCategory = driver.findElement(By.xpath("//span[normalize-space()='Laptops & Notebooks']"));
        weCategory.click();

        WebElement weProductFilter = driver.findElement(By.xpath("//*[@id='mz-filter-panel-0-1']/div/div[1]"));
        weProductFilter.click();

        WebElement weProduct = driver.findElement(By.xpath("//*[@id='entry_212408']/div/div[1]"));
        WebElement weProductName = weProduct.findElement(By.xpath(".//div/div[2]/h4"));
        String productName = weProductName.getText();

        Actions actions = new Actions(driver);
        actions.moveToElement(weProduct).perform();

        WebElement weAddToCart = weProduct.findElement(By.xpath(".//div/div[1]/div[2]/button[1]"));
        weAddToCart.click();

        driver.navigate().to("https://ecommerce-playground.lambdatest.io/index.php?route=checkout/cart");

        WebElement weProductInCart = driver.findElement(By.xpath("//*[@id='content']/form/div/table/tbody/tr/td[2]/a"));
        String cartProductName = weProductInCart.getText();

        Assert.assertEquals(cartProductName, productName);
    }
}


