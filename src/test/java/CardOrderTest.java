import com.codeborne.selenide.SelectorMode;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldTestFormWithCorrectData() { // проверка корректной работы формы при верных данных

        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79185463782");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldTestFormWithEmptyNameField() { // проверка работы формы при пустом поле ввода имени

        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79185463782");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();

        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения")); // проверка текста ошибки
        boolean b = $("[data-test-id=name].input_invalid").isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
    }

    @Test
    public void shouldTestFormWithIncorrectNameField() { // проверка работы формы при некорректных значениях поля ввода имени

        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("2212");
        form.$("[data-test-id=phone] input").setValue("+79185463782");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();

        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")); // проверка текста ошибки
        boolean b = $("[data-test-id=name].input_invalid").isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
    }

    @Test
    public void shouldTestFormWithEmptyPhoneField() { // проверка работы формы при пустом поле ввода телефона

        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();

        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения")); // проверка текста ошибки
        boolean b = $("[data-test-id=phone].input_invalid").isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
    }

    @Test
    public void shouldTestFormWithIncorrectPhoneField() { // проверка работы формы при некорректных значениях поля ввода телефона

        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("Петрович");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();

        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")); // проверка текста ошибки
        boolean b = $("[data-test-id=phone].input_invalid").isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
    }

    @Test
    public void shouldTestFormWithoutClickingOnCheckbox() { // проверка работы формы, если не нажат чекбокс

        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79185463782");
        form.$("button").click();

        boolean b = $("[data-test-id=agreement].input_invalid").isDisplayed(); // проверка наличия класса input_invalid
        Assertions.assertTrue(b);
    }
}
