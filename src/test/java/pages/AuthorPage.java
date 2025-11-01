package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class AuthorPage {

    @Step("Проверить, что открылась карточка автора с именем: {AuthorName}")
    public AuthorPage checkAuthorName(String AuthorName){
        $("h1.author-page__title").shouldHave(text(AuthorName));
        return this;
    }

}
