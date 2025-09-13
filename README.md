# 🐾 Clínica Veterinária

Sistema de gerenciamento para clínica veterinária com cadastro de clientes e animais.

## 📋 Funcionalidades

- ✅ Cadastro de clientes
- 🔄 Gerenciamento de dados de clientes (CRUD)
- 🗄️ Banco de dados PostgreSQL
- 📊 Estrutura preparada para cadastro de animais

## 🚀 Instalação e Configuração

### Pré-requisitos

- Python 3.8 ou superior
- PostgreSQL instalado e rodando
- Acesso de administrador ao PostgreSQL

### 1. Clone ou baixe o projeto

```bash
# Navegue até o diretório do projeto
cd ClinicaVeterinaria
```

### 2. Instale as dependências

```bash
pip install -r requirements.txt
```

### 3. Configure o banco de dados

1. **Configure as variáveis de ambiente:**
   - Copie o arquivo `env_example.txt` para `.env`
   - Edite o arquivo `.env` com suas configurações do PostgreSQL:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=projeto_clinica_veterinaria
DB_USER=postgres
DB_PASSWORD=sua_senha_aqui
```

1. **Execute o script de setup:**

```bash
python database.py
```

Este comando irá:

- Testar a conexão com o PostgreSQL
- Criar o banco de dados `projeto_clinica_veterinaria`
- Criar a tabela `tab_clientes`
- Inserir dados de exemplo

## 📊 Estrutura do Banco de Dados

### Tabela: `tab_clientes`

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | SERIAL | Chave primária (auto-incremento) |
| `nome` | VARCHAR(100) | Nome completo do cliente |
| `email` | VARCHAR(100) | Email do cliente (único) |
| `telefone` | VARCHAR(20) | Telefone do cliente |
| `endereco` | TEXT | Endereço completo |
| `cpf` | VARCHAR(14) | CPF do cliente (único) |
| `data_cadastro` | TIMESTAMP | Data de cadastro (automático) |
| `data_atualizacao` | TIMESTAMP | Data da última atualização (automático) |

## 💻 Como Usar

### Exemplo de uso básico

```python
from database import DatabaseManager, ClienteDAO

# Criar instâncias
db_manager = DatabaseManager()
cliente_dao = ClienteDAO(db_manager)

# Criar um novo cliente
cliente_dao.criar_cliente(
    nome="João Silva",
    email="joao@email.com",
    telefone="(11) 99999-9999",
    endereco="Rua das Flores, 123",
    cpf="123.456.789-00"
)

# Buscar todos os clientes
clientes = cliente_dao.buscar_todos_clientes()
for cliente in clientes:
    print(f"Cliente: {cliente['nome']} - {cliente['email']}")

# Buscar cliente por ID
cliente = cliente_dao.buscar_cliente_por_id(1)
if cliente:
    print(f"Cliente encontrado: {cliente['nome']}")

# Atualizar cliente
cliente_dao.atualizar_cliente(1, telefone="(11) 88888-8888")

# Deletar cliente
cliente_dao.deletar_cliente(1)
```

## 📁 Estrutura do Projeto

```text
ClinicaVeterinaria/
├── database.py              # Módulo principal de banco de dados
├── config.py                # Configurações da aplicação
├── setup_database.sql       # Script SQL de criação do banco
├── requirements.txt         # Dependências Python
├── env_example.txt          # Exemplo de configuração de ambiente
└── README.md               # Este arquivo
```

## 🔧 Comandos SQL Úteis

### Conectar ao banco via psql

```bash
psql -U postgres -d projeto_clinica_veterinaria
```

### Verificar tabelas

```sql
\dt
```

### Ver estrutura da tabela

```sql
\d tab_clientes
```

### Consultar clientes

```sql
SELECT * FROM tab_clientes;
```

## 🐛 Solução de Problemas

### Erro de conexão com PostgreSQL

1. Verifique se o PostgreSQL está rodando
2. Confirme as credenciais no arquivo `.env`
3. Teste a conexão manualmente via psql

### Erro de permissão

1. Certifique-se de que o usuário tem permissão para criar bancos
2. Execute como superusuário se necessário

### Erro de dependências

1. Atualize o pip: `pip install --upgrade pip`
2. Reinstale as dependências: `pip install -r requirements.txt`

## 📝 Próximos Passos

- [ ] Implementar cadastro de animais
- [ ] Criar interface web
- [ ] Adicionar sistema de agendamentos
- [ ] Implementar relatórios
- [ ] Adicionar autenticação de usuários

## 📞 Suporte

Para dúvidas ou problemas, verifique:

1. Os logs de erro no console
2. A documentação do PostgreSQL
3. A documentação do psycopg2

---

**Desenvolvido para o projeto Clínica Veterinária** 🐕🐱
