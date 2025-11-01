package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:local.properties",
        "classpath:remote.properties",
        "system:properties"
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