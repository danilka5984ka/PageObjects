package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.commands.As;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class MoneyTransferTest {

    @BeforeEach
    void openBrowser() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstBeforeTransfer = dashboardPage.getCardBalance(0);
        int balanceSecondBeforeTransfer = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardRefill(0);
        int amount = 1000;
        moneyTransferPage.moneyTransfer(amount, DataHelper.gerSecondCardInfo().getCardNumber());
        Assertions.assertEquals(balanceFirstBeforeTransfer + amount, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balanceSecondBeforeTransfer - amount, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstBeforeTransfer = dashboardPage.getCardBalance(0);
        int balanceSecondBeforeTransfer = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardRefill(1);
        int amount = 5000;
        moneyTransferPage.moneyTransfer(amount, DataHelper.getFirstCardInfo().getCardNumber());
        Assertions.assertEquals(balanceFirstBeforeTransfer - amount, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balanceSecondBeforeTransfer + amount, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldNotTransferMoneyFromFirstToFirst() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstBeforeTransfer = dashboardPage.getCardBalance(0);
        int balanceSecondBeforeTransfer = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardRefill(0);
        int amount = 1000;
        moneyTransferPage.moneyTransfer(amount, DataHelper.getFirstCardInfo().getCardNumber());
        Assertions.assertEquals(balanceFirstBeforeTransfer, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balanceSecondBeforeTransfer, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldNotTransferMoneyFromSecondToFirstOverLimit() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstBeforeTransfer = dashboardPage.getCardBalance(0);
        int balanceSecondBeforeTransfer = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardRefill(0);
        moneyTransferPage.moneyTransfer(balanceSecondBeforeTransfer + 11000, DataHelper.gerSecondCardInfo().getCardNumber());
        moneyTransferPage.errorMassage();
        Assertions.assertEquals(balanceFirstBeforeTransfer, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balanceSecondBeforeTransfer, dashboardPage.getCardBalance(1));

    }

}
