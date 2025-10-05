-- Script para remover a coluna data_atualizacao da tabela tab_cliente
-- Remove apenas a coluna data_atualizacao da tabela tab_cliente

-- Conectar ao banco projeto_clinica_veterinaria
\c projeto_clinica_veterinaria;

-- Verificar estrutura atual da tabela antes da alteração
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_cliente' 
ORDER BY ordinal_position;

-- Remover data_atualizacao da tabela tab_cliente
ALTER TABLE tab_cliente DROP COLUMN IF EXISTS data_atualizacao;

-- Verificar estrutura da tabela após a alteração
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_cliente' 
ORDER BY ordinal_position;

-- Mostrar dados da tabela após a alteração
SELECT * FROM tab_cliente ORDER BY id LIMIT 5;

-- Comentários na tabela e colunas (atualizados)
COMMENT ON TABLE tab_cliente IS 'Tabela para armazenar os clientes da clínica veterinária';
COMMENT ON COLUMN tab_cliente.id IS 'Identificador único do cliente';
COMMENT ON COLUMN tab_cliente.nome IS 'Nome completo do cliente';
COMMENT ON COLUMN tab_cliente.email IS 'E-mail do cliente';
COMMENT ON COLUMN tab_cliente.telefone IS 'Telefone do cliente';
COMMENT ON COLUMN tab_cliente.cep IS 'CEP do cliente';
COMMENT ON COLUMN tab_cliente.cpf IS 'CPF do cliente';
COMMENT ON COLUMN tab_cliente.data_cadastro IS 'Data e hora de cadastro do cliente';











