package ru.netology.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.Date.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;

import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


 class CardDeliveryTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }
        @Test
        @DisplayName("Should successful plan meeting")
        void ShouldSuccessfulPlanMeeting(){
            DataGenerator.UserInfo valiUser = DataGenerator.Registration.generateUser("ru");
            int daysToAddForFirstMetting = 4;
            String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMetting);
            int daysToAddForSecondMeeting = 7;
            String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
            $("[data-test-id=city] input").setValue(valiUser.getCity());
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $("[data-test-id=date] input").setValue(firstMeetingDate);
            $("[data-test-id=name] input").setValue(valiUser.getName());
            $("[data-test-id=phone] input").setValue(valiUser.getPhone());
            $("[data-test-id=agreement]").click();
            $(byText("Запланировать")).click();
            $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
            $("[data-test-id=success-notification] .notification__content")
                    .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate))
                    .shouldBe(visible);
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $("[data-test-id=date] input").setValue(secondMeetingDate);
            $(byText("Запланировать")).click();
            $("[data-test-id=replan-notification] .notification__content")
                    .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                    .shouldBe(visible);
            $("[data-test-id=replan-notification] button").click();
            $("[data-test-id=success-notification] .notification__content")
                    .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate))
                    .shouldBe(visible);
        }
    }