# Dokumentacja aplikacji Premier League

## 1. Informacje ogolne

Aplikacja jest projektem mobilnym na Androida napisanym w Kotlinie. Interfejs uzytkownika zostal wykonany w Jetpack Compose. Projekt korzysta z architektury zblizonej do MVVM: ekrany Compose pobieraja dane przez ViewModel, ViewModel komunikuje sie z repozytorium, repozytorium pobiera dane z API lub z lokalnej bazy Room.

Glowny pakiet aplikacji:

```text
com.example.premierleagueapka
```

Glowny katalog kodu:

```text
app/src/main/java/com/example/premierleagueapka/
```

Glowny ekran startowy aplikacji:

```text
app/src/main/java/com/example/premierleagueapka/MainActivity.kt
```

## 2. Technologie

Projekt wykorzystuje:

- Kotlin
- Jetpack Compose
- Navigation Compose
- Room
- Retrofit
- Gson
- SharedPreferences
- MediaPlayer
- VideoView przez AndroidView

Najwazniejsze zaleznosci znajduja sie w:

```text
app/build.gradle.kts
```

## 3. Struktura katalogow

```text
app/src/main/java/com/example/premierleagueapka/
```

Najwazniejsze foldery:

```text
data/local/
```

Lokalne dane aplikacji: Room, DAO, encje oraz SharedPreferences.

```text
data/remote/
```

Konfiguracja Retrofit oraz definicje odpowiedzi API.

```text
data/repository/
```

Warstwa repozytoriow. Laczy API z Room i przygotowuje dane dla ViewModeli.

```text
viewmodel/
```

ViewModele dla ekranow aplikacji.

```text
ui/screens/
```

Ekrany aplikacji napisane w Jetpack Compose.

```text
ui/components/
```

Wspolne komponenty UI, np. pasek gorny, dolne menu, herby klubow.

```text
navigation/
```

Nawigacja miedzy ekranami.

## 4. Architektura MVVM

Przeplyw danych w aplikacji:

```text
API / Room
   |
Repository
   |
ViewModel
   |
Compose Screen
```

Przyklad dla meczow:

```text
FootballApi -> MatchesRepository -> MatchesViewModel -> MatchesScreen
```

Przyklad dla strzelcow:

```text
FootballDataApi -> PlayerStatsRepository -> PlayerStatsViewModel -> PlayerStatsScreen
```

Repozytoria dzialaja w sposob Room-first. Oznacza to, ze aplikacja najpierw sprawdza, czy dane sa juz zapisane lokalnie w Room. Jezeli sa, pokazuje dane z bazy bez ponownego pobierania z internetu. Jezeli ich nie ma, pobiera dane z API i zapisuje je do Room.

## 5. Nawigacja

Nawigacja znajduje sie w pliku:

```text
app/src/main/java/com/example/premierleagueapka/navigation/NavGraph.kt
```

Dostepne trasy:

| Trasa | Ekran | Opis |
| --- | --- | --- |
| `menu` | `MenuScreen` | Ekran glowny |
| `table` | `TableScreen` | Tabela ligowa |
| `matches` | `MatchesScreen` | Mecze i wyniki |
| `match_stats/{matchId}` | `MatchStatsScreen` | Szczegoly meczu |
| `statistics` | `PlayerStatsScreen` | Tabela strzelcow |
| `news` | `NewsScreen` | Lista wiadomosci |
| `news_detail/{newsId}` | `NewsDetailScreen` | Szczegoly wiadomosci |
| `media` | `MediaScreen` | Audio i video |
| `settings` | `SettingsScreen` | Ustawienia |

## 6. Baza danych Room

Baza danych znajduje sie w:

```text
app/src/main/java/com/example/premierleagueapka/data/local/database/AppDatabase.kt
```

Provider bazy:

```text
app/src/main/java/com/example/premierleagueapka/data/local/database/DatabaseProvider.kt
```

Nazwa bazy:

```text
football_db
```

Aktualna wersja bazy:

```text
7
```

Baza zawiera cztery tabele:

- `matches`
- `teams`
- `player_stats`
- `news`

### 6.1. Tabela `matches`

Encja:

```text
app/src/main/java/com/example/premierleagueapka/data/local/entity/MatchEntity.kt
```

DAO:

```text
app/src/main/java/com/example/premierleagueapka/data/local/dao/MatchDao.kt
```

Struktura:

| Pole | Typ | Opis |
| --- | --- | --- |
| `id` | `Int` | Klucz glowny |
| `homeTeam` | `String` | Gospodarz |
| `awayTeam` | `String` | Gosc |
| `homeScore` | `Int` | Bramki gospodarzy |
| `awayScore` | `Int` | Bramki gosci |
| `round` | `Int` | Numer kolejki |
| `possessionHome` | `Int` | Posiadanie pilki gospodarzy |
| `possessionAway` | `Int` | Posiadanie pilki gosci |
| `shotsHome` | `Int` | Strzaly gospodarzy |
| `shotsAway` | `Int` | Strzaly gosci |

Najwazniejsze zapytania:

- `getAll()` - pobiera wszystkie mecze posortowane po kolejce i id
- `getById(id)` - pobiera jeden mecz
- `insertAll(matches)` - zapisuje mecze
- `deleteAll()` - usuwa mecze

### 6.2. Tabela `teams`

Encja:

```text
app/src/main/java/com/example/premierleagueapka/data/local/entity/TeamEntity.kt
```

DAO:

```text
app/src/main/java/com/example/premierleagueapka/data/local/dao/TeamDao.kt
```

Struktura:

| Pole | Typ | Opis |
| --- | --- | --- |
| `id` | `Int` | Klucz glowny generowany automatycznie |
| `name` | `String` | Nazwa klubu |
| `points` | `Int` | Punkty |
| `goalBalance` | `Int` | Bilans bramkowy |

Tabela jest budowana na podstawie wynikow meczow. Logika liczenia punktow i bilansu bramkowego znajduje sie w:

```text
app/src/main/java/com/example/premierleagueapka/data/repository/MatchesRepository.kt
```

### 6.3. Tabela `player_stats`

Encja:

```text
app/src/main/java/com/example/premierleagueapka/data/local/entity/PlayerStatsEntity.kt
```

DAO:

```text
app/src/main/java/com/example/premierleagueapka/data/local/dao/PlayerStatsDao.kt
```

Struktura:

| Pole | Typ | Opis |
| --- | --- | --- |
| `id` | `Int` | Klucz glowny generowany automatycznie |
| `playerName` | `String` | Imie i nazwisko zawodnika |
| `teamName` | `String` | Klub zawodnika |
| `goals` | `Int` | Liczba bramek |
| `assists` | `Int` | Liczba asyst |

Dane sa pobierane z football-data.org i zapisywane do Room.

### 6.4. Tabela `news`

Encja:

```text
app/src/main/java/com/example/premierleagueapka/data/local/entity/NewsEntity.kt
```

DAO:

```text
app/src/main/java/com/example/premierleagueapka/data/local/dao/NewsDao.kt
```

Struktura:

| Pole | Typ | Opis |
| --- | --- | --- |
| `id` | `Int` | Klucz glowny generowany automatycznie |
| `title` | `String` | Tytul wiadomosci |
| `content` | `String` | Tresc wiadomosci |
| `imageName` | `String` | Nazwa obrazka z `res/drawable` |

Newsy startowe sa dodawane w:

```text
app/src/main/java/com/example/premierleagueapka/viewmodel/NewsViewModel.kt
```

Obrazki newsow znajduja sie w:

```text
app/src/main/res/drawable/
```

Przyklad:

```kotlin
NewsEntity(
    title = "Tytul newsa",
    content = "Tresc newsa",
    imageName = "senesi"
)
```

Dla `imageName = "senesi"` aplikacja szuka pliku:

```text
app/src/main/res/drawable/senesi.jpg
```

## 7. API

Definicje API:

```text
app/src/main/java/com/example/premierleagueapka/data/remote/FootballApi.kt
```

Konfiguracja Retrofit:

```text
app/src/main/java/com/example/premierleagueapka/data/remote/FootballApiProvider.kt
```

### 7.1. API meczow

Mecze sa pobierane z repozytorium openfootball:

```text
https://raw.githubusercontent.com/openfootball/football.json/master/2025-26/en.1.json
```

Endpoint w kodzie:

```kotlin
@GET("2025-26/en.1.json")
suspend fun getPremierLeagueMatches(): CompetitionResponse
```

Dane sa nastepnie mapowane do `MatchEntity` i zapisywane w tabeli `matches`.

### 7.2. API strzelcow

Tabela strzelcow korzysta z:

```text
https://api.football-data.org/
```

Endpoint:

```kotlin
@GET("v4/competitions/PL/scorers")
```

API wymaga klucza:

```text
FOOTBALL_DATA_API_KEY
```

Klucz nalezy dodac w pliku:

```text
local.properties
```

Przyklad:

```properties
FOOTBALL_DATA_API_KEY=twoj_klucz
```

Pliku `local.properties` nie nalezy wrzucac na GitHuba.

## 8. Opis ekranow

### 8.1. Ekran glowny

Plik:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/MenuScreen.kt
```

Opis:

- pokazuje logo Premier League
- wyswietla powitanie z imieniem zapisanym w ustawieniach
- zawiera wejscia do glownych funkcji aplikacji
- logo posiada animacje alpha

Animacja znajduje sie w:

```text
app/src/main/java/com/example/premierleagueapka/ui/components/AppChrome.kt
```

Funkcja:

```kotlin
AnimatedPremierLogo()
```

### 8.2. Tabela

Plik:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/TableScreen.kt
```

Opis:

- pokazuje tabele klubow Premier League
- wyswietla miejsce, herb, nazwe klubu, bilans bramkowy i punkty
- dane sa pobierane przez `TableViewModel`
- tabela jest liczona na podstawie wynikow meczow
- wybrane miejsca maja kolorowe, polprzezroczyste tlo:
  - 1 miejsce: zielone
  - 2-5: granatowe
  - 6-7: pomaranczowe
  - 8: jasnoniebieskie
  - 18-20: czerwone

### 8.3. Mecze i wyniki

Plik:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/MatchesScreen.kt
```

Opis:

- pokazuje mecze Premier League z sezonu 2025/26
- posiada wybor kolejki od 1 do 38
- wyswietla herby klubow i wynik
- dane sa pobierane z API openfootball i zapisywane do Room
- po kliknieciu meczu mozna przejsc do statystyk meczu

### 8.4. Statystyki meczu

Plik:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/MatchStatsScreen.kt
```

Opis:

- pokazuje szczegoly wybranego meczu
- wyswietla druzyny, wynik, posiadanie pilki i strzaly
- dane sa pobierane z lokalnej bazy Room na podstawie `matchId`

### 8.5. Tabela strzelcow

Plik:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/PlayerStatsScreen.kt
```

Opis:

- pokazuje zawodnikow Premier League
- wyswietla imie i nazwisko, klub, bramki oraz asysty
- dane sa pobierane z football-data.org
- po pobraniu dane sa zapisywane w Room
- jezeli dane sa juz w Room, aplikacja uzywa lokalnej kopii

### 8.6. Wiadomosci

Plik listy:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/NewsScreen.kt
```

Plik szczegolow:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/NewsDetailScreen.kt
```

Opis:

- `NewsScreen` pokazuje liste newsow
- po kliknieciu newsa otwiera sie `NewsDetailScreen`
- szczegoly newsa pokazuja obrazek nad tytulem i trescia
- newsy sa zapisane w Room
- obrazek jest wybierany przez pole `imageName`

### 8.7. Media

Plik:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/MediaScreen.kt
```

Opis:

- ekran pokazuje lokalny film MP4
- film jest osadzony przez `VideoView`
- `VideoView` jest uzyty w Compose przez `AndroidView`
- film jest odtwarzany w petli
- film jest wyciszony, zeby nie mieszal sie z hymnem w tle

Aktualny plik video:

```text
app/src/main/res/raw/waving_flag.mp4
```

Odwolanie w kodzie:

```kotlin
R.raw.waving_flag
```

### 8.8. Ustawienia

Plik:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/SettingsScreen.kt
```

Opis:

- mozna zapisac imie uzytkownika
- mozna zapisac ulubiony klub
- mozna zapisac date urodzenia
- mozna wlaczyc lub wylaczyc hymn w tle
- ustawienia sa zapisywane w `SharedPreferences`

Obsluga ustawien:

```text
app/src/main/java/com/example/premierleagueapka/data/local/UserPreferences.kt
```

## 9. Wspolny interfejs aplikacji

Wspolny wyglad ekranow znajduje sie w:

```text
app/src/main/java/com/example/premierleagueapka/ui/components/AppChrome.kt
```

Najwazniejsze elementy:

- `AppScreen()` - wspolny layout kazdego ekranu
- gorny pasek z tytulem ekranu
- przycisk powrotu
- dolne menu nawigacyjne
- `TeamBadge()` - wyswietlanie herbow klubow
- `AnimatedPremierLogo()` - animowane logo

Dolne menu zawiera:

- Tabela
- Mecze
- Bramki
- News
- Media
- Opcje

## 10. Zasoby graficzne, audio i video

### 10.1. Herby i obrazki

Folder:

```text
app/src/main/res/drawable/
```

Przyklady:

```text
arsenal.png
chelsea.png
liverpool.png
tottenham.png
pl_lion.png
senesi.jpg
psg.jpg
guardiola.jpg
europa.jpg
```

Herby klubow sa mapowane w:

```text
app/src/main/java/com/example/premierleagueapka/ui/components/AppChrome.kt
```

Funkcja:

```kotlin
teamBadge(team: String)
```

### 10.2. Audio

Folder:

```text
app/src/main/res/raw/
```

Aktualny hymn:

```text
app/src/main/res/raw/pl_anthem.mp3
```

Odtwarzanie hymnu znajduje sie w:

```text
app/src/main/java/com/example/premierleagueapka/MainActivity.kt
```

Hymn jest odtwarzany przez `MediaPlayer`, zapetlony i ustawiony na nizsza glosnosc.

### 10.3. Video

Folder:

```text
app/src/main/res/raw/
```

Aktualny film:

```text
app/src/main/res/raw/waving_flag.mp4
```

Film jest podlaczony w:

```text
app/src/main/java/com/example/premierleagueapka/ui/screens/MediaScreen.kt
```

## 11. Ustawienia uzytkownika

Ustawienia sa zapisane w `SharedPreferences`.

Plik:

```text
app/src/main/java/com/example/premierleagueapka/data/local/UserPreferences.kt
```

Zapisywane wartosci:

| Klucz | Opis |
| --- | --- |
| `name` | Imie uzytkownika |
| `favorite_club` | Ulubiony klub |
| `birth_date` | Data urodzenia |
| `anthem_enabled` | Czy hymn w tle jest wlaczony |

## 12. Najwazniejsze rozwiazania techniczne

### 12.1. Room-first

Aplikacja najpierw czyta dane z lokalnej bazy Room. Dopiero jezeli danych brakuje, pobiera je z API i zapisuje lokalnie.

Zalety:

- szybsze dzialanie po pierwszym uruchomieniu
- mniejsza liczba zapytan do internetu
- dane sa dostepne po ponownym uruchomieniu aplikacji

### 12.2. Obsługa formatu wyniku z API

W openfootball pole `score` moze miec rozna strukture JSON. Dlatego w `MatchesRepository` jest parser oparty o `JsonElement`, ktory obsluguje zarowno tablice, jak i obiekt z wynikiem `ft`.

Plik:

```text
app/src/main/java/com/example/premierleagueapka/data/repository/MatchesRepository.kt
```

### 12.3. Optymalizacja obrazkow

Herby klubow zostaly zmniejszone do 256x256 px i zachowuja przezroczystosc PNG. Zmniejsza to lagi przy listach i podczas przewijania.

### 12.4. Bezpieczne ladowanie herbow

Funkcja `safeDrawable()` chroni aplikacje przed crashem, gdyby ktorys plik graficzny byl pusty albo uszkodzony.

Plik:

```text
app/src/main/java/com/example/premierleagueapka/ui/components/AppChrome.kt
```

### 12.5. Video zgodne z Androidem

Film `waving_flag.mp4` zostal przekodowany do formatu:

```text
H.264, 1280x720, yuv420p
```

To jest bezpieczniejszy format dla `VideoView` na Androidzie.

## 13. Jak dodac nowe newsy

Edytuj plik:

```text
app/src/main/java/com/example/premierleagueapka/viewmodel/NewsViewModel.kt
```

Dodaj nowy element:

```kotlin
NewsEntity(
    title = "Tytul",
    content = "Tresc",
    imageName = "nazwa_obrazka"
)
```

Obrazek wrzuc do:

```text
app/src/main/res/drawable/
```

Przyklad:

```text
app/src/main/res/drawable/nazwa_obrazka.jpg
```

Wazne: nazwa pliku powinna miec male litery, cyfry i podkreslenia, bez spacji i polskich znakow.

## 14. Jak podmienic audio

Plik hymnu:

```text
app/src/main/res/raw/pl_anthem.mp3
```

Najprosciej podmienic plik na inny MP3 o tej samej nazwie:

```text
pl_anthem.mp3
```

Kod odwoluje sie do niego przez:

```kotlin
R.raw.pl_anthem
```

## 15. Jak podmienic video

Plik video:

```text
app/src/main/res/raw/waving_flag.mp4
```

Najprosciej podmienic plik na inny MP4 o tej samej nazwie:

```text
waving_flag.mp4
```

Kod odwoluje sie do niego przez:

```kotlin
R.raw.waving_flag
```

Zalecany format video:

```text
H.264, yuv420p, 720p lub 1080p
```

## 16. Jak uruchomic projekt

1. Otworz projekt w Android Studio.
2. Poczekaj na synchronizacje Gradle.
3. Podlacz telefon z wlaczonym debugowaniem USB albo uruchom emulator.
4. Kliknij Run.

Alternatywnie z terminala:

```bash
sh gradlew :app:assembleDebug
```

APK debug znajduje sie potem w:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## 17. Pliki, ktorych nie wrzucac publicznie

Nie nalezy wrzucac na GitHuba:

```text
local.properties
```

Ten plik moze zawierac prywatny klucz:

```text
FOOTBALL_DATA_API_KEY
```

