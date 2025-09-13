-- Script de inicialização de dados para o Spring Boot
-- Este arquivo é executado automaticamente quando a aplicação inicia

-- Criar tabela de serviços se não existir
CREATE TABLE IF NOT EXISTS tab_servicos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    observacoes TEXT,
    status VARCHAR(20) DEFAULT 'Ativo' CHECK (status IN ('Ativo', 'Inativo')),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inserir dados de exemplo (apenas se a tabela estiver vazia)
INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) 
SELECT 'Consulta Veterinária', 'Consultas', 'Atendimento clínico completo para cães e gatos', 120.00, 'Inclui exame físico completo e orientações', 'Ativo'
WHERE NOT EXISTS (SELECT 1 FROM tab_servicos WHERE nome = 'Consulta Veterinária');

INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) 
SELECT 'Vacinação', 'Prevenção', 'Aplicação de vacinas essenciais para pets', 80.00, 'Vacinas V8, V10, antirrábica e outras', 'Ativo'
WHERE NOT EXISTS (SELECT 1 FROM tab_servicos WHERE nome = 'Vacinação');

INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) 
SELECT 'Cirurgia de Castração', 'Cirurgias', 'Procedimento cirúrgico para castração de cães e gatos', 350.00, 'Inclui anestesia e pós-operatório', 'Ativo'
WHERE NOT EXISTS (SELECT 1 FROM tab_servicos WHERE nome = 'Cirurgia de Castração');

INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) 
SELECT 'Banho e Tosa', 'Estética', 'Serviço completo de higiene e estética animal', 60.00, 'Banho, secagem, tosa e corte de unhas', 'Ativo'
WHERE NOT EXISTS (SELECT 1 FROM tab_servicos WHERE nome = 'Banho e Tosa');

INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) 
SELECT 'Exames Laboratoriais', 'Diagnóstico', 'Coleta e análise de exames de sangue e urina', 150.00, 'Hemograma completo e bioquímica', 'Ativo'
WHERE NOT EXISTS (SELECT 1 FROM tab_servicos WHERE nome = 'Exames Laboratoriais');
