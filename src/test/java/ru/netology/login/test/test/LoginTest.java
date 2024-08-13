package ru.netology.login.test.test;

import org.junit.jupiter.api.*;


import ru.netology.login.test.data.DataHelper;
import ru.netology.login.test.data.SQLHelper;
import ru.netology.login.test.page.LoginPage;
import ru.netology.login.test.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.login.test.data.SQLHelper.cleanAuthCodes;
import static ru.netology.login.test.data.SQLHelper.cleanDatabase;


public class LoginTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() {
   //     cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
     //   cleanDatabase();
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
      //  verificationPage.verify(SQLHelper.getVerificationCode());
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");

    }

}
