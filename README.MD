# Pauta api



### Descrição

Api para cadastro de pauta e gerenciamento de sessão de votação.

## Build

### Docker

```
mvn clean package -DskipTests
```
```
docker compose up -d --build
```
## API

#### Cadastrar pauta 
```
POST http://localhost:8080/pauta

{
    "name": "Debate"
}
```
#### Abrir sessão
```
PUT http://localhost:8080/pauta/sessao

{
    "pautaId": "63321860c169ed2ef0bab581",
    "minutes": 3
}
```
#### Adicionar voto a sessão
```
PUT http://localhost:8080/pauta/sessao/voto

{
    "pautaId": "63321860c169ed2ef0bab581",
    "associadoCpf": "95220733001",
    "voto": "Não"
}
```

## Swagger

http://localhost:8080/swagger-ui/index.html
