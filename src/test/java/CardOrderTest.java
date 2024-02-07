import com.codeborne.selenide.SelectorMode;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {

    @Test
    public void ShouldTestFormWithCorrectData() { // проверка корректной работы формы при верных данных
        open("http://localhost:9999/");
        SelenideElement form = $("#root");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79185463782");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void ShouldTestFormWithEmptyNameField() { // проверка работы формы при пустом поле ввода имени

        open("http://localhost:9999/");
        SelenideElement form = $("#root");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79185463782");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();

        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения")); // проверка текста ошибки
        String fieldColor = $("[data-test-id=name] .input__sub").getCssValue("color"); // получение цвета текста ошибки
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim()); // сравнение цвета текста ошибки с красным
    }

    @Test
    public void ShouldTestFormWithIncorrectNameField() { // проверка работы формы при некорректных значениях поля ввода имени

        open("http://localhost:9999/");
        SelenideElement form = $("#root");
        form.$("[data-test-id=name] input").setValue("2212");
        form.$("[data-test-id=phone] input").setValue("+79185463782");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();

        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")); // проверка текста ошибки
        String fieldColor = $("[data-test-id=name] .input__sub").getCssValue("color"); // получение цвета текста ошибки
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim()); // сравнение цвета текста ошибки с красным
    }

    @Test
    public void ShouldTestFormWithEmptyPhoneField() { // проверка работы формы при пустом поле ввода телефона

        open("http://localhost:9999/");
        SelenideElement form = $("#root");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();

        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения")); // проверка текста ошибки
        String fieldColor = $("[data-test-id=phone] .input__sub").getCssValue("color"); // получение цвета текста ошибки
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim()); // сравнение цвета текста ошибки с красным
    }

    @Test
    public void ShouldTestFormWithIncorrectPhoneField() { // проверка работы формы при некорректных значениях поля ввода телефона

        open("http://localhost:9999/");
        SelenideElement form = $("#root");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("Петрович");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();

        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")); // проверка текста ошибки
        String fieldColor = $("[data-test-id=phone] .input__sub").getCssValue("color"); // получение цвета текста ошибки
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim()); // сравнение цвета текста ошибки с красным
    }

    @Test
    public void ShouldTestFormWithoutClickingOnCheckbox() { // проверка работы формы, если не нажат чекбокс

        open("http://localhost:9999/");
        SelenideElement form = $("#root");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79185463782");
        form.$("button").click();

        String fieldColor = $("[data-test-id=agreement] .checkbox__text").getCssValue("color"); // получение цвета текста ошибки
        Assertions.assertEquals("rgba(255, 92, 92, 1)", fieldColor.trim()); // сравнение цвета текста ошибки с красным
    }
}
