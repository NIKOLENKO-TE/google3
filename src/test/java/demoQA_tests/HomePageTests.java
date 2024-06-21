package demoQA_tests;

import core.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static core.AppManager.*;
import static google_page.HomePage.HOME_PAGE_URL;
import static google_page.HomePage.printSearchResult;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Test Class to Test the Home Page GOOGLE.COM")
public class HomePageTests extends BaseTest {

  @BeforeEach
  public void precondition() {
    basePage
        .open(HOME_PAGE_URL)
        .click(homePage.acceptAllButton);
  }
  @Disabled
  @Feature("Find {kittens} on {Google.com}") // * Заголовок в отчете Allure
  @Story("Test Case #01")// * Подзаголовок в отчете Allure
  @DisplayName("Find {kittens} on {Google.com}") // * Название теста
  //@Test
  @RepeatedTest(value = 1, name = "{displayName} :: итерация [{currentRepetition}] из [{totalRepetitions}]")
  public void searchTest() {
    basePage.type(homePage.searchBar, "kittens");
    basePage.pressEnter();
    assertNotNull(homePage.searchResultField, "The 'Search results' field was not found");
    assertNotNull(homePage.searchResultText, "Text 'Search Results' Not Found");
    basePage.shouldHaveText(homePage.searchResultText, "Search results",5000);
    printSearchResult();
    System.out.println("Test Complete");
  }
}

