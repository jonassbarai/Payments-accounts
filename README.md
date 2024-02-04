
# API de contas de pagamento

Este é um projeto Spring Backend que oferece serviços para contas de pagamento entre usuários lojistas e comuns e também operações com a própria conta como saques e depósitos.

## Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring JPA com hibernate
- Documentação via swagger
- Spring security e JWT
- Testes unitários e de integração (Junit e mockito)

## Deploy
Foi feito o deploy via dockerfile na plataforma render, acesse o link: https://api-payments-accounts.onrender.com/swagger-ui/index.html#/



## Observações de uso
Alguns endpoints são bloqueados, como /transactions outros são livres, como o /login.

Para gerar o token de autenticação primeiramente você precisará criar um usuário válido e utilizá-lo para fazer o login.

Lembre-se que o usuário precisa ter um cpf/cnpj válido e e um email único, esse email será utilizado no login juntamente com a senha.

Após isso utilizar o token gerado no login para autenticação na plataforma de sua preferência.

## Regras de negócio
- usuários lojistas não podem fazer transações somente receber
- o cnpj/cpf precisa ser válido e é unico no sistema
- o email é unico
- o email é utilizado como login do usuário

## Autores

- [@jonassbarai](https://github.com/jonassbarai)


