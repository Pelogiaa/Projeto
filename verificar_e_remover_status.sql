-- Script para verificar e remover a coluna status da tabela tab_servicos

-- Conectar ao banco
\c projeto_clinica_veterinaria;

-- Verificar se a coluna status existe
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_servicos' 
AND column_name = 'status';

-- Se a coluna existir, removê-la
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS status;

-- Verificar estrutura final da tabela
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_servicos' 
ORDER BY ordinal_position;
