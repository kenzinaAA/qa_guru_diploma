package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static drivers.WebDriver.BASE_URL;

public class MainPage {

    private final SelenideElement popupCloseButton = $$("div.chg-app-button__content").findBy(text("Да, я здесь"));
    private final SelenideElement popupCloseButton2 = $("div.popmechanic-close");
    private final SelenideElement firstBookRow = $$("a.custom-link__has-link").findBy(text("Новинки литературы"));
    private final SelenideElement cartButton  = $("button.header-controls__btn[aria-label='Корзина']");

    @Step("Открыть главную страницу сайта")
    public MainPage open() {
        Selenide.open(BASE_URL);
        sleep(2000);
        closePopupsIfPresent();
        return this;
    }

    @Step("Осуществить поиск товара '{query}'")
    public MainPage search(String query){
        SelenideElement searchField = $("[name='search']");
        searchField.setValue(query).sendKeys(Keys.ENTER);
        $("button.search-form__button-search").shouldBe(visible).click();
        sleep(1000);
        return this;
    }

    @Step("Найти и нажать 'Купить' у {bookNumber} разных книг")
    public MainPage scrollAndBuy(int bookNumber) {
        firstBookRow.scrollIntoView(true);
        for (int i = 0; i < bookNumber; i++)
            $$("button.chg-app-button.chg-app-button--primary.chg-app-button--s")
                    .filter(visible).get(i).click();
        return this;
    }

    @Step("Перейти в корзину")
    public void goToCart() {
        cartButton.click();
    }

    @Step("Проверить, что у иконки Корзины появился индикатор с числом {number}")
    public void cartHasBook(int number){
        $("div.chg-indicator--bg-red.header-controls__indicator")
                .shouldBe(visible)
                .shouldHave(text(String.valueOf(number)));
    }

    private void closePopupsIfPresent() {
        if (popupCloseButton.isDisplayed()) {
            popupCloseButton.shouldBe(visible).click();
        }
        if (popupCloseButton2.isDisplayed()) {
            popupCloseButton2.shouldBe(visible).click();
        }
    }

}
