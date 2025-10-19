package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.sun.tools.javac.Main;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static config.WebDriver.BASE_URL;

public class MainPage {

    // Селектор для всплывающего окна выбора города
    private SelenideElement popupCloseButton = $$("div.chg-app-button__content").findBy(text("Да, я здесь"));
    private SelenideElement footerPopup = $("div.popmechanic-close");

    // Селектор для первого ряда книг
    private SelenideElement firstBookRow = $$("a.custom-link__has-link").findBy(text("Новинки литературы"));

    // Селектор для кнопки корзины
    private SelenideElement cartButton  = $("button.header-controls__btn[aria-label='Корзина']");
    // Селектор для информационной области в корзине
    private SelenideElement cartInfo = $(".cart-sidebar__item .info-item__title");
    // Селектор для информационной области в корзине
    private SelenideElement searchField =  $("[name='search']");

    // Метод открытия главной страницы
    public MainPage open() {
        Selenide.open(BASE_URL);
        sleep(300);
        closePopupIfPresent();
        return this;
    }

    // Поиск текста в строке поиска
    public MainPage find(String query){
        searchField.setValue(query);
        searchField.sendKeys(Keys.ENTER);
        closePopupIfPresent();
        return this;
    }

    // Поиск книги в результатах поиска, если требуется - с переходом
    public MainPage checkBook(String query, boolean follow){
        SelenideElement theBook = $$("a[class='product-card__title']")
                .findBy(text(query))
                .shouldBe(visible);
        if (follow){
            theBook.scrollIntoView(true).click();
        }
        return this;
    }

    // Поиск найденного количества товаров
    public Integer numberOfFound(){
        // Селектор для количества найденных элементов
        SelenideElement counter = $("sup.chg-app-tabs__tab-button-counter");
        counter.should(exist);
        return Integer.parseInt(counter.getText());
    }

    // Скролл через главный баннер до первого ряда книг
    public MainPage scrollAndBuy(int bookNumber) {
        firstBookRow.scrollIntoView(true);
        for (int i=0; i<bookNumber; i++)
        $$("button.chg-app-button.chg-app-button--primary.chg-app-button--s")
                .filter(visible).get(i).click();
        return this;
    }

    // Переход в Корзину
    public MainPage goToCart() {
        cartButton.click();
        return this;
    }

    // Проверка количества товаров в корзине
    public MainPage checkCart(int bookNumber){
        cartInfo.shouldHave(text(String.valueOf(bookNumber)));
        return this;
    }

    // Метод закрытия всплывающего окна
    private void closePopupIfPresent() {
        if (popupCloseButton.isDisplayed()) {
            popupCloseButton.click();
        }
        if (footerPopup.isDisplayed()){
            footerPopup.click();
        }
    }

}
