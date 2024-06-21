package core;

import google_page.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class AppManager extends BasePage {

  public static WebDriver driver;
  private static final Duration WAIT_MILLIS_WAIT = Duration.ofMillis(500);
  private final String browser;
  public static BasePage basePage;
  public static HomePage homePage;
  public AppManager(String browser) {
    this.browser = browser;
  }


  public void init() {
    if (browser.equalsIgnoreCase("chrome")) {
      driver = new ChromeDriver();
    } else if (browser.equalsIgnoreCase("firefox")) {
      driver = new FirefoxDriver();
    } else if (browser.equalsIgnoreCase("edge")) {
      System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");
      driver = new EdgeDriver();
    } else {
      throw new IllegalArgumentException("Unsupported browser: " + browser);
    }
    basePage = new BasePage();
    homePage = new HomePage();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(WAIT_MILLIS_WAIT);
  }

  public void stop() {
    driver.quit();
  }
}
