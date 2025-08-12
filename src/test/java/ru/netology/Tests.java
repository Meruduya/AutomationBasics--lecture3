package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void shouldSubmitRequest() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79272000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                result.getText().trim());
    }

    @Test
    void shouldSubmitErrorWhenNameIsEmpty() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79272000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void shouldSubmitErrorWhenPhoneIsEmpty() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());

    }

    @Test
    void shouldSubmitErrorWhenNotChecked() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79272000000");
        driver.findElement(By.tagName("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text"));
        assertTrue(result.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",
                result.getText().trim());

    }

    @Test
    void shouldSubmitErrorWhenNameIsFalse() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79272000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                result.getText().trim());

    }
    @Test
    void shouldSubmitErrorWhenPhoneIsFalse() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("9180000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                result.getText().trim());

    }
}


