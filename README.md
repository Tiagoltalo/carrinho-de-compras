# Carrinho de Compras

Projeto Java de gerenciamento de carrinho de compras.

## Estrutura do Projeto

```
carrinhodecompras/
├── pom.xml
├── README.md
├── LICENSE
├── .gitignore
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/trabalho/carrinhodecompras/
│   │   │       ├── Main.java
│   │   │       ├── model/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       └── exception/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/
│       │   └── br/com/trabalho/carrinhodecompras/service/
│       └── resources/
└── target/   ← gerada pelo build, não versionada
```

## Como executar

```bash
mvn compile
mvn exec:java -Dexec.mainClass="br.com.trabalho.carrinhodecompras.Main"
```

## Como testar

```bash
mvn test
```

## Tecnologias

- Java 17
- Maven
- JUnit 5
