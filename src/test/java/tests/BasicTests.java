package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.MainPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Owner("kenzinaAA")
@DisplayName("Тестирование веб-приложения Читай-Город")
public class BasicTests extends TestBase{

    MainPage mainPage = new MainPage();
    @DisplayName("Поиск товаров на двух языках")
    @ParameterizedTest(name = "Нахождение товара {1} в результатах поиска {0}")
    @Tag("BLOKER")
    @Feature("Поиск")
    @CsvSource({
            "java, Java. Основы программирования",
            "тестирование, Тестирование веб-API"
    })
    void searchResultsTest(String searchItem, String requiredItem) {
        mainPage.open()
                .search(searchItem)
                .checkBook(requiredItem, false);
    }

    @DisplayName("Добавление товаров в корзину")
    @Test
    @Tag("BLOKER")
    @Feature("Корзина")
    void putIntoCartTest() {
        mainPage.open()
                .scrollAndBuy(1);
        step("Проверить, что у значка Корзины появился индикатор с числом 1", () ->
                $("div.chg-indicator--bg-red.header-controls__indicator")
                        .shouldBe(visible)
                        .shouldHave(text("1"))
        );
    }

    @DisplayName("Проверка функциональности корзины")
    @Test
    @Tag("CRITICAL")
    @Feature("Корзина")
    void actionsInCartTest() {
        mainPage.open()
                .scrollAndBuy(2)
                .goToCart()
                .checkCart(2);
        step("Удаляем один из товаров из корзины", () ->
                $$("button.cart-item__delete-button").first().click()
        );
        mainPage.checkCart(1);
        step("Нажать на кнопку восстановления удаленного товара", () ->
                $("button.cart-item-deleted__button").shouldBe(visible).click()
        );
        mainPage.checkCart(2);
    }

    @DisplayName("Проверяем, что уточнение поиска уменьшает количество найденных товаров")
    @Test
    @Tag("MINOR")
    @Feature("Поиск")
    void detailedSearchTest() {
        int countPen = mainPage.open()
                .search("Тетрадка")
                .numberOfFound();
        //step("Сбрасываем результаты поиска", () -> $("a.header-sticky__logo-link").click());
        int countDetail = mainPage.open().
                search("Тетрадка в клеточку").
                numberOfFound();
        step("Сравнить количество найденных товаров", () ->
                Assertions.assertTrue(countPen>countDetail)
        );
    }

    @DisplayName("Проверяем переход на карточку товара")
    @Test
    @Tag("BLOKER")
    @Feature("Карточка товара")
    void bookTest() {
        String theBook = "Хоббит";
        mainPage.open()
                .search(theBook)
                .checkBook(theBook, true);
        String bookName = step("Проверить, что открылась карточка товара", () ->
                $("h1.product-detail-page__title").text()
        );
        step("Проверка, что товар подходит", () -> Assertions.assertTrue(bookName.contains(theBook)));
    }

    @DisplayName("Проверяем окно возрастного ограничения")
    @Test
    @Tag("BLOKER")
    @Feature("Карточка товара")
    void adultRatingTest() {
        String adultBook = "Пятьдесят оттенков серого";
        mainPage.open()
                .search(adultBook)
                .checkBook(adultBook, true);
        step("Проверить наличие упреждающего окна", () ->
                $("body.popmechanic-desktop.is-locked").exists()
        );
    }

    @DisplayName("Проверяем переход из книги на страничку автора")
    @Test
    @Tag("MINOR")
    @Feature("Карточка товара")
    void bookToAuthorTest() {
        String theBook = "Хоббит";
        mainPage.open()
                .search(theBook)
                .checkBook(theBook, true);
        step("Переход на страницу первого автора", () -> $$("ul.product-authors li a").first().click());
        step("Проверить, что открылась нужная карточка автора", () ->
                $("h1.author-page__title").shouldHave(text("Джон Рональд Руэл Толкин"))
        );
    }

    @DisplayName("Проверяем поиск книги по isbn")
    @Test
    @Tag("MINOR")
    @Feature("Поиск")
    void bookInfoTest() {
        String theBook = "Хоббит";
        mainPage.open()
                .search(theBook)
                .checkBook(theBook, true);
        String bookName = step("Извлечь полное название товара", () ->
                $("h1.product-detail-page__title").text()
        );
        String isbn = step("Извлекаем isbn", () -> $("span[itemprop='isbn']").getText());
        mainPage.search(isbn).checkBook(theBook, true);
        step("Сверяем полные названия товаров после перехода", () ->
                $("h1.product-detail-page__title").shouldHave(text(bookName))
        );
    }


}
