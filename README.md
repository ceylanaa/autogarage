# Autogarage backend

Backend (REST API) voor een autogarage, gemaakt als eindopdracht Backend bij NOVI.
Met deze applicatie kan de garage klanten en auto's registreren, keuringen inplannen, reparaties bijhouden
(onderdelen, standaard handelingen en overige handelingen) en een bon maken inclusief 21% BTW.
Ook kunnen autopapieren (PDF) worden geüpload en gedownload.

Gemaakt met Java 21, Spring Boot 3, Spring Security (JWT) en PostgreSQL.

Belangrijkste functies:
- klanten en auto's registreren (CRUD)
- keuringen inplannen en de uitslag vastleggen
- reparaties bijhouden met onderdelen, standaard handelingen en overige handelingen
- bon maken met 21% BTW en betaling registreren
- autopapieren (PDF) uploaden en downloaden
- inloggen met JWT, met 3 rollen: kassamedewerker, monteur en backoffice

## Inhoudsopgave

1. [Projectstructuur en technieken](#projectstructuur-en-technieken)
2. [Benodigdheden](#benodigdheden)
3. [Installatie](#installatie)
4. [Tests draaien](#tests-draaien)
5. [Testgebruikers](#testgebruikers)
6. [Postman collectie](#postman-collectie)
7. [Endpoints per rol](#endpoints-per-rol)
8. [Bon](#bon)
9. [Overige commando's](#overige-commandos)

## Projectstructuur en technieken

```
autogarage/
├── docs/                      Postman collectie en diagrammen
├── src/main/java/nl/novi/autogarage/
│   ├── controllers/           REST controllers (endpoints)
│   ├── dtos/                  input- en output DTO's
│   ├── exceptions/            eigen exceptions en GlobalExceptionHandler
│   ├── mappers/               omzetten van entiteit naar DTO en andersom
│   ├── models/                entiteiten (JPA)
│   ├── repositories/          Spring Data JPA repositories
│   ├── security/              JWT filter, JwtService en SecurityConfig
│   └── services/              businesslogica (o.a. de bon)
├── src/main/resources/
│   ├── application.properties
│   └── data.sql               testdata
├── src/test/                  unit- en integratietests
└── pom.xml                    Maven dependencies
```

Gebruikte technieken:
- Java 21 en Spring Boot 3.3 (Web, Data JPA, Security, Validation)
- Maven als build tool
- PostgreSQL als database (H2 voor de tests)
- JJWT voor de JWT tokens
- Lombok
- JUnit 5, Mockito, MockMvc en JaCoCo voor het testen

## Benodigdheden

- Java 21 (JDK)
- Maven 3.8+ (of de Maven in IntelliJ)
- PostgreSQL 15 of hoger
- Postman (handig om de endpoints te testen)

## Installatie

1. Clone of download het project en open het in IntelliJ.
2. Maak in PostgreSQL een database aan:
   ```sql
   CREATE DATABASE autogarage;
   ```
3. Check `src/main/resources/application.properties`. Standaard wordt verbonden met
   `jdbc:postgresql://localhost:5432/autogarage` met gebruiker `postgres`.
   Gebruik je andere gegevens, pas dan deze regels aan of zet de environment variabelen `DB_URL`, `DB_USERNAME` en `DB_PASSWORD`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/autogarage
   spring.datasource.username=postgres
   spring.datasource.password=jouwwachtwoord
   ```
   De JWT secret kun je instellen met `JWT_SECRET` (minimaal 32 tekens).
4. Start de applicatie:
   ```bash
   mvn spring-boot:run
   ```
   De API draait dan op `http://localhost:8080`.

Let op: `ddl-auto` staat op `create-drop`, dus bij elke start worden de tabellen opnieuw aangemaakt
en wordt `data.sql` met testdata ingeladen.

## Tests draaien

```bash
mvn test
```
De tests gebruiken een H2 in-memory database (profiel `test`), dus hier heb je geen PostgreSQL voor nodig.
Er zijn unit tests (KlantService, OnderdeelService) en integratietests (KlantController, AutoController).
Een coverage rapport komt in `target/site/jacoco/index.html`.

## Testgebruikers

| Gebruikersnaam  | Wachtwoord    | Rol             |
|-----------------|---------------|-----------------|
| kassamedewerker | wachtwoord123 | KASSAMEDEWERKER |
| monteur         | wachtwoord123 | MONTEUR         |
| backoffice      | wachtwoord123 | BACKOFFICE      |

Inloggen: `POST /auth/login` met body
```json
{ "gebruikersnaam": "monteur", "wachtwoord": "wachtwoord123" }
```
Je krijgt een token terug. Zet die bij elke request in de header: `Authorization: Bearer <token>`.
Een token is 24 uur geldig.

## Postman collectie

In de map `docs` staat `Autogarage_API.postman_collection.json`.
1. Importeer dit bestand in Postman (Import > bestand kiezen).
2. Vul bij de collectie variabelen `testWachtwoord` in met het wachtwoord uit de tabel hierboven.
3. Voer de drie login requests uit (map "Openbaar"). De tokens worden dan automatisch opgeslagen
   in `token_kassamedewerker`, `token_monteur` en `token_backoffice`.
4. Daarna werken de andere requests, die staan per rol in een eigen map.

Draai de collectie (bijvoorbeeld met de Collection Runner) altijd na een herstart van de applicatie.
De requests passen de testdata aan, dus een tweede keer achter elkaar geeft bij de DELETE requests een 404.

Alle endpoints met uitleg en voorbeelden staan ook in de API-documentatie (PDF).

## Endpoints per rol

**Iedereen (zonder login)**
- `POST /auth/login`, `POST /auth/register`

**Kassamedewerker**
- `GET/POST /klanten`, `GET/PUT/DELETE /klanten/{id}`
- `GET/POST /autos`, `GET/PUT/DELETE /autos/{id}`
- `POST /autos/{id}/autopapier` (form-data, key `bestand`, PDF) en `GET /autos/{id}/autopapier`
- `POST /keuringen`, `DELETE /keuringen/{id}`, `GET /keuringen`, `GET /keuringen/{id}`
- `GET /reparaties/{id}/bon`, `PUT /reparaties/{id}/betaald`

**Monteur**
- `GET /keuringen`, `GET /keuringen/{id}`, `PUT /keuringen/{id}/status`
- `GET/POST /reparaties`, `GET /reparaties/{id}`, `PUT /reparaties/{id}/status`
- `POST /reparaties/{id}/onderdelen`, `DELETE /reparaties/{id}/onderdelen/{regelId}`
- `POST /reparaties/{id}/handelingen`, `DELETE /reparaties/{id}/handelingen/{regelId}`
- `POST /reparaties/{id}/overigehandelingen`, `DELETE /reparaties/{id}/overigehandelingen/{regelId}`
- `GET /onderdelen`, `GET /reparatiehandelingen` (alleen opzoeken)

**Backoffice**
- volledige CRUD op `/onderdelen` en `/reparatiehandelingen`
- `GET /gebruikers`

## Bon

De bon bestaat uit de keuring (vast €45,00 excl. BTW) plus alle handelingen, overige handelingen en onderdelen.
Bij elke regel staat de prijs excl. en incl. 21% BTW, en onderaan het totaal. Als de reparatie de status
`NIET_UITVOEREN` heeft, betaalt de klant alleen de keuring.

## Overige commando's

| Commando | Wat doet het |
|----------|--------------|
| `mvn clean` | verwijdert de map `target` |
| `mvn clean install` | bouwt het project en draait de tests |
| `mvn package -DskipTests` | maakt een jar zonder de tests te draaien |
| `java -jar target/autogarage-0.0.1-SNAPSHOT.jar` | start de gebouwde jar |
| `mvn test -Dtest=KlantServiceTest` | draait één testklasse |
| `mvn verify` | draait de tests en maakt het JaCoCo rapport |
