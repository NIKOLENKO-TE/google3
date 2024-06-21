package core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static core.BasePage.takeScreenshot;
import static core.BaseTest.app;

/*
В Java, вы можете иметь несколько классов в одном .java-файле.
@ExtendWith Указывает что нужно "расширить" текущий тестовый класс с помощью указанного класса расширения.
Основной класс (в данном случае BaseTest) должен иметь то же имя, что и файл, и только он может быть public.
Все остальные классы в файле должны быть не public.
*/
@ExtendWith(BaseTestLogger.class)
public class BaseTest {
  public static AppManager app = new AppManager(System.getProperty("browser", "chrome"));
  public static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

  @BeforeEach
  void testStarted(TestInfo testInfo) {
    LOGGER.info("========================= {} ============================", testInfo.getTestMethod().orElseThrow().getName());
    LOGGER.info("START TEST: {}", testInfo.getDisplayName());
    app.init();
    BaseTestLogger.startTime = System.currentTimeMillis();
  }

  @AfterAll
  static void afterAll() {
    app.stop();
    LOGGER.info("**************************** ВСЕ ТЕСТЫ ЗАВЕРШЕНЫ ****************************");
  }
}

// ! BaseTestLogger имплементируют TestWatcher.
// ! Он позволяет перехватывать события результата выполнения тестов и выполнять действия в зависимости от результата.
class BaseTestLogger implements TestWatcher {
  public static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
  public static long startTime; // * Статическое поле для хранения времени начала теста

  @Override // * Переопределяем метод testSuccessful класса TestWatcher, который вызывается после успешного выполнения теста
  public void testSuccessful(ExtensionContext context) {
    long endTime = (System.currentTimeMillis() - startTime); // * Вычисляем время выполнения теста
    app.stop();
    LOGGER.info("PASSED TEST: [{}], TIME for test execution: [{}] ms", context.getRequiredTestMethod().getName(), endTime);
  }

  @Override // * Переопределяем метод testFailed класса TestWatcher, который вызывается после неудачного выполнения теста
  public void testFailed(ExtensionContext context, Throwable cause) {
    takeScreenshot();
    long endTime = (System.currentTimeMillis() - startTime);
    app.stop();
    LOGGER.error("FAILED TEST: [{}], TIME for test execution: [{}] ms", context.getRequiredTestMethod().getName(), endTime);
  }

  @Override // * Переопределяем метод testDisabled класса TestWatcher, который вызывается после отключения теста
  public void testDisabled(ExtensionContext context, Optional<String> reason) {
    takeScreenshot();
    long endTime = (System.currentTimeMillis() - startTime);
    app.stop();
    LOGGER.warn("DISABLED TEST: [{}], TIME for test execution: [{}] ms", context.getRequiredTestMethod().getName(), endTime);
  }

  @Override // * Переопределяем метод testAborted класса TestWatcher, который вызывается после прерывания теста
  public void testAborted(ExtensionContext context, Throwable cause) {
    takeScreenshot();
    long endTime = (System.currentTimeMillis() - startTime);
    app.stop();
    LOGGER.warn("ABORTED TEST: [{}], TIME for test execution: [{}] ms", context.getRequiredTestMethod().getName(), endTime);
  }
}
