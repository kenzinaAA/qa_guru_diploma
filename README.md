# 🚀 Дипломный проект по UI тестированию интернет-магазина [Читай-город](https://www.chitai-gorod.ru)

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo-chitay-gorod.png)

> Проект автоматизированного тестирования UI.

## 📚 Содержание

- [Технологии и инструменты, используемые в данном проекте](#технологии-и-инструменты)
- [Архитектура тестов](#архитектура-тестов)
- [Тест-кейсы](#тест-кейсы)
- [Сборка в Jenkins](#-сборка-в-jenkins)
- [Информация о тестах в Allure report](#-allure-отчет)
- [Интеграция с TestOps](#-интеграция-с-testops)
- [Интеграция с Jira](#-интеграция-с-jira)
- [Телеграмм-бот с уведомлениями о результатах тестов](#-телеграмм-бот-с-уведомлениями-о-результатах-тестов)
- [Видео-пример при выполнение автотеста на проверку функциональности корзины](#-пример-записи-видео-при-выполнении-тестов)

---
<a id="технологии-и-инструменты"></a>
## 🛠 Технологии и инструменты, используемые в данном проекте
[<img alt="Java" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Allure.svg" width="50"/>](https://www.java.com/)
[<img alt="IDEA" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Allure_EE.svg" width="50"/>](https://www.jetbrains.com/idea/)
[<img alt="Appium" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/GitHub.svg" width="50"/>](https://appium.io/)
[<img alt="Selenide" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Gradle.svg" width="50"/>](https://ru.selenide.org/)
[<img alt="Selenoid" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Idea.svg" width="50"/>](https://aerokube.com/selenoid/latest/)
[<img alt="Android" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Java.svg" width="50"/>](https://developer.android.com/studio)
[<img alt="Browserstack" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Jenkins.svg" width="50"/>](https://www.browserstack.com/)
[<img alt="Github" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Junit5.svg" width="50"/>](https://github.com/) 
[<img alt="JUnit 5" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Selenide.svg" width="50"/>](https://junit.org/junit5/) 
[<img alt="Gradle" height="50" src="https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/logo/Telegram.svg" width="50"/>](https://gradle.org/)

В данном проекте автотесты написаны на **Java** с использованием фреймворка для тестирования **Selenide**. Для сборки проекта в среде **IntelliJ IDEA** используется **Gradle**.
**JUnit5** задействован в качестве фреймворка модульного тестирования.
---

<a id="архитектура-тестов"></a>
## 🏗 Архитектура тестов

```bash
src/test/java/
├── 📁 config/                      # ⚙️ Настройка конфигурации веб-драйвера через библиотеку Owner
│   ├── UrlConfig.java               
│   ├── WebConfig.java               
│   └── WebDriver.java                
├── 📁 helpers/                     # 🛠 Вспомогательные классы
│   └── Attach.java                   
├── pages/                          # 🛠 Классы, реализующие паттерн PageObject
│   └── MainPage.java                 
├── 📁 tests/                       # 🧪 Тестовые классы
│   ├── BasicTests.java               
│   └── TestBase.java                
└── 📁 resources/                   # 🛠 Данные для запусков тестов
    ├── local.properties              
    ├── remote.properties             
    └── urls.properties               
````

---
<a id="Покрытие функциональности"></a>
## 🏗 Тест кейсы

✅ Поиск товаров на двух языках

✅ Добавление товаров в корзину

✅ Проверка функциональности корзины

├── ✅ Удаление товаров из корзины

└── ✅ Восстановление удаленного товара

✅ Проверка работы поисковой строки при уточнении запроса

✅ Проверка перехода на карточку товара

✅ Проверка появления окна возрастного ограничения

✅ Проверка перехода на страницу автора из выбранной книги

✅ Проверка поиска книги по isbn

✅ Мануальный тест-кейс на наличие ссылки с правилами акции

---
## [Сборка в Jenkins](https://jenkins.autotests.cloud/job/Diploma_Chitai-Gorod_UI/)

clean
test
-DremoteUrl=${selenoidUrl}
-Duser=${user}
-Dpassword=${password}
-Dbrowser=${browser}
-DbrowserVersion=${browserVersion}
-DbrowserSize=${browserSize}

В данной сборке указаны следующие параметры:

_selenoidUrl_ - ссылка на selenoid.autotests.cloud

_user_ - логин

_password_ - пароль

_browser_ - браузер, в котором будут выполняться тесты (**chrome**, **firefox**)

_browserVersion_ - версия браузера

_browserSize_ - размер окна браузера при выполнении тестов

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/JenkinsRun.png)

## Информация о тестах в [Allure report](https://jenkins.autotests.cloud/user/kenzinaaa/my-views/view/all/job/Diploma_Chitai-Gorod_UI/allure/)

### Главное окно

Запуск тестов через Jenkins упирается в новый ВПН фильтр сайта Читай-город по геолокации.

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/Geo-filter.png)
![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/AllureJenkins.png)

Тесты, запущенные локально выполняются без ошибок. Окно локального отчета Allure:

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/LocalAllure.png)

#### Список тестов с примером выполнения одного из них

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/TestCases.png)

#### Разделение тестов на группы по аннотации @Feature

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/TestFeatures.png)

#### Подробное пошаговое выполнение теста

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/TestSteps.png)

### Окно с тестовыми кейсами

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/TestCases.png)

### Окно с графиками

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/AllureGraphs.png)

## Интеграция с [AllureTestOps](https://allure.autotests.cloud/project/4975/)

### Тест-кейсы с историей запусков

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/TestOppsLaunches.png)

### Мануальный тест-кейс на наличие ссылки с правилами акции

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/TestOppsCasesManual.png)

## Интеграция с [Jira](https://jira.autotests.cloud/browse/HOMEWORK-1519)

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/Jira.png)

## Телеграмм-бот с уведомлениями о результатах тестов

После завершения тестов отчет о прохождении приходит в Telegram с помощью заранее созданного бота

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/ChatBot.jpg)

## Видео-пример при выполнение автотеста на проверку функциональности корзины

![](https://github.com/kenzinaAA/qa_guru_diploma/blob/main/images/TestCart.gif)
