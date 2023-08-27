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

        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);

        $("[data-test-id=name] input").setValue("Морозова Арина");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

//    @org.junit.jupiter.api.Test
//    void shouldTestIfFailedByUnavailableCity() {
//        open("http://localhost:9999");
//
//        $("[data-test-id=city] input").setValue("Борисоглебск");
//
//        String currentDate = generateDate(4, "dd.MM.yyyy");
//        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        $("[data-test-id=date] input").sendKeys(currentDate);
//
//        $("[data-test-id=name] input").setValue("Морозова Арина");
//        $("[data-test-id=phone] input").setValue("+71234567890");
//        $("[data-test-id=agreement]").click();
//        $("button.button").click();
//        $("input_invalid.input__sub")
//                //.shouldBe(Condition.visible, Duration.ofSeconds(15))
//                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
//    }

}
