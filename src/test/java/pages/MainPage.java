package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static config.WebDriver.configure;
import static io.qameta.allure.Allure.step;

public class MainPage {

    // Селекторы для всплывающего окон начальной страницы
    private final SelenideElement popupCloseButton = $$("div.chg-app-button__content").findBy(text("Да, я здесь"));
    private final SelenideElement popupCloseButton2 = $("div.popmechanic-close");
    private final SelenideElement popupCloseButton3 = $("button.chg-app-button--secondary.chg-app-button--xs");

    // Селектор для первого ряда книг
    private final SelenideElement firstBookRow = $$("a.custom-link__has-link").findBy(text("Новинки литературы"));

    // Селектор для кнопки корзины
    private final SelenideElement cartButton  = $("button.header-controls__btn[aria-label='Корзина']");

    // Метод открытия главной страницы
    public MainPage open() {
        step("Открыть главную страницу сайта", () -> {
            Selenide.open();
            sleep(2000);
            closePopupsIfPresent();
        });
        return this;
    }

    // Поиск текста в строке поиска
    public MainPage search(String query){
        SelenideElement searchField = $("[name='search']");
        step("Осуществить поиск товара " + query, () -> {
            searchField.setValue(query).sendKeys(Keys.ENTER);
            $("button.search-form__button-search").shouldBe(visible).click();
            sleep(500);
        });
        return this;
    }

    // Поиск книги в результатах поиска, если требуется - с переходом
    public MainPage checkBook(String query, boolean follow){
        step("Найти нужный товар " + query + " в результатах поиска ", () -> {
            SelenideElement theBook = $$("a[class='product-card__title']")
                    .findBy(text(query))
                    .shouldBe(visible);
            if (follow)
               theBook.scrollIntoView(true).click();
        });
        return this;
    }

    // Поиск найденного количества товаров
    public Integer numberOfFound(){
        SelenideElement counter = $("sup.chg-app-tabs__tab-button-counter");
        String result = step("Посчитать количество результатов поиска", () ->
                counter.should(exist).shouldBe(visible).getText()
        );
        System.out.println(result);
        return Integer.parseInt(result);
    }

    // Скролл через главный баннер до первого ряда книг
    public MainPage scrollAndBuy(int bookNumber) {
        step("Найти и нажать 'Купить' у " + bookNumber + " разных книг", () -> {
            firstBookRow.scrollIntoView(true);
            for (int i = 0; i < bookNumber; i++)
                $$("button.chg-app-button.chg-app-button--primary.chg-app-button--s")
                        .filter(visible).get(i).click();
        });
        return this;
    }

    // Переход в Корзину
    public MainPage goToCart() {
        step("Переход в Корзину", () -> cartButton.click());
        return this;
    }

    // Проверка количества товаров в корзине
    public MainPage checkCart(int bookNumber){
        step("Убедиться, что в корзине " + bookNumber + " товаров", () ->
            $(".cart-sidebar__item .info-item__title").shouldHave(text(String.valueOf(bookNumber)))
        );
        return this;
    }

    // Метод закрытия всплывающего окна
    private void closePopupsIfPresent() {
        if (popupCloseButton.isDisplayed()) {
            popupCloseButton.shouldBe(visible).click();
        }
        if (popupCloseButton2.isDisplayed()) {
            popupCloseButton2.shouldBe(visible).click();
        }
        if (popupCloseButton3.isDisplayed()) {
            //popupCloseButton3.shouldBe(visible).click();
        }
    }

}
