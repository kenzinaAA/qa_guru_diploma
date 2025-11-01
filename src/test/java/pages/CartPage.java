package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CartPage {

    @Step("Убедиться, что в корзине {bookNumber} товаров")
    public CartPage checkCart(int bookNumber){
        $(".cart-sidebar__item .info-item__title").shouldHave(text(String.valueOf(bookNumber)));
        return this;
    }

    @Step("Удаляем один из товаров из корзины")
    public CartPage deleteFirst(){
        $$("button.cart-item__delete-button").first().click();
        return this;
    }

    @Step("Нажать на кнопку восстановления удаленного товара")
    public CartPage restoreBook(){
        $("button.cart-item-deleted__button").shouldBe(visible).click();
        return this;
    }
}
