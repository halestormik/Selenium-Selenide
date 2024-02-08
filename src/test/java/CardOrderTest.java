import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
     void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldTestFormWithCorrectData() { // проверка корректной работы формы при верных данных

        WebElement form = driver.findElement(By.className("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79185463782");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldTestFormWithEmptyNameField() { // проверка работы формы при пустом поле ввода имени

        WebElement form = driver.findElement(By.className("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79185463782");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        boolean b =form.findElement(By.cssSelector("[data-test-id=name].input_invalid")).isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
        String fieldText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText(); // текст ошибки
        Assertions.assertEquals("Поле обязательно для заполнения", fieldText.trim());
    }

    @Test
    public void shouldTestFormWithIncorrectNameField() { // проверка работы формы при некорректных значениях поля ввода имени

        WebElement form = driver.findElement(By.className("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("22212");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79185463782");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();


        boolean b =form.findElement(By.cssSelector("[data-test-id=name].input_invalid")).isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
        String fieldText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", fieldText.trim());
    }

    @Test
    public void shouldTestFormWithEmptyPhoneField() { // проверка работы формы при пустом поле ввода телефона

        WebElement form = driver.findElement(By.className("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        boolean b =form.findElement(By.cssSelector("[data-test-id=phone].input_invalid")).isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
        String fieldText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", fieldText.trim());
    }

    @Test
    public void shouldTestFormWithIncorrectPhoneField() { // проверка работы формы при некорректных значениях поля ввода телефона

        WebElement form = driver.findElement(By.className("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Петрович");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();

        boolean b =form.findElement(By.cssSelector("[data-test-id=phone].input_invalid")).isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
        String fieldText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", fieldText.trim());
    }

    @Test
    public void shouldTestFormWithoutClickingOnCheckbox() { // проверка работы формы, если не нажат чекбокс

        WebElement form = driver.findElement(By.className("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79185463782");
        form.findElement(By.className("button")).click();

        boolean b =form.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
    }
}
