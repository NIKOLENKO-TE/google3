package demoQA_tests;

import core.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static core.AppManager.*;
import static google_page.HomePage.HOME_PAGE_URL;
import static google_page.HomePage.printSearchResult;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тестовый класс для проверки домашней страницы GOOGLE.COM")
public class HomePageTests extends BaseTest {

  @BeforeEach
  public void precondition() {
    basePage
        .open(HOME_PAGE_URL)
        .click(homePage.acceptAllButton);
  }

  @Feature("Find {Котики} on {Google.com}") // * Заголовок в отчете Allure
  @Story("Test Case #01")// * Подзаголовок в отчете Allure
  @DisplayName("Find {Котики} on {Google.com}") // * Название теста
  //@Test
  @RepeatedTest(value = 1, name = "{displayName} :: итерация [{currentRepetition}] из [{totalRepetitions}]")
  public void searchTest() {
    basePage.type(homePage.searchBar, "Котики");
    basePage.pressEnter();
    assertNotNull(homePage.searchResultField, "Поле 'Результаты поиска' не найдены");
    assertNotNull(homePage.searchResultText, "Текст 'Результаты поиска' не найден");
    basePage.shouldHaveText(homePage.searchResultText, "Результаты поиска",5000);
    printSearchResult();
  }
}

