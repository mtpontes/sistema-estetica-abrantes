# Clínica Estética Abrantes

Este é um sistema de agendamentos de uma clínica estética. Nele, os atendentes conseguem criar e gerenciar agendamentos para clientes, e os clientes também conseguem se cadastrar como usuário para criar e gerenciar seus próprios agendamentos sem necessitar de ser atendido por uma pessoa. Nesta clínica os atendimentos são realizados por uma única esteticista, a proprietária. Esta versão utiliza de muitos dos conceitos do Clean Architecture e SOLID, possui um sistema de validações extremamente robusto e mais de 300 testes (totalizando mais de 400 cases) para garantir a integridade e confiabilidade do sistema.

## Documentação
- [Confira a documentação completa da API feita com Postman](https://abrantes.doc.mtpontes.com)

- [Acesse o deploy da aplicação no Heroku](http://abrantes.mtpontes.com)

## Tecnologias

- SpringBoot
- JWT
- PostGreSQL
- Postman


<details>
  <summary><h2>Sobre o desenvolvimento</h2></summary>

> **IMPORTANT**
> 
> Este projeto foi desenvolvido exclusivamente para fins de estudo. O cenário simula um sistema real, mas nenhuma informação sensível, confidencial ou de produção foi utilizada. O "cliente" mencionado foi um colaborador voluntário, que participou da definição do cenário como parte de um exercício técnico. Todo o conteúdo é fictício ou foi compartilhado com consentimento explícito.


Este projeto foi iniciado pelo meu colega [Ironildo Junior](https://github.com/JIJunior22), onde ele fez o levantamento de requisitos do projeto com a proprietária de uma clínica estética. Após as reuniões de requisitos, ele modelou e criou toda a base do sistema, definindo as entidades e seus mapeamentos, e o CRUD básico da API. 

A seguir, entrei para o projeto como colaborador, somando com qualidade de código em geral, implementando uma arquitetura MVC muito inspirada nos princípios do Clean Architecture, SOLID e Domain Driven Design. Implementei todas as regras de negócio, validações, algorítmos, querys, papéis, permissões, testes, entradas, saídas, remodelei entidades e documentei toda a API.

Fizemos reuniões entre nós e reuniões com o cliente, que resultaram em diversos ajustes, melhorias e novas ideias para o projeto. Cada um desenvolveu a sua própria versão do sistema por uma questão de exercício, mas ambos contribuem para o projeto do outro.

Esse projeto me trouxe diversos insights de recursos que eu poderia implementar nele, mas como ele foi criado desenvolvido com base nas necessidades deste cliente, tem muita coisa que não faria sentido implementar, então, fica pra um próximo projeto :)

</details>



</details>


<details>
  <summary><h2>Como rodar localmente</h2></summary>

### Pré-requisitos

- Docker
- Docker Compose

### Detalhes

Existem duas maneiras de rodar a aplicação, através do arquivo docker-compose.yml a aplicação rodará com perfil de produção, atráves do docker-compose-demo.yml será o perfil de demonstração. Para rodar com o docker-compose.yml é necessário configurar um provedor de email. 

No docker-compose-demo.yml é levantado um container [Mailhog](https://github.com/mailhog/MailHog), uma aplicação que simula um provedor de email. O Mailhog utiliza a porta 1025 para SMTP e 8025 para uma interface web onde, você pode visualizar os emails enviados com ele. Acesse http://localhost:8025 para visualizar os emails.

### Variáveis de ambiente:

#### Produção
- Na raiz do projeto localize o diretório "env-demo"
- Faça uma cópia desse diretório e renomeie-o para "env"
- Abra os arquivos app.env e db.env e preencha os valores das variáveis de ambiente

#### Banco de dados
- `DB_USERNAME`: username do banco de dados
- `DB_PASSWORD`: senha do banco de dados

#### Segurança
- `JWT_SECRET`: segredo utilizado na geração de um token JWT

#### Usuário ADMIN
- `ADMIN_USERNAME`: login do admin padrão do sistema
- `ADMIN_PASSWORD`: senha do admin padrão do sistema

#### Outras
- `SPRING_MAIL_...`: configurações do provedor de email

##### Essas configurações também podem ser alteradas no `application.properties`.

### Deploy

Clone o projeto ou baixe o zip pelo Github:

    git clone https://github.com/mtpontes/sistema-estetica-abrantes.git

Levante os containers: 
        
    docker-compose -f docker-compose-demo.yml up --build

</details>

<!--
<details>
  <summary><h2>Melhorias</h2></summary>

- [ ] _Sistema de pagamentos_: Será adicionado um sistema de pagamentos utilizando de uma API externa
- [ ] _Relatórios_: Será possível gerar relatórios de atendimentos e clientes
- [ ] _Sistema de notificação_: Os clientes serão notificados via email quando seus agendamento estiver próximo.
- [ ] _Alta cobertura de testes de integração_: Os atuais testes da API são todos testes de unidade. Irei implementar testes de integração com banco de dados e teste end-to-end, com a intenção de fazer a maior cobertura possível
-->

</details>


## Colaboradores
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/JIJunior22" title="defina o titulo do link">
        <img src="https://avatars.githubusercontent.com/u/108276322?v=4" width="100px;" alt="Foto do Iuri Silva no GitHub"/><br>
        <sub>
          <b>Ironildo Junior</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
