# Eksamen i PGR209 - Backend programmering

## Hvordan kjøre applikasjonen og logge inn i databasen

1. Åpne IntelliJ eller din foretrukne IDE.

2. Gå til *"Order-System-For-Machine-Factory\src\main\resources\application.yml"*, og endre spring:profiles:active: til *"dev"* om du ønsker å laste inn testdata, eller *"prod"* om du ikke ønsker å laste inn testdata. 

3. Gå til  *"Order-System-For-Machine-Factory\src\main\java\no.kristiania.ordersystemformachinefactory"*,  og kjør *"OrderSystemForMachineFactoryApplication"*.

4. Gå til http://localhost:8080/h2-console og logg inn med følgende innstillinger og legitimasjon, og trykk connect:
   - Saved Settings: Generic H2 (Embedded)
   - Setting name: Generic H2 (Embedded)
   - Driver Class: org.h2.Driver
   - JDBC URL: jdbc:h2:mem:machinefactorydb
   - Brukernavn: sa
   - Passord: [*INGEN PASSORD, LA VÆRE BLANKT*]
5. Bruk Postman eller liknende verktøy for å teste API-et. Se under for eksempler på hvordan API-et kan brukes.

## TODO Sjekkliste

### Tekniske krav:
- [x] Build REST-API Using Java and Spring Boot.
- [x] Use Spring Data JPA and H2 in-memory database to persist all data.
- [x] Implement pagination for all endpoints that return lists.
- [ ] ~~Use Flyway~~ (Not required) - *By this we assume that Flyway is not required to get top score and grading.*
- [x] Make use of @SpringBootTest and @DataJpaTest Where necessary.
- [x] Unit tests.
- [x] Integration tests using MockMvc.
- [x] End-to-end tests using MocMvc.

### Funksjonelle krav:

**Relasjoner:**
- [x] A customer can have many addresses.
- [x] An address has one or more customers.
- [x] An order has one or more machines.
- [x] A machine has one or more subassemblies.
- [x] A subassembly has one or more parts.
- [x] Customer must know about addresses, and addresses must know customers they belong to.
- [x] Order must know about customers, and customers must know all their orders.
- [x] Order must know about machines, but machines do not need to know what orders they are part of.
- [x] Machines must know what subassemblies they require, subassemblies do not need to know what machines they are a part of.
- [x] Subassemblies must know about parts, parts do not need to know what machines they are part of.

**Funksjonalitet:**
- [x] Controllers, Services, Repositories should be implemented for all domain objects,
- [x] implement the following functionality for all domain objects: 
   * - [x] Get one by id.
   * - [x] Create one.
   * - [x] Delete one.
   * - [x] Update one.
   * - [x] Get all with pagination.


**Tilleggsfunksjonalitet fra oppgaveteksten:**
- [x] Create a customer, and add an address to it
- [x] Create an address and add it to a customer
- [x] Add an address to a customer.

**Tilleggsfunksjonalitet vi har funnet på:**
- [x] Add a machine to an order.
- [x] Add a subassembly to a machine.
- [x] Add a part to a subassembly.
- [x] Add order to customer

## Vurderingskriterier
- F - The application does not include at least one model, repository, service, controller, or the application does not run.
- E - The application implements partial functionality
- D - The application implements complete functionality, but no tests
- C - The application implements complete functionality, some additional functionality and includes unit tests
- B - The application implements complete functionality, some additional functionality and includes both unit and integration tests
- A - The application implements complete functionality, some additional functionality and includes unit, integration, and end-to-end tests