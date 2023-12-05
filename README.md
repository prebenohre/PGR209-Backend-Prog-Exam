# Eksamen i PGR209 - Backend programmering

## Hvordan kjøre applikasjonen og logge inn i databasen

1. Åpne IntelliJ eller din foretrukne IDE

2. Gå til  *"Order-System-For-Machine-Factory\src\main\java\no.kristiania.ordersystemformachinefactory",* og kjør *"OrderSystemForMachineFactoryApplication"*.

4. Gå til http://localhost:8080/h2-console og logg inn med følgende innstillinger og legitimasjon:
   - Saved Settings: Generic H2 (Embedded)
   - Setting name: Generic H2 (Embedded)
   - Driver Class: org.h2.Driver
   - JDBC URL: jdbc:h2:mem:machinefactorydb
   - Brukernavn: sa
   - Passord: [*INGEN PASSORD, LA VÆRE BLANKT*]

## TODO Sjekkliste

### Tekniske krav:
- [ ] Build REST-API Using Java and Spring Boot.
- [ ] Use Spring Data JPA and H2 in-memory database to persist all data.
- [ ] Implement pagiantion for all endpoints that return lists.
- [ ] Use Flyway (Not required)
- [ ] Make use og @SpringBootTest and @DataJpaTest Where necessary.
- [ ] Unit tests
- [ ] Integration tests
- [ ] End-to-end tests using MocMvc

### Funksjonelle krav:

**Relasjoner:**
- [ ] A customer can hav many addresses.
- [ ] An address has one or more customers.
- [ ] An order has one or more machines.
- [ ] A machine has one or more subassemblies.
- [ ] A subassembly has one or more parts.
- [ ] Customer must know about addresses, and addresses must know customers they belong to.
- [ ] Order must know about customers, and customers must know all their orders.
- [ ] Order must know about machines, but machines do not need to know what orders they are part of.
- [ ] Machines must know what subassemblies they require, subassemblies do not need to know what machin es they are a part of.
- [ ] Subassemblies must know about parts, parts do not need to know what machines they are part of.

**Funksjonalitet:**
- [ ] Controllers, Services, Repositories should be implemented for all domain objects,
- [ ] implement the following functionality for all domain objects: Get one by id, Get all with pagination, Create one, Delete one, Update one.


**Tilleggsfunksjonalitet:**
- [ ] Create a customer, and add an address to it
- [ ] Create an address and add it to a customer
- [ ] Add an address to a customer

## Vurderingskriterier
- F - That application does not include at least one model, repository, service, controller, or the application does not run.
- E - The application implements partial functionality
- D - The application implements complete fuctionality, but no tests
- C - The application implements complete functionality, some additional functionality and includes unit tests
- B - The application implements complete functionality, some additional functionality and includes both unit and integration tests
- A - The application implements complete functionality, some additional functionality and includes unit, inte gration, and end-to-end tests