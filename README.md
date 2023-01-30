**Tecnologias**:

- Java 11
- Spring Boot (Security, Data, H2)
- Insomnia (API Test)

**Como Funciona**

Para efetuar o teste na aplicação é recomendado a utilização da Insomnia ou Swagger UI. Disponibilizei o arquivo "Collection Insomnia Films.json" para importar no Insomnia.

Executar a aplicação spring-boot (spring-boot:run).
No Insomnia, deve-se seguir os passos abaixo:
- Login (criação do token)
- Iniciar partida
- Próxima jogada
- Enviar jogada (voltar ao item acima para nova jogada)
- Ranking
- Logout

Para consultar a base de dados, acessar o endereço (username: sa - sem senha):
http://localhost:8080/h2-console/

**Usuários**

Há dois usuários pré-cadastrado no Sistema, ambos estão no arquivo data.sql.
- usuário: MARIA senha: 123456
- usuário: JOSE senha: 654321

**Open API**
- *url:* http://localhost:8080/swagger-ui/index.html
