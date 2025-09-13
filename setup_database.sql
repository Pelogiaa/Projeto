-- =====================================================
-- SCRIPT DE CRIAÇÃO DO BANCO DE DADOS
-- Projeto: Clínica Veterinária
-- =====================================================

-- Criar o banco de dados
CREATE DATABASE projeto_clinica_veterinaria
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Conectar ao banco criado
\c projeto_clinica_veterinaria;

-- Criar a tabela de clientes
CREATE TABLE tab_cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    endereco TEXT NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar índices para melhorar performance
CREATE INDEX idx_cliente_cpf ON tab_cliente(cpf);
CREATE INDEX idx_cliente_email ON tab_cliente(email);
CREATE INDEX idx_cliente_nome ON tab_cliente(nome);

-- Comentários nas colunas para documentação
COMMENT ON TABLE tab_cliente IS 'Tabela para armazenar dados dos clientes da clínica veterinária';
COMMENT ON COLUMN tab_cliente.id IS 'Identificador único do cliente';
COMMENT ON COLUMN tab_cliente.nome IS 'Nome completo do cliente';
COMMENT ON COLUMN tab_cliente.email IS 'Endereço de email do cliente';
COMMENT ON COLUMN tab_cliente.telefone IS 'Número de telefone do cliente';
COMMENT ON COLUMN tab_cliente.endereco IS 'Endereço completo do cliente';
COMMENT ON COLUMN tab_cliente.cpf IS 'CPF do cliente (formato: 000.000.000-00)';
COMMENT ON COLUMN tab_cliente.data_cadastro IS 'Data e hora do cadastro do cliente';
COMMENT ON COLUMN tab_cliente.data_atualizacao IS 'Data e hora da última atualização do cliente';

-- Inserir alguns dados de exemplo (opcional)
INSERT INTO tab_cliente (nome, email, telefone, endereco, cpf) VALUES
('João Silva', 'joao.silva@email.com', '(11) 99999-9999', 'Rua das Flores, 123 - São Paulo/SP', '123.456.789-00'),
('Maria Santos', 'maria.santos@email.com', '(11) 88888-8888', 'Av. Paulista, 456 - São Paulo/SP', '987.654.321-00'),
('Pedro Oliveira', 'pedro.oliveira@email.com', '(11) 77777-7777', 'Rua Augusta, 789 - São Paulo/SP', '456.789.123-00');

-- Criar a tabela de animais
CREATE TABLE tab_animais (
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER NOT NULL,
    nome_animal VARCHAR(100) NOT NULL,
    idade INTEGER NOT NULL,
    tipo_animal VARCHAR(50) NOT NULL,
    sexo VARCHAR(10) NOT NULL CHECK (sexo IN ('Macho', 'Fêmea')),
    peso DECIMAL(5,2) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_animais_cliente FOREIGN KEY (id_cliente) REFERENCES tab_cliente(id) ON DELETE CASCADE
);

-- Criar índices para melhorar performance
CREATE INDEX idx_animais_cliente ON tab_animais(id_cliente);
CREATE INDEX idx_animais_nome ON tab_animais(nome_animal);
CREATE INDEX idx_animais_tipo ON tab_animais(tipo_animal);

-- Comentários nas colunas para documentação
COMMENT ON TABLE tab_animais IS 'Tabela para armazenar dados dos animais da clínica veterinária';
COMMENT ON COLUMN tab_animais.id IS 'Identificador único do animal';
COMMENT ON COLUMN tab_animais.id_cliente IS 'ID do cliente proprietário do animal (chave estrangeira)';
COMMENT ON COLUMN tab_animais.nome_animal IS 'Nome do animal';
COMMENT ON COLUMN tab_animais.idade IS 'Idade do animal em anos';
COMMENT ON COLUMN tab_animais.tipo_animal IS 'Tipo/espécie do animal (ex: Cão, Gato, etc.)';
COMMENT ON COLUMN tab_animais.sexo IS 'Sexo do animal (Macho ou Fêmea)';
COMMENT ON COLUMN tab_animais.peso IS 'Peso do animal em kg';
COMMENT ON COLUMN tab_animais.data_cadastro IS 'Data e hora do cadastro do animal';
COMMENT ON COLUMN tab_animais.data_atualizacao IS 'Data e hora da última atualização do animal';

-- Inserir alguns dados de exemplo (opcional)
INSERT INTO tab_animais (id_cliente, nome_animal, idade, tipo_animal, sexo, peso) VALUES
(1, 'Rex', 3, 'Cão', 'Macho', 25.5),
(1, 'Luna', 2, 'Gato', 'Fêmea', 4.2),
(2, 'Thor', 5, 'Cão', 'Macho', 30.0),
(2, 'Mimi', 1, 'Gato', 'Fêmea', 3.8),
(3, 'Bella', 4, 'Cão', 'Fêmea', 22.3);

-- =====================================================
-- ATUALIZAÇÃO: TROCAR ENDEREÇO POR CEP
-- =====================================================

-- Adicionar coluna CEP
ALTER TABLE tab_cliente ADD COLUMN cep VARCHAR(9);

-- Atualizar dados existentes (exemplo - substituir por CEPs reais)
UPDATE tab_cliente SET cep = '01234-567' WHERE id = 1;
UPDATE tab_cliente SET cep = '04567-890' WHERE id = 2;
UPDATE tab_cliente SET cep = '07890-123' WHERE id = 3;

-- Tornar CEP obrigatório
ALTER TABLE tab_cliente ALTER COLUMN cep SET NOT NULL;

-- Remover coluna endereco
ALTER TABLE tab_cliente DROP COLUMN endereco;

-- Atualizar comentários
COMMENT ON COLUMN tab_cliente.cep IS 'CEP do cliente (formato: 00000-000)';

-- Verificar se as tabelas foram criadas corretamente
SELECT 'Tabela tab_cliente criada com sucesso!' as status;
SELECT COUNT(*) as total_clientes FROM tab_cliente;

SELECT 'Tabela tab_animais criada com sucesso!' as status;
SELECT COUNT(*) as total_animais FROM tab_animais;

