package demoblaze;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

public class demoblazeabhinav {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            driver.get("https://www.demoblaze.com/");

            // Click category menu
            wait.until(ExpectedConditions.elementToBeClickable(By.id("itemc"))).click();

            // Select product
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Sony vaio i5']"))).click();
            takeScreenshot(driver, "D:\\select_product.jpg");

            // Add to cart
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-success btn-lg']"))).click();
            takeScreenshot(driver, "D:\\add_to_cart.jpg");

            // Handle alert
            wait.until(ExpectedConditions.alertIsPresent()).accept();

            // Navigate to cart
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

            // Verify product
            boolean isProductDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[normalize-space()='Sony vaio i5']"))).isDisplayed();
            boolean isPriceDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='790']"))).isDisplayed();

            System.out.println(isProductDisplayed && isPriceDisplayed ? "Product correctly added to the cart!" : " Product verification failed.");
            takeScreenshot(driver, "D:\\cart_page.jpg");

            // Place order
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Place Order']"))).click();

            // Ensure visibility and scroll into view before interaction
            fillOrderForm(driver, wait, "Abhinav Mathur", "India", "Hyderabad", "12345678", "June", "2025");

            // Submit order
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Purchase']"))).click();
            WebElement confirmationMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Thank you for your purchase!')]")));

            if (confirmationMessage.isDisplayed()) {
                System.out.println("Purchase confirmation displayed successfully!");
            } else {
                System.out.println("Purchase confirmation validation failed.");
            }
takeScreenshot(driver, "D:\\confirmation_message.jpg");

        } catch (Exception e) {
            System.out.println("Error encountered: " + e.getMessage());
        } 
    }

    private static void takeScreenshot(WebDriver driver, String filePath) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(srcFile, new File(filePath));
    }

    private static void fillOrderForm(WebDriver driver, WebDriverWait wait, String name, String country, String city, String card, String month, String year) {
        scrollToElement(driver, By.id("name"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(name);
        driver.findElement(By.id("country")).sendKeys(country);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("card")).sendKeys(card);
        driver.findElement(By.id("month")).sendKeys(month);
        driver.findElement(By.id("year")).sendKeys(year);
    }

    private static void scrollToElement(WebDriver driver, By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}