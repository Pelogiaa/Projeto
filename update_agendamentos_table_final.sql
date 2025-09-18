-- Script para atualizar a tabela tab_agendamentos com a estrutura final
-- Baseado nas alterações realizadas no backend

-- Conectar ao banco projeto_clinica_veterinaria
\c projeto_clinica_veterinaria;

-- Verificar estrutura atual da tabela antes da alteração
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_agendamentos' 
ORDER BY ordinal_position;

-- Garantir que a coluna data_atualizacao existe
ALTER TABLE tab_agendamentos ADD COLUMN IF NOT EXISTS data_atualizacao TIMESTAMP;

-- Atualizar data_atualizacao para registros existentes que não possuem
UPDATE tab_agendamentos 
SET data_atualizacao = data_cadastro 
WHERE data_atualizacao IS NULL;

-- Definir valor padrão para data_atualizacao
ALTER TABLE tab_agendamentos ALTER COLUMN data_atualizacao SET DEFAULT CURRENT_TIMESTAMP;

-- Verificar estrutura da tabela após a alteração
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_agendamentos' 
ORDER BY ordinal_position;

-- Mostrar dados da tabela após a alteração
SELECT * FROM tab_agendamentos ORDER BY id;

-- Comentários na tabela e colunas
COMMENT ON TABLE tab_agendamentos IS 'Tabela para armazenar agendamentos e consultas da clínica veterinária';
COMMENT ON COLUMN tab_agendamentos.id IS 'Identificador único do agendamento';
COMMENT ON COLUMN tab_agendamentos.data_agendamento IS 'Data do agendamento';
COMMENT ON COLUMN tab_agendamentos.hora_agendamento IS 'Hora do agendamento';
COMMENT ON COLUMN tab_agendamentos.id_cliente IS 'ID do cliente (chave estrangeira para tab_cliente)';
COMMENT ON COLUMN tab_agendamentos.id_animal IS 'ID do animal (chave estrangeira para tab_animais)';
COMMENT ON COLUMN tab_agendamentos.id_servico IS 'ID do serviço (chave estrangeira para tab_servicos)';
COMMENT ON COLUMN tab_agendamentos.observacoes IS 'Observações adicionais sobre o agendamento';
COMMENT ON COLUMN tab_agendamentos.status IS 'Status atual do agendamento';
COMMENT ON COLUMN tab_agendamentos.data_cadastro IS 'Data e hora do cadastro do agendamento';
COMMENT ON COLUMN tab_agendamentos.data_atualizacao IS 'Data e hora da última atualização do agendamento';

