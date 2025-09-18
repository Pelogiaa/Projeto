-- Script para remover colunas da tabela tab_servicos
-- Remove: categoria, data_cadastro, descricao, observacoes, status, data_atualizacao
-- Mantém: id, nome, preco

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

-- Remover as colunas especificadas
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS categoria;
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS data_cadastro;
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS descricao;
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS observacoes;
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS status;
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS data_atualizacao;

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

-- Comentários na tabela e colunas restantes
COMMENT ON TABLE tab_servicos IS 'Tabela para armazenar os serviços oferecidos pela clínica veterinária';
COMMENT ON COLUMN tab_servicos.id IS 'Identificador único do serviço';
COMMENT ON COLUMN tab_servicos.nome IS 'Nome do serviço oferecido';
COMMENT ON COLUMN tab_servicos.preco IS 'Preço do serviço em reais (R$)';
