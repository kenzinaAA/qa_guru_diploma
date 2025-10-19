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
        step("Открыть главную страницу сайта", () -> mainPage.open());
        step("Осуществить поиск товаров в категории " + searchItem, () -> mainPage.find(searchItem));
        sleep(600);
        step("Проверить наличие в результатах поиска товара " + requiredItem, () ->
                mainPage.checkBook(requiredItem, false)
        );
    }

    @DisplayName("Добавление товаров в корзину")
    @Test
    @Tag("BLOKER")
    @Feature("Корзина")
    void putIntoCartTest() {
        step("Открыть главную страницу сайта", () -> mainPage.open());
        step("Найти и нажать на первую кнопку 'Купить'", () -> mainPage.scrollAndBuy(1));
        step("Проверить, что в корзине появился индикатор с числом 1", () ->
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
        step("Открыть главную страницу сайта", () -> mainPage.open());
        step("Положить любую книгу в корзину и перейти в корзину", () -> mainPage.scrollAndBuy(2).goToCart());
        step("Убедиться, что в корзине 2 товара", () -> mainPage.checkCart(2));
        step("Удаляем один из товаров из корзины", () ->
                $$("button.cart-item__delete-button").first().click()
        );
        step("Убедиться, что 1 товар удалился", () -> mainPage.checkCart(1));
        step("Нажать на кнопку восстановления удаленного товара", () ->
                $("button.cart-item-deleted__button").shouldBe(visible).click()
        );
        step("Убедиться, что товар восстановился", () -> mainPage.checkCart(2));
    }

    @DisplayName("Проверяем, что уточнение поиска уменьшает количество найденных товаров")
    @Test
    @Tag("MINOR")
    @Feature("Поиск")
    void detailedSearchTest() {
        step("Открыть главную страницу сайта", () -> mainPage.open());
        step("Осуществить поиск товаров в категории Ручка", () -> mainPage.find("Ручка"));
        int countPen = mainPage.numberOfFound();
        step("Сбрасываем результаты поиска", () -> $("a.header-sticky__logo-link").click());
        step("Осуществить поиск товаров в категории Ручка Pilot", () -> mainPage.find("Ручка Pilot"));
        int countDetail = mainPage.numberOfFound();
        step("Сравнить количество найденных товаров", () ->
                Assertions.assertTrue(countPen>=countDetail)
        );
        System.out.println(countPen + " " + countDetail);
    }

    @DisplayName("Проверяем переход на карточку товара")
    @Test
    @Tag("BLOKER")
    @Feature("Карточка товара")
    void bookTest() {
        String theBook = "Хоббит";
        step("Открыть главную страницу сайта", () -> mainPage.open());
        step("Осуществить поиск товара с взрослым рейтингом", () -> mainPage.find(theBook));
        step("Перейти на страничку товара", () ->  mainPage.checkBook(theBook, true));
        String bookName = step("Проверить, что открылась карточка товара", () ->
                $("h1.product-detail-page__title").text()
        );
        Assertions.assertTrue(bookName.contains(theBook));
    }

    @DisplayName("Проверяем окно возрастного ограничения")
    @Test
    @Tag("BLOKER")
    @Feature("Карточка товара")
    void adultRatingTest() {
        String adultBook = "Пятьдесят оттенков серого";
        step("Открыть главную страницу сайта", () -> mainPage.open());
        step("Осуществить поиск товара с взрослым рейтингом", () -> mainPage.find(adultBook));
        step("Перейти на страничку товара", () -> mainPage.checkBook(adultBook, true));
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
        step("Открыть главную страницу сайта", () -> mainPage.open());
        step("Осуществить поиск товара с взрослым рейтингом", () -> mainPage.find(theBook));
        step("Перейти на страничку товара", () ->  mainPage.checkBook(theBook, true));
        step("Переход на страницу первого автора", () -> $$("ul.product-authors li a").first().click());
        step("Проверить, что открылась карточка автора", () ->
                $("h1.author-page__title").shouldHave(text("Джон Рональд Руэл Толкин"))
        );
    }

    @DisplayName("Проверяем поиск книги по id и isbn")
    @Test
    @Tag("MINOR")
    @Feature("Поиск")
    void bookInfoTest() {
        String theBook = "Хоббит";
        step("Открыть главную страницу сайта", () -> mainPage.open());
        step("Осуществить поиск товара с взрослым рейтингом", () -> mainPage.find(theBook));
        step("Перейти на страничку товара", () ->  mainPage.checkBook(theBook, true));


    }


}
