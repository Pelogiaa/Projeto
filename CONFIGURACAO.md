# 🏥 Configuração - Clínica Veterinária

## 📋 Pré-requisitos

### Software Necessário

- **Java 17+** - [Download Oracle JDK](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://adoptium.net/)
- **Node.js 16+** - [Download Node.js](https://nodejs.org/)
- **PostgreSQL 12+** - [Download PostgreSQL](https://www.postgresql.org/download/)
- **Maven** (opcional - o projeto usa Maven Wrapper)

### Verificar Instalações

```bash
# Verificar Java
java -version

# Verificar Node.js
node --version
npm --version

# Verificar PostgreSQL
psql --version
```

## 🗄️ Configuração do Banco de Dados

### 1. Instalar PostgreSQL

- Instale o PostgreSQL seguindo as instruções do site oficial
- Anote a senha do usuário `postgres` durante a instalação

### 2. Criar Banco de Dados

```sql
-- Conectar ao PostgreSQL como superusuário
psql -U postgres

-- Criar banco de dados
CREATE DATABASE projeto_clinica_veterinaria;

-- Verificar se foi criado
\l
```

### 3. Configurar Conexão

As configurações de banco estão em:

- `src/main/resources/application.properties` (produção)
- `src/main/resources/application-dev.properties` (desenvolvimento)

**Configuração padrão:**

- Host: `localhost`
- Porta: `5432`
- Banco: `projeto_clinica_veterinaria`
- Usuário: `postgres`
- Senha: `5720`

> ⚠️ **Importante**: Altere a senha no arquivo de configuração para a senha real do seu PostgreSQL!

## 🚀 Como Executar

### Opção 1: Scripts Automáticos (Recomendado)

#### Desenvolvimento Completo

```bash
# Iniciar Backend + Frontend simultaneamente
start-both-dev.bat
```

#### Apenas Backend

```bash
# Iniciar apenas o backend
start-backend-dev.bat
```

#### Apenas Frontend

```bash
# Iniciar apenas o frontend
start-frontend-dev.bat
```

### Opção 2: Comandos Manuais

#### Backend (Spring Boot)

```bash
# Compilar e executar
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run

# Ou com perfil específico
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Frontend (React)

```bash
# Instalar dependências (primeira vez)
npm install

# Iniciar aplicação
npm start
```

## 🌐 URLs de Acesso

- **Frontend**: <http://localhost:3000>
- **Backend API**: <http://localhost:8080/api>
- **Backend Swagger**: <http://localhost:8080/swagger-ui.html> (se configurado)

## 📁 Estrutura do Projeto

```text
ClinicaVeterinaria/
├── src/
│   ├── main/java/          # Código Java (Backend)
│   └── main/resources/     # Configurações
├── src/                    # Código React (Frontend)
├── public/                 # Arquivos estáticos
├── target/                 # Build do Java
├── build/                  # Build do React
└── node_modules/           # Dependências Node.js
```

## ⚙️ Configurações Avançadas

### Perfis do Spring Boot

- **Padrão**: `application.properties`
- **Desenvolvimento**: `application-dev.properties`
- **Local**: `application-local.properties`

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=projeto_clinica_veterinaria
DB_USER=postgres
DB_PASSWORD=sua_senha_aqui
```

## 🔧 Solução de Problemas

### Erro de Conexão com Banco

1. Verifique se o PostgreSQL está rodando
2. Confirme as credenciais no arquivo de configuração
3. Teste a conexão: `psql -U postgres -d projeto_clinica_veterinaria`

### Erro de Porta em Uso

- Backend: Altere `server.port` em `application.properties`
- Frontend: Altere `PORT` no script `package.json`

### Erro de Dependências

```bash
# Limpar e reinstalar dependências
npm cache clean --force
rm -rf node_modules package-lock.json
npm install

# Para Maven
.\mvnw.cmd clean
.\mvnw.cmd compile
```

## 📝 Logs e Debug

### Habilitar Logs Detalhados

No arquivo `application.properties`:

```properties
logging.level.com.clinicaveterinaria=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

### Verificar Status da Aplicação

- Backend: <http://localhost:8080/actuator/health>
- Logs: Console onde a aplicação foi iniciada

## 🆘 Suporte

Se encontrar problemas:

1. Verifique os logs da aplicação
2. Confirme se todos os pré-requisitos estão instalados
3. Teste a conexão com o banco de dados
4. Verifique se as portas estão livres

---

### Desenvolvido com ❤️ para Clínica Veterinária
