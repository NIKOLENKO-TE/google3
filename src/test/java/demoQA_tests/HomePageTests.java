package demoQA_tests;

import core.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

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
  @Test
  public void searchTest() {
    basePage.type(homePage.searchBar, "Котики"); // Вводим текст в строку поиска
    basePage.pressEnter(); // Нажимаем Enter для запуска поиска
    assertNotNull(homePage.searchResultField, "Поле 'Результаты поиска' не найдены"); // Проверяем, что поле найдено
    assertNotNull(homePage.searchResultText, "Текст 'Результаты поиска' не найден"); // Проверяем, что текст найден
    basePage.shouldHaveText(homePage.searchResultText, "Результаты поиска",5000); // Проверяем, что текст в поиске соответствует ожидаемому
    printSearchResult(); // Выводим результаты поиска
  }
}

