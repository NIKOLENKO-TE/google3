package core;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class BasePage {
  protected static WebDriver driver;
  JavascriptExecutor js;
  public static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

  public BasePage() {
    driver = AppManager.driver;
    js = (JavascriptExecutor) driver;
    PageFactory.initElements(driver, this);
  }

  @Step("Открываем ссылку: [{url}]")
  public BasePage open(String url) {
    driver.get(url);
    return this;
  }

  @Step("Получаем скриншот ошибки и прикрепляем к отчету Allure")
  @Attachment(value = "Screenshot", type = "image/png")
  public static byte[] takeScreenshot() {
    if (driver instanceof TakesScreenshot) {
      TakesScreenshot ts = (TakesScreenshot) driver;
      File scrFile = ts.getScreenshotAs(OutputType.FILE);
      Path destination = Paths.get("./screenshots", scrFile.getName());
      try {
        Files.createDirectories(destination.getParent());
        FileHandler.copy(scrFile, destination.toFile());
        return Files.readAllBytes(destination);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return new byte[0];
  }

  @Step("Кликаем по элементу: [{element}]")
  public void click(WebElement element) {
    element.click();
  }

  @Step("Кликаем по элементу с JS: [{element}]")
  protected void clickJS(WebElement element, int x, int y) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2)); // ожидание в течение 10 секунд
    wait.until(ExpectedConditions.elementToBeClickable(element)); // ожидание, пока элемент не станет доступным для клика
    js.executeScript("window.scrollBy(" + x + "," + y + ")");
    click(element);
  }

  @Step("Вводим текст: [{text}]")
  public void type(WebElement element, String text) {
    if (text != null) {
      click(element);
      element.clear();
      element.sendKeys(text);
    }
  }

  // Метод, который будет искать текст в локаторе
  public void shouldHaveText(WebElement element, String text, int timeout) {
    LOGGER.info("ТЕКСТ ПРОВЕРЕН: [{}] В ЭЛЕМЕНТЕ: [{}]", text, element.getTagName());
    try {
      new WebDriverWait(driver, Duration.ofMillis(timeout)).until(ExpectedConditions.textToBePresentInElement(element, text));
    } catch (TimeoutException e) {
      LOGGER.error("Текст [{}] не был найден в элементе [{}] в течение [{}] миллисекунд", text, element.getTagName(), timeout);
      throw new AssertionError("Текст [" + text + "] не был найден в элементе [" + element.getTagName() + "] в течение [" + timeout + "] миллисекунд");
    }
  }

  public void pressEnter() {
    new Actions(driver).sendKeys(Keys.ENTER).perform();
  }
}
