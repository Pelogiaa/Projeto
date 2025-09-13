-- Script para criar tabela tab_servicos no banco projeto_clinica_veterinaria
-- Baseado na estrutura do modal de serviços

-- Conectar ao banco projeto_clinica_veterinaria
\c projeto_clinica_veterinaria;

-- Criar tabela tab_servicos
CREATE TABLE IF NOT EXISTS tab_servicos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    observacoes TEXT,
    status VARCHAR(20) DEFAULT 'Ativo' CHECK (status IN ('Ativo', 'Inativo')),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comentários na tabela e colunas
COMMENT ON TABLE tab_servicos IS 'Tabela para armazenar os serviços oferecidos pela clínica veterinária';
COMMENT ON COLUMN tab_servicos.id IS 'Identificador único do serviço';
COMMENT ON COLUMN tab_servicos.nome IS 'Nome do serviço oferecido';
COMMENT ON COLUMN tab_servicos.preco IS 'Preço do serviço em reais (R$)';
COMMENT ON COLUMN tab_servicos.observacoes IS 'Observações adicionais sobre o serviço';
COMMENT ON COLUMN tab_servicos.status IS 'Status do serviço (Ativo/Inativo)';
COMMENT ON COLUMN tab_servicos.data_cadastro IS 'Data e hora de cadastro do serviço';
COMMENT ON COLUMN tab_servicos.data_atualizacao IS 'Data e hora da última atualização do serviço';

-- Inserir dados de exemplo
INSERT INTO tab_servicos (nome, preco, observacoes, status) VALUES
('Banho e Tosa', 80.00, 'Serviço completo de higiene e estética animal', 'Ativo'),
('Cirurgia de Castração', 350.00, 'Procedimento cirúrgico para castração de cães e gatos', 'Ativo'),
('Consulta Veterinária', 120.00, 'Atendimento clínico completo para cães e gatos', 'Ativo'),
('Exames Laboratoriais', 150.00, 'Coleta e análise de exames de sangue e urina', 'Ativo'),
('Vacinação', 80.00, 'Aplicação de vacinas essenciais para pets', 'Ativo')
ON CONFLICT DO NOTHING;

-- Verificar se a tabela foi criada corretamente
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_servicos' 
ORDER BY ordinal_position;

-- Mostrar dados inseridos
SELECT * FROM tab_servicos ORDER BY nome;
