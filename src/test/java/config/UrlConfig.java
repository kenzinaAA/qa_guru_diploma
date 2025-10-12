package config;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:urls.properties",   // локальные настройки
        "system:properties"        // можно переопределить через -D
})
public interface UrlConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("https://www.eldorado.ru/")
    String baseURL();

    @Key("downloadFileUrl")
    @DefaultValue("https://www.eldorado.ru/b2b/")
    String downloadFileURL();

}
