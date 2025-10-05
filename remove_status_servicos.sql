-- Script para remover a coluna status da tabela tab_servicos
-- Remove apenas a coluna status da tabela tab_servicos

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

-- Remover status da tabela tab_servicos
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS status;

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

-- Comentários na tabela e colunas (atualizados)
COMMENT ON TABLE tab_servicos IS 'Tabela para armazenar os serviços oferecidos pela clínica veterinária';
COMMENT ON COLUMN tab_servicos.id IS 'Identificador único do serviço';
COMMENT ON COLUMN tab_servicos.nome IS 'Nome do serviço oferecido';
COMMENT ON COLUMN tab_servicos.preco IS 'Preço do serviço em reais (R$)';
COMMENT ON COLUMN tab_servicos.descricao IS 'Descrição detalhada do serviço';
COMMENT ON COLUMN tab_servicos.observacoes IS 'Observações adicionais sobre o serviço';
COMMENT ON COLUMN tab_servicos.data_cadastro IS 'Data e hora de cadastro do serviço';

