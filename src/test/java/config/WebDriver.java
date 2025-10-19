package config;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static java.lang.String.format;

public class WebDriver {

    private static final WebConfig CONFIG = ConfigFactory.create(WebConfig.class, System.getProperties());
    private static final UrlConfig URL_CONFIG = ConfigFactory.create(UrlConfig.class, System.getProperties());

    public static final String BASE_URL = URL_CONFIG.baseURL();

    public static void configure() {

        // Общие настройки Selenide
        Configuration.browser = CONFIG.browser();
        Configuration.browserVersion = CONFIG.browserVersion();
        Configuration.browserSize = CONFIG.browserSize();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        String remoteUrl = CONFIG.remoteURL();
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            if (!CONFIG.user().isEmpty() && !CONFIG.password().isEmpty()) {
                remoteUrl = format("https://%s:%s@%s", CONFIG.user(), CONFIG.password(), remoteUrl.replace("https://", ""));
            }
            Configuration.remote = remoteUrl;

            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
        }

        // Настройки Chrome
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
                "--no-sandbox",
                "--enable-automation",
                "--disable-popup-blocking",
                "--disable-notifications",
                "--disable-gpu"
        );
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        Configuration.browserCapabilities = capabilities;
    }
}