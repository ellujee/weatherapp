#Retrofit on Android-kehityksessä käytettävä kirjasto, joka huolehtii HTTP-pyyntöjen hallinnasta. Se muuttaa API-rajapinnan kutsut Kotlin-funktioiksi ja hoitaa verkkoyhteyden muodostamisen OpenWeatherMap-palveluun.

#Verkkopalvelun palauttama JSON-muotoinen data muutetaan automaattisesti Kotlinin dataluokiksi. Gson-kirjasto hoitaa tämän muunnoksen taustalla, jolloin sovellus voi käyttää dataa suoraan tyypillisinä olioina ilman manuaalista jäsentämistä.

#Sovelluksessa käytetään korutiineja:

-API-kutsu tehdään taustasäikeessä, jotta sovelluksen käyttöliittymä ei jäädy verkkopyynnön ajaksi.

-Kun data on haettu onnistuneesti, korutiini palauttaa tuloksen ja käyttöliittymä päivittyy automaattisesti uuden datan mukaisesti.

#Käyttöliittymän tilan hallinta

-ViewModel: ViewModel-luokka hallitsee WeatherUiState-oliota, joka sisältää tiedon latauksesta, onnistumisesta tai virheestä.

-Jetpack Compose: Käyttöliittymä reagoi tilamuutoksiin automaattisesti. Kun ViewModelissa oleva tila muuttuu, Compose piirtää näkymän heti uudelleen.

-Turvallisuuden parantamiseksi API-avaimen pitäisi olla piilotettu koodista.
