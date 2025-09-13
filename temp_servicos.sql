-- Criar tabela de serviÃ§os
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

-- Inserir dados de exemplo
INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) VALUES
('Consulta VeterinÃ¡ria', 'Consultas', 'Atendimento clÃ­nico completo para cÃ£es e gatos', 120.00, 'Inclui exame fÃ­sico completo e orientaÃ§Ãµes', 'Ativo'),
('VacinaÃ§Ã£o', 'PrevenÃ§Ã£o', 'AplicaÃ§Ã£o de vacinas essenciais para pets', 80.00, 'Vacinas V8, V10, antirrÃ¡bica e outras', 'Ativo'),
('Cirurgia de CastraÃ§Ã£o', 'Cirurgias', 'Procedimento cirÃºrgico para castraÃ§Ã£o de cÃ£es e gatos', 350.00, 'Inclui anestesia e pÃ³s-operatÃ³rio', 'Ativo'),
('Banho e Tosa', 'EstÃ©tica', 'ServiÃ§o completo de higiene e estÃ©tica animal', 60.00, 'Banho, secagem, tosa e corte de unhas', 'Ativo'),
('Exames Laboratoriais', 'DiagnÃ³stico', 'Coleta e anÃ¡lise de exames de sangue e urina', 150.00, 'Hemograma completo e bioquÃ­mica', 'Ativo')
ON CONFLICT DO NOTHING;
