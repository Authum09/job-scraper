# Projektkontext: Ausbildungsplatz-Tracker (job-scraper)

Dieses Dokument fasst Planung und Entscheidungen aus einem früheren Chat zusammen,
damit eine neue Claude-Sitzung ohne den alten Verlauf weiterarbeiten kann.

## Arbeitsweise (wichtig)

- Der Nutzer ist auf Ausbildungsplatzsuche (Richtung Fachinformatik/Java) und will
  **den Code selbst schreiben**, um zu lernen.
- Claude gibt **Schritt-für-Schritt-Anleitungen, Erklärungen und Code-Reviews**,
  schreibt aber **keinen fertigen Code**, außer der Nutzer bittet ausdrücklich darum.
- Antworten auf Deutsch. Konkret werden: Klassennamen, Methodensignaturen,
  typische Stolpersteine, "Fertig, wenn ..."-Kriterien.
- Bei Fragen zu Code: Dateien im Repo lesen und gezielt Feedback geben.

## Ausgangslage: Konsolen-Scraper (Stand v1)

Repo: https://github.com/Authum09/job-scraper (Branch `master`, Maven, Java 25, Paket `org.example`)

Einzige Dependency: `jackson-databind`. Ablauf in `Main`:
Config laden -> Arbeitsagentur-API abfragen -> filtern -> Bekanntes aus `seen.txt`
entfernen -> neue Angebote per Telegram senden -> `seen.txt` speichern.

Klassen: `ArbeitsagenturSource` (implementiert `JobSource`), `ConfigLoader`,
`JobFilter`, `JobOffer`, `JobSource`, `SearchCriteria` (record), `SeenStore`,
`TelegramConfig` (record), `TelegramNotifier`.
Lokale, nicht eingecheckte Dateien: `config.json`, `secrets.json`, `seen.txt`.

### Datenquellen: Entscheidung

- **Einzige Quelle: Bundesagentur für Arbeit** (inoffizielle, frei nutzbare REST-API,
  Endpunkt `.../jobsuche-service/pc/v6/jobs`, `angebotsart=4` für Ausbildung).
- **Kein Scraping von ausbildungsmarkt.de:** Die robots.txt sperrt für `User-agent: *`
  `/suche.html`, `/job.php` und `/ausbildungsplatz/`, also Suche und Anzeigen.
- Große Portale (Indeed, StepStone, LinkedIn, Xing) verbieten Scraping per AGB
  und blocken Bots. Nicht einbauen.
- Mögliche weitere Quellen: Karriereseiten einzelner Betriebe, IHK-/HWK-Lehrstellenbörsen.
  Vorher immer robots.txt und AGB prüfen. Reihenfolge der Methoden: RSS/Sitemap ->
  interne JSON-Schnittstelle (F12 -> Netzwerk) -> JSON-LD `JobPosting` -> Jsoup
  auf statischem HTML -> Playwright nur im Notfall. Höflich bleiben (Pausen, ehrlicher User-Agent).

### Bekannte Verbesserungen am v1-Code

1. **Paginierung fehlt** (nur Seite 1 mit `size=100`). `page` und `maxErgebnisse` nutzen,
   mit Obergrenze gegen Endlosschleifen.
2. Nur **ein** Suchprofil in `config.json`. Es sollen mehrere werden.
3. **ID ohne Quellen-Präfix** (z. B. `"arbeitsagentur:" + referenznummer`), sonst
   Kollisionen bei weiteren Quellen. Quelle ist aktuell hartkodiert.
4. `JobOffer` als `record` statt Klasse mit Boilerplate. Dabei bewusst entscheiden,
   dass Gleichheit nur über die ID läuft.
5. **Gesehen wird erst am Ende gespeichert.** Wirft `send` mittendrin, gibt es beim
   nächsten Lauf Doppelmeldungen. Pro erfolgreich gesendetem Angebot speichern.
6. `seen.txt` wächst endlos (später: Zeitstempel in der Datenbank).
7. Paket `org.example` umbenennen, `.idea/` aus dem Repo entfernen
   (`git rm -r --cached .idea`, in `.gitignore` aufnehmen).
8. Kein README, keine Tests, `System.out` statt Logging (SLF4J).

## Geplantes Projekt: Umbau zu Spring-Boot-REST-API

Ziel: Aus dem Scraper ein nutzbares Tool machen und dabei Spring Boot lernen
(Portfolio-tauglich für die Bewerbung). Nicht "mehr Quellen", sondern bessere Bedienung.

Schritte (je ein Wochenende, gesamt ca. 3-5):

0. **Stand sichern:** `git tag v1.0`, neuer Branch `spring-boot`.
1. **Projekt aufsetzen:** start.spring.io (Maven; Spring Web, Spring Data JPA, Validation,
   H2, DevTools). Auf unterstützte Java-Version achten. Bestehende Klassen übernehmen,
   Start prüfen.
2. **Datenbank statt `seen.txt`:** `JobOffer` als JPA-Entity, neue Entity `SearchProfile`
   (ersetzt `SearchCriteria`), zwei `JpaRepository`. "Gesehen?" = `existsById`.
3. **Service-Schicht:** `ScrapingService` (Profile laden -> Quelle abfragen -> filtern ->
   neue speichern -> benachrichtigen). `ArbeitsagenturSource` und `TelegramNotifier` als
   Beans mit Konstruktor-Injection. Token aus `application.properties`/Umgebungsvariablen
   (`@ConfigurationProperties`), nicht aus `secrets.json`.
4. **Endpunkte:** `GET /api/offers` (Filter `?status=`), CRUD für `/api/profiles`.
   DTOs statt Entities, Bean Validation, `@RestControllerAdvice` für Fehlerantworten.
5. **Status und Zeitplan:** Feld `status` (`NEU`, `INTERESSANT`, `BEWORBEN`, `ABGESAGT`),
   `PATCH /api/offers/{id}`. Abruf per `@Scheduled`.
6. **Tests:** JUnit für Filter und Parsing (Parsing getrennt vom HTTP-Aufruf halten),
   `@WebMvcTest` mit MockMvc, `@DataJpaTest`.
7. **Oberfläche und Doku:** einfache Thymeleaf-Seite (Trefferliste, Statusknöpfe,
   Profilformular), README mit Setup und Beispielaufrufen, optional springdoc-openapi.

## Nächster Schritt

Schritt 0 und 1 umsetzen. Danach `pom.xml` und Startklasse zeigen, Claude prüft,
bevor Schritt 2 beginnt.

## Weitere Projektideen (für später)

Passwortmanager (Kryptografie), Haushaltsbuch (JDBC, `BigDecimal`), Datei-Organizer
(`WatchService`), Chat-Server (Sockets, Nebenläufigkeit), JavaFX-App, Discord-/Telegram-Bot,
Interpreter für eine Mini-Sprache (*Crafting Interpreters*), Mini-Datenbank.
