package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @org.junit.jupiter.api.Test
    void shouldTestIfSuccess() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Архангельск");

        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(planningDate);

        $("[data-test-id=name] input").setValue("Морозова Арина");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }

    @org.junit.jupiter.api.Test
    void shouldTestIfFailedByIncorrectCity() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Борисоглебск");

        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(planningDate);

        $("[data-test-id=name] input").setValue("Морозова Арина");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=city] .input__sub")
                //.shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @org.junit.jupiter.api.Test
    void shouldTestIfFailedByIncorrectDate() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Архангельск");

        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue("01.01.2000");

        $("[data-test-id=name] input").setValue("Морозова Арина");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=date] .input__sub")
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @org.junit.jupiter.api.Test
    void shouldTestIfFailedByIncorrectName() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Архангельск");

        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(planningDate);

        $("[data-test-id=name] input").setValue("Морозова.Арина");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name] .input__sub")
                //.shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @org.junit.jupiter.api.Test
    void shouldTestIfFailedByIncorrectPhone() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Архангельск");

        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(planningDate);

        $("[data-test-id=name] input").setValue("Морозова Арина");
        $("[data-test-id=phone] input").setValue("123");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone] .input__sub")
                //.shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @org.junit.jupiter.api.Test
    void shouldTestIfFailedByNotFilledCheckbox() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Архангельск");

        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(planningDate);

        $("[data-test-id=name] input").setValue("Морозова Арина");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("button.button").click();
        $("[data-test-id=agreement] .checkbox__text")
                .shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
