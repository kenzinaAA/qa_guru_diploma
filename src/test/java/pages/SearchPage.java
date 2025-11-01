package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchPage {

    @Step("Найти в результата поиска товар '{query}'. Нужно перейти: {follow}.")
    public SearchPage findBookAndMaybePress(String query, boolean follow){
        SelenideElement theBook = $$("a[class='product-card__title']")
                .findBy(text(query))
                .shouldBe(visible);
        if (follow)
            theBook.scrollIntoView(true).click();
        return this;
    }

    @Step("Посчитать количество результатов поиска")
    public Integer numberOfFound(){
        SelenideElement counter = $("sup.chg-app-tabs__tab-button-counter");
        String result = counter.should(exist).shouldBe(visible).getText();
        return Integer.parseInt(result);
    }
}
