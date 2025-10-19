package config;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:urls.properties",   // локальные настройки
        "system:properties"        // можно переопределить через -D
})
public interface UrlConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("https://www.chitai-gorod.ru/")
    String baseURL();

}
