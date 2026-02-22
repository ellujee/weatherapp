#Viikko 5

-Retrofit on Android-kehityksessä käytettävä kirjasto, joka huolehtii HTTP-pyyntöjen hallinnasta. Se muuttaa API-rajapinnan kutsut Kotlin-funktioiksi ja hoitaa verkkoyhteyden muodostamisen OpenWeatherMap-palveluun.

-Verkkopalvelun palauttama JSON-muotoinen data muutetaan automaattisesti Kotlinin dataluokiksi. Gson-kirjasto hoitaa tämän muunnoksen taustalla, jolloin sovellus voi käyttää dataa suoraan tyypillisinä olioina ilman manuaalista jäsentämistä.

-Sovelluksessa käytetään korutiineja varmistamaan sujuva käyttökokemus:

API-kutsu tehdään taustasäikeessä, jotta sovelluksen käyttöliittymä ei jäädy verkkopyynnön ajaksi.
Kun data on haettu onnistuneesti, korutiini palauttaa tuloksen ja käyttöliittymä päivittyy automaattisesti uuden datan mukaisesti.

Käyttöliittymän tilan hallinta

ViewModel: ViewModel-luokka hallitsee WeatherUiState-oliota, joka sisältää tiedon latauksesta, onnistumisesta tai virheestä.

Jetpack Compose: Käyttöliittymä reagoi tilamuutoksiin automaattisesti. Kun ViewModelissa oleva tila muuttuu, Compose piirtää näkymän heti uudelleen.

Turvallisuuden parantamiseksi API-avaimen pitäisi olla piilotettu koodista.

#Viikko 6

-Room-arkkitehtuurin osat:

Entity: Määrittää tietokannan rakenteen (taulun)..

DAO: Sisältää rajapinnan, jolla suoritetaan SQL-kyselyt.

Database: Itse tietokantaolio, joka hallitsee yhteyttä ja versiointia.

Repository:Päättää haetaanko data verkosta vai paikallisesti.

ViewModel: Pitää huolen käyttöliittymän tilasta ja tarjoaa datan UI:lle.

UI: Näyttää datan käyttäjälle ja reagoi painikkeiden painalluksiin.

-Projektin rakenne

data.model: Sisältää säädatat.

data.local: Sisältää Room-tietokannan ja DAO-rajapinnan.

data.remote: Sisältää Retrofit-asetukset verkkokutsuja varten.

repository: Sisältää Repository-luokan, joka yhdistää paikallisen ja etätiedon.

ui.theme.weather: Sisältää ViewModelin, sen Factoryn ja Compose-näkymän.

-Datavirran kulku

Haku: Käyttäjä painaa "Hae" -> UI kutsuu ViewModelia.

API-pyyntö: ViewModel pyytää Repositorya hakemaan sään Retrofitillä verkosta.

Tallennus: Kun käyttäjä painaa "Tallenna", ViewModel muuntaa verkkodatan Entityksi ja käskee Repositorya tallentamaan sen.

Päivitys: Room palauttaa tiedot Flow-muodossa. Repository välittää tämän ViewModelille, joka päivittää StateFlow-tilan.

Näyttö: Compose-UI huomaa tilan muutoksen ja päivittää listan näytölle automaattisesti.

-Välimuistilogiikka

Verkkohaku: Uusin sää haetaan aina ensin OpenWeather API:sta, jotta käyttäjä näkee reaaliaikaisen tilanteen.

Pysyvyys: Kun sää tallennetaan, se jää Room-tietokantaan talteen. Jos sovellus suljetaan tai nettiyhteys katkeaa, käyttäjä näkee aiemmin tallennetut säätiedot historiasta.
