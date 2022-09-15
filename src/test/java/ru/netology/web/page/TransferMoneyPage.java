package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferMoneyPage {

    private SelenideElement heading1 = $("[data-test-id='dashboard']");
    private SelenideElement heading2 = $("div#root h1");
    private SelenideElement amountFiled = $("[data-test-id='amount'] input");
    private SelenideElement fromFiled = $("[data-test-id='from'] input");
    private SelenideElement transactButton = $("[data-test-id='action-transfer'] span");
    private SelenideElement errorMassage = $(".notification__content");

    public TransferMoneyPage() {
        heading1.shouldBe(visible);
        heading2.shouldBe(visible);
    }

    public TransferMoneyPage moneyTransfer(int amount, String cardNumber) {
        amountFiled.setValue(String.valueOf(amount));
        fromFiled.setValue(cardNumber);
        transactButton.click();
        return new TransferMoneyPage();
    }

    public void errorMassage() {
        errorMassage.shouldHave(text("Ошибка")).shouldBe(visible);
    }
}
