package drivers;

import com.codeborne.selenide.Configuration;
import config.UrlConfig;
import config.WebConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class WebDriver {

    private static final WebConfig CONFIG = ConfigFactory.create(WebConfig.class, System.getProperties());
    private static final UrlConfig URL_CONFIG = ConfigFactory.create(UrlConfig.class, System.getProperties());

    public static final String BASE_URL = URL_CONFIG.baseUrl();

    public static void configure() {

        Configuration.baseUrl = URL_CONFIG.baseUrl();
        Configuration.browser = CONFIG.browser();
        Configuration.browserVersion = CONFIG.browserVersion();
        Configuration.browserSize = CONFIG.browserSize();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
                "--no-sandbox",
                "--disable-gpu",
                "--disable-popup-blocking",
                "--disable-notifications",
                "--disable-dev-shm-usage"
        );

        try {
            Path tmpProfile = Files.createTempDirectory("chrome-profile-");
            chromeOptions.addArguments("--user-data-dir=" + tmpProfile.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String remoteUrl = CONFIG.remoteUrl();
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            if (!CONFIG.user().isEmpty() && !CONFIG.password().isEmpty()) {
                remoteUrl = String.format("https://%s:%s@%s", CONFIG.user(), CONFIG.password(), remoteUrl.replace("https://", ""));
            }
            Configuration.remote = remoteUrl;

            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("enableVideo", true);

            chromeOptions.setCapability("selenoid:options", selenoidOptions);
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        Configuration.browserCapabilities = capabilities;
    }

}