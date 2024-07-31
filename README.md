# Clínica Estética Abrantes

Este é um sistema de agendamentos de uma clínica estética. Nele, os atendentes conseguem criar e gerenciar agendamentos para clientes, e os clientes também conseguem se cadastrar como usuário para criar e gerenciar seus próprios agendamentos sem necessitar de ser atendido por uma pessoa. Esta versão utiliza de muitos dos conceitos do Clean Architecture e SOLID, possui um sistema de validações extremamente robusto e mais de 300 testes (totalizando mais de 400 cases) para garantir a integridade e confiabilidade do sistema.

## 📖 Documentação
Confira a documentação completa da API feita com Postman: [Documentação](https://abrantes.doc.mtpontes.com)

O Deploy na AWS pode ser acessado aqui: [Abrantes API](https://abrantes.mtpontes.com)

## 💡 Melhorias

- [ ] _Sistema de notificação:_ Os clientes serão notificados via email/Whatsapp quando seus agendamentos estiverem próximos, podendo confirmar ou cancelar a sua presença.
- [ ] _Sistema de pagamentos:_ Será adicionado um sistema de pagamentos utilizando de uma API externa
- [ ] _Validação de CEP:_ A validação de CEP será feita consultando a api ViaCep

## 🖥️ Sobre o desenvolvimento
<details><summary>Clique para expandir</summary>
<br>

Este projeto foi iniciado pelo meu colega [Ironildo Junior](https://github.com/JIJunior22), onde ele fez o levantamento de requisitos do projeto com a proprietária de uma clínica estética. Após as reuniões de requisitos, ele modelou e criou toda a base do sistema, definindo as entidades e seus mapeamentos, e o CRUD básico da API. 

A seguir, entrei para o projeto como colaborador, somando com qualidade de código em geral, implementando uma arquitetura MVC muito inspirada nos princípios do Clean Architecture, SOLID e Domain Driven Design. Implementei todas as regras de negócio, validações, algorítmos, querys, papéis, permissões, testes, entradas, saídas, remodelei entidades e documentei toda a API.

Fizemos reuniões entre nós e reuniões com o cliente, que resultaram em diversos ajustes, melhorias e novas ideias para o projeto. Cada um desenvolveu a sua própria versão do sistema por uma questão de exercício, mas ambos contribuem para o projeto do outro.

### 🧰 Tecnologias

- SpringBoot
- JWT
- PostGreSQL
- Postman

</details>


## 🚀 Como Rodar
<details><summary>Clique para expandir</summary>

### 📋 Pré-requisitos

- Java 17
- PostGreSQL 16 (também deve funcionar na versão 8 e superiores)

### 📦 Instalando

- Clone o projeto com o comando (ou baixe o zip pelo Github):

      git clone https://github.com/mtpontes/sistema-estetica-abrantes.git

- Entre no diretório principal do projeto e execute: 
    * Para Linux: 
    
          ./mvnw clean install -DskipTests


    * Para Windows: 
          
          mvnw.cmd clean install -DskipTests


    * Caso já possua Maven instalado: 
    
          mvn clean install -DskipTests

### 🔎 Detalhes

A aplicação está configurada para se conectar ao PostGre pela porta 5432.

### 🌍 Variáveis de ambiente:

#### Banco de dados
- `DB_USERNAME`: valor padrão **root**
- `DB_PASSWORD`: valor padrão **root**

#### Segurança
- `JWT_SECRET`: segredo utilizado na geração de um token JWT. Valor padrão **my-secret-key**

#### Usuário ADMIN
- `ADMIN_USERNAME`: login do usuário, valor padrão **root**
- `ADMIN_PASSWORD`: senha do usuário, valor padrão **rooT@34923**

##### Essas configurações também podem ser alteradas no `application.properties`.

### 🌐 Deploy

O app empacotado pode ser encontrado no diretório `/target` após seguir o procedimento de instalação.

Para executar a aplicação use o comando: 
        
    java -jar nome_do_jar

</details>


## 🐞 Problemas Comuns
<details><summary>Clique para expandir</summary>

### Erro de encoding ao fazer o build da aplicação
- **Solução**: Crie a variável de ambiente "MAVEN_OPTS" com o valor "-Dfile.encoding=UTF-8".

### Falha na conexão com o banco de dados PostGre
- **Solução**: Verifique se o PostGre está rodando na porta correta (5432) e se as credenciais de acesso (DB_USERNAME e DB_PASSWORD) estão corretas.

</details>



## 🤝 Colaboradores
<table>
  <tr>
    <td align="center">
      <a href="#" title="defina o titulo do link">
        <img src="https://avatars.githubusercontent.com/u/108276322?v=4" width="100px;" alt="Foto do Iuri Silva no GitHub"/><br>
        <sub>
          <b>Ironildo Junior</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
