# Programa Task Flow

## Techlabs Software House

### Dev group 4

O programa Task flow, da *Techlabs Software House*, consiste de um sistema de organização de tarefas pessoais.
Focando na função de simplificar a distribuição e organização de tarefas. Com funções como filtragem e alteração de tarefas pós criação.
O programa é focado na simplicidade, com uma interface feita completamente em Java e integrado com Banco de dados pessoal, para individualização do sistema.

### SM: Henrique Rodrigues

### PO: Arthur Vargas

Dev Team: Vitor Harthmann, Bruno Soares, Otavio Martins, Gabriel Brandão

---

## Tecnologias utilizadas

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- ![Swing](https://img.shields.io/badge/Swing-0081CB?style=for-the-badge&logo=java&logoColor=white)
- ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
- ![JDBC](https://img.shields.io/badge/JDBC-07405E?style=for-the-badge&logo=java&logoColor=white)
- [jBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt) (hash seguro de senhas)

---

## Estrutura do projeto

```text
src/br/ulbra/
 ├─ dao/         → Classes DAO (AbstractDAO, UsuarioDAO, ClienteDAO)
 ├─ controller/  → Lógica de controle (UsuarioController, ClienteController)
 ├─ model/       → Modelos (Usuario.java, Cliente.java)
 ├─ view/        → Interfaces gráficas (LoginView, MenuPrincipalView, UsuarioView, ClienteView)
 └─ img/         → Ícones
```

---

## Banco de Dados

``` sql
CREATE TABLE usuario(
    idusuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL UNIQUE,
    email VARCHAR(100),
    cargo VARCHAR(50) ,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE tarefa(
    idtarefa INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL UNIQUE,
    descricao text NULL,
    prioridade VARCHAR(20),
    status VARCHAR(20) NOT NULL UNIQUE,
    prazo date,
    idusuario INT(11) SECOND KEY
);

```

---

## 👤 Criando o primeiro usuário (ADM)

### Opção 1 — Gerar hash manual
Use esta classe para gerar o hash:
```java
import org.mindrot.jbcrypt.BCrypt;
public class HashGenerator {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("admin123", BCrypt.gensalt()));
    }
}
```
Depois insira no banco:
```sql
INSERT INTO usuario (login, senha, nome, ativo)
VALUES ('adm', '$2a$10$HASHGERADO...', 'Administrador', 1);
```

### Opção 2 — Criar automaticamente no código
No `LoginView`, antes de abrir a tela de login, verifique se há usuários e crie o **adm/admin123** caso não exista.

---

## ▶️ Execução
1. Rode o projeto (classe `CadastroUsuario.view` é a principal).
2. Faça login:
   - Usuário: `adm`
   - Senha: `admin123`
3. Após autenticação, o sistema abre o **Main.view**.

---

## 🔒 Segurança
- Senhas são armazenadas com **jBCrypt**, nunca em texto puro.
- Recomenda-se usar um usuário MySQL dedicado em produção:
```sql
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'senhaSegura';
GRANT ALL PRIVILEGES ON cruddb1.* TO 'appuser'@'localhost';
```

---
## 👨‍🏫 Sobre
Este projeto foi desenvolvido para fins **educacionais**, como exemplo de CRUD com **Java + MySQL + Swing**, servindo de base para práticas de programação fullstack. Além disso, utiliza da collaboração entre alunos, ensinando-os as dificuldes do mercado de trabalho e como o ambiente de trabalho funciona.
