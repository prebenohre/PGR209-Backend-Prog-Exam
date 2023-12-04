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