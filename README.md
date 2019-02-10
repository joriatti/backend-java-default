Projeto com Backend Java Default utilizando Spring Boot.

Obs.:

1. Segurança habilitada com Spring Security.
    Para realizar o Login é necessário fazer um POST para:

    http://server:server_port/login
    Formato do JSON necessário, ex.:

    {
      "email": "admin@admin.com",
      "password": "123"
    }

    A requisição irá retornar o token JWT, exemplo:
    Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZmVsaXBlam9yaWF0dGlAZ21haWwuY29tIiwiZXhwIjoxNTUwNDE0MzA3fQ.KGS4hLXIc2o5tX-gyz6Uxdq7vt0HmZ9p4t8SN5lwRsHnAvSQX92ey65yVYJGlNCsWEf1kbfyghf4IX8euOa22g

    O Token deve então ser adicionado a todos so requests subsequentes que exigem usuário logado, para isto
    basta informar no cabeçalho da requisição: Authorization: Bearer XXX

2. Os dados de conexão com Banco dentre outras configurações estão no application.properties
3. O Hibernate está no modo auto update
4. O projeto está rodando testes integrados por padrão com Docker + MySQL
5. Para rodar os testes integrados: mvn clean install -Pintegration-test