**Tecnologias**:

- Java 11
- Spring Boot (Security, Data, H2)
- Insomnia (API Test)

**Como Funciona**

Para efetuar o teste na aplicação é recomendado a utilização da Insomnia. Disponibilizei o arquivo "Collection Insomnia Films.json", que deve ser importado ao Insomnia.

Executar a aplicação spring-boot (spring-boot:run).
No Insomnia, deve-se seguir a proposta abaixo:
- Login (criação do token)
- Iniciar partida
- Próxima jogada
- Enviar jogada (voltar ao item acima para nova jogada)
- Ranking
- Lougout

Para consultar a base de dados, acessar o endereço (username: sa - sem senha):
http://localhost:8080/h2-console/

**Usuários**

Há dois usuários pré-cadastrado no Sistema, ambos estão no arquivo data.sql.
- usuário: MARIA senha: 123456
- usuário: JOSE senha: 654321

**Trabalhos futuros**

Não consegui terminar o desafio com todos os requisitos solicitados.
Mas como eu gosto de aprender e exercitar vou manter esse repositório atualizado com o passar dos dias.

Há pontos de melhorias que precisam ser corrigidos, como na questão de segurança da senha, por exemplo. Além de outros pontos.