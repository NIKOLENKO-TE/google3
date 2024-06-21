package google_page;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static core.AppManager.homePage;

public class HomePage extends BasePage {
  public static final String HOME_PAGE_URL = "https://google.com/";
  /*
    @FindBy(xpath = "//div[contains(text(),'Принять все')]") // таких локаторов 2 на странице
    @FindBy(xpath = "(//div[contains(text(),'Принять все')])[2]") // поэтому ищем такой второй по счёту
    @FindBy(xpath = "//div[text()='Принять все']") // ищем по точному совпадению с текстом
    @FindBy(xpath = "//button[.//div[text()='Принять все']]")
    @FindBy(xpath = "//button[contains(.,'Принять все')]")
  */
  @FindBy(xpath = "//div[text()='Принять все']")
  public WebElement acceptAllButton;

  // @FindBy(name = "q") // [name='q'] CSS
  // @FindBy(css = "[name='q']") /
  @FindBy(xpath = "//*[@name='q']") // //*[@name='q'] xpath
  public WebElement searchBar;

  @FindBy(id = "search")
  public WebElement searchResultField;

  @FindBy(xpath = "//*[@id='search']//*[contains(text(), 'Результаты поиска')]")
  public WebElement searchResultText;


  public List<WebElement> getElements() {
    return driver.findElements(By.xpath("//*[@id='search']//*[contains(text(), 'котики')]"));
  }
  public static void printSearchResult() {
    if (homePage.getElements().isEmpty()) {
      System.out.println("Список элементов пуст");
    } else {
      for (WebElement element : homePage.getElements()) {
        String text = element.getText();
        if (text.isEmpty()) {
          System.out.println("Test failed: element text is empty");
        } else {
          System.out.println(text);
        }
      }
    }
  }
}
