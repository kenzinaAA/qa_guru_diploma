package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:config/local.properties",   // локальные настройки
        "classpath:config/remote.properties",  // удалённые настройки (например BrowserStack)
        "system:properties"        // можно переопределить через -D
})
public interface WebConfig extends Config {

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("browserVersion")
    @DefaultValue("141.0")
    String browserVersion();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("remoteUrl")
    @DefaultValue("")
    String remoteUrl();

    @Key("user")
    @DefaultValue("")
    String user();

    @Key("password")
    @DefaultValue("")
    String password();
}