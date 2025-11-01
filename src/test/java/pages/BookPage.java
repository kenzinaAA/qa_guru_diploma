package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BookPage {

    @Step("Проверить, что открылась карточка товара")
    public String getBookTitle(){
        return $("h1.product-detail-page__title").text();
    }

    @Step("Извлекаем isbn книги")
    public String getISBN(){
        return $("span[itemprop='isbn']").getText();
    }

    @Step("Проверить наличие упреждающего окна 18+")
    public BookPage checkForAdultScreen(){
        $("body.popmechanic-desktop.is-locked").exists();
        return this;
    }

    @Step("Переход на страницу первого автора")
    public void clickFirstAuthour(){
        $$("ul.product-authors li a").first().click();
    }

    @Step("Сверяем полное название товара с сохраненным {bookName}")
    public BookPage compareTitles(String bookName){
        $("h1.product-detail-page__title").shouldHave(text(bookName));
        return this;
    }

}
