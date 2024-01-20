### Liquibase 

#### update

Update will run the changelogs to bring the DB up to speed

```shell
./mvnw liquibase:update
./mvnw liquibase:update -Ptest # run using test profile
```