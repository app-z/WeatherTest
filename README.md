# WeatherTest
Yandex Weather Test
XML Retrofit Parser Sample

Feed urls
```
private static final String CITIES_SERVER = "https://pogoda.yandex.ru";
private static final String WEATHER_SERVER = "http://export.yandex.ru";
```

Retrofit settigs for SimpleXMLConverter
```
// Set up Retrofit for cities
RestAdapter restCitiesAdapter = new RestAdapter.Builder()
  .setEndpoint(CITIES_SERVER)
  //  .setLogLevel(RestAdapter.LogLevel.FULL)
      .setClient(new MyUrlConnectionClient())
      .setConverter(new SimpleXMLConverter())
      .build();
```
For ignore unused XML elements while deserializing a feed use @Root(strict=false)

```
@Root(name = "forecast", strict=false)
public class Forecast implements Serializable{
...
```
