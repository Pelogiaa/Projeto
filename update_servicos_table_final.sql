-- Script para atualizar a tabela tab_servicos com a estrutura final
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
WHERE table_name = 'tab_servicos' 
ORDER BY ordinal_position;

-- Garantir que a coluna data_atualizacao existe
ALTER TABLE tab_servicos ADD COLUMN IF NOT EXISTS data_atualizacao TIMESTAMP;

-- Atualizar data_atualizacao para registros existentes que não possuem
UPDATE tab_servicos 
SET data_atualizacao = data_cadastro 
WHERE data_atualizacao IS NULL;

-- Definir valor padrão para data_atualizacao
ALTER TABLE tab_servicos ALTER COLUMN data_atualizacao SET DEFAULT CURRENT_TIMESTAMP;

-- Verificar estrutura da tabela após a alteração
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_servicos' 
ORDER BY ordinal_position;

-- Mostrar dados da tabela após a alteração
SELECT * FROM tab_servicos ORDER BY id;

-- Comentários na tabela e colunas
COMMENT ON TABLE tab_servicos IS 'Tabela para armazenar os serviços oferecidos pela clínica veterinária';
COMMENT ON COLUMN tab_servicos.id IS 'Identificador único do serviço';
COMMENT ON COLUMN tab_servicos.nome IS 'Nome do serviço oferecido';
COMMENT ON COLUMN tab_servicos.preco IS 'Preço do serviço em reais (R$)';
COMMENT ON COLUMN tab_servicos.descricao IS 'Descrição detalhada do serviço';
COMMENT ON COLUMN tab_servicos.observacoes IS 'Observações adicionais sobre o serviço';
COMMENT ON COLUMN tab_servicos.status IS 'Status do serviço (Ativo/Inativo)';
COMMENT ON COLUMN tab_servicos.data_cadastro IS 'Data e hora de cadastro do serviço';
COMMENT ON COLUMN tab_servicos.data_atualizacao IS 'Data e hora da última atualização do serviço';

