import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardOrderTest {
    private WebDriver driver;

  /*  @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }*/

    @BeforeEach
     void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void ShouldTestFormWithCorrectData() { // проверка корректной работы формы при верных данных

        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.id("root"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79185463782");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void ShouldTestFormWithEmptyNameField() { // проверка работы формы при пустом поле ввода имени

        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.id("root"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79185463782");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        String fieldText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText(); // текст ошибка
        String fieldColor = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getCssValue("color"); // цвет текста ошибки
        Assertions.assertEquals("Поле обязательно для заполнения", fieldText.trim());
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim());
    }

    @Test
    public void ShouldTestFormWithIncorrectNameField() { // проверка работы формы при некорректных значениях поля ввода имени

        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.id("root"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("22212");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79185463782");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        String fieldText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        String fieldColor = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getCssValue("color");
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", fieldText.trim());
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim());
    }

    @Test
    public void ShouldTestFormWithEmptyPhoneField() { // проверка работы формы при пустом поле ввода телефона

        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.id("root"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        String fieldText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        String fieldColor = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getCssValue("color");
        Assertions.assertEquals("Поле обязательно для заполнения", fieldText.trim());
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim());
    }

    @Test
    public void ShouldTestFormWithIncorrectPhoneField() { // проверка работы формы при некорректных значениях поля ввода телефона

        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.id("root"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Петрович");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        String fieldText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        String fieldColor = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getCssValue("color");
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", fieldText.trim());
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim());
    }

    @Test
    public void ShouldTestFormWithoutClickingOnCheckbox() { // проверка работы формы, если не нажат чекбокс

        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.id("root"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79185463782");
        form.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getCssValue("color");
        Assertions.assertEquals("rgba(255, 92, 92, 1)", text.trim());
    }
}
