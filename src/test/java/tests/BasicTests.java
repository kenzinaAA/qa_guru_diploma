package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.*;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Owner("kenzinaAA")
@DisplayName("Тестирование веб-приложения Читай-Город")
public class BasicTests extends TestBase{

    MainPage mainPage = new MainPage();
    SearchPage searchPage = new SearchPage();
    AuthorPage authorPage = new AuthorPage();
    BookPage bookPage = new BookPage();
    CartPage cartPage = new CartPage();

    @DisplayName("Поиск товаров на двух языках")
    @ParameterizedTest(name = "Нахождение товара {1} в результатах поиска {0}")
    @Tag("BLOKER")
    @Feature("Поиск")
    @CsvSource({
            "java, Java. Основы программирования",
            "тестирование, Тестирование веб-API"
    })
    void searchResultsTest(String searchItem, String requiredItem) {
        mainPage.open().search(searchItem);
        searchPage.findBookAndMaybePress(requiredItem, false);
    }

    @DisplayName("Добавление товаров в корзину")
    @Test
    @Tag("BLOKER")
    @Feature("Корзина")
    void putIntoCartTest() {
        mainPage.open()
                .scrollAndBuy(1)
                .cartHasBook(1);
    }

    @DisplayName("Проверка функциональности корзины")
    @Test
    @Tag("CRITICAL")
    @Feature("Корзина")
    void actionsInCartTest() {
        mainPage.open()
                .scrollAndBuy(2)
                .goToCart();
        cartPage.checkCart(2)
                .deleteFirst();
        sleep(300);
        cartPage.checkCart(1)
                .restoreBook();
        sleep(300);
        cartPage.checkCart(2);
    }

    @DisplayName("Проверяем, что уточнение поиска уменьшает количество найденных товаров")
    @Test
    @Tag("MINOR")
    @Feature("Поиск")
    void detailedSearchTest() {
         mainPage.open().search("Тетрадка");
        int countPen = searchPage.numberOfFound();
        mainPage.open().search("Тетрадка в клеточку");
        int countDetail = searchPage.numberOfFound();
        step("Сравнить количество найденных товаров", () -> Assertions.assertTrue(countPen>countDetail));
    }

    @DisplayName("Проверяем переход на карточку товара")
    @Test
    @Tag("BLOKER")
    @Feature("Карточка товара")
    void bookTest() {
        String theBook = "Хоббит";
        mainPage.open().search(theBook);
        searchPage.findBookAndMaybePress(theBook, true);
        String bookName = bookPage.getBookTitle();
        step("Проверка, что товар подходит", () -> Assertions.assertTrue(bookName.contains(theBook)));
    }

    @DisplayName("Проверяем окно возрастного ограничения")
    @Test
    @Tag("BLOKER")
    @Feature("Карточка товара")
    void adultRatingTest() {
        String adultBook = "Пятьдесят оттенков серого";
        mainPage.open().search(adultBook);
        searchPage.findBookAndMaybePress(adultBook, true);
        bookPage.checkForAdultScreen();
    }

    @DisplayName("Проверяем переход из книги на страничку автора")
    @Test
    @Tag("MINOR")
    @Feature("Карточка товара")
    void bookToAuthorTest() {
        String theBook = "Хоббит";
        String theAuthor = "Джон Рональд Руэл Толкин";
        mainPage.open().search(theBook);
        searchPage.findBookAndMaybePress(theBook, true);
        bookPage.clickFirstAuthour();
        authorPage.checkAuthorName(theAuthor);
    }

    @DisplayName("Проверяем поиск книги по isbn")
    @Test
    @Tag("MINOR")
    @Feature("Поиск")
    void bookInfoTest() {
        String theBook = "Хоббит";
        mainPage.open().search(theBook);
        searchPage.findBookAndMaybePress(theBook, true);
        String bookName = bookPage.getBookTitle();
        String isbn = bookPage.getISBN();
        mainPage.open().search(isbn);
        searchPage.findBookAndMaybePress(theBook, true);
        bookPage.compareTitles(bookName);
    }

}
