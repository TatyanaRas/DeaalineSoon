package ru.netology.login.test.page;

import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");


    public void VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldBe(visible).shouldHave(exactText(expectedText));
    }

    public DashboardPage validVerify(String verificationCode) {
        //codeField.setValue(verificationCode);
        // verifyButton.click();
        verify(verificationCode);
        return new DashboardPage();
    }

    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    }
}