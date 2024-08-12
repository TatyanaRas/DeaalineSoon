package ru.netology.login.test.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import ru.netology.login.test.data.DataHelper;
import ru.netology.login.test.data.SQLHelper;
import ru.netology.login.test.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.login.test.data.SQLHelper.cleanAuthCodes;


public class LoginTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterEach
    void tearDownAll() {
        cleanDataBase();
    }

    @BeforeEach
    void setUp() {

        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test

    //валидные данные пользователя и верный код
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

  @Test

    //невалидные данные пользователя
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationUserRandom() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
    }

   @Test

    //валидные данные пользователя а код неверный
    @DisplayName("Should get error notification if login with exist in base and active user and random verification code")
    void shouldGetErrorNotificationUserRandomVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");

    }
}
