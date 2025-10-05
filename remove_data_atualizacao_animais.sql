-- Script para remover a coluna data_atualizacao da tabela tab_animais
-- Remove apenas a coluna data_atualizacao da tabela tab_animais

-- Conectar ao banco projeto_clinica_veterinaria
\c projeto_clinica_veterinaria;

-- Verificar estrutura atual da tabela antes da alteração
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_animais' 
ORDER BY ordinal_position;

-- Remover data_atualizacao da tabela tab_animais
ALTER TABLE tab_animais DROP COLUMN IF EXISTS data_atualizacao;

-- Verificar estrutura da tabela após a alteração
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default
FROM information_schema.columns 
WHERE table_name = 'tab_animais' 
ORDER BY ordinal_position;

-- Mostrar dados da tabela após a alteração
SELECT * FROM tab_animais ORDER BY id LIMIT 5;

-- Comentários na tabela e colunas (atualizados)
COMMENT ON TABLE tab_animais IS 'Tabela para armazenar os animais dos clientes da clínica veterinária';
COMMENT ON COLUMN tab_animais.id IS 'Identificador único do animal';
COMMENT ON COLUMN tab_animais.id_cliente IS 'ID do cliente proprietário do animal';
COMMENT ON COLUMN tab_animais.nome_animal IS 'Nome do animal';
COMMENT ON COLUMN tab_animais.idade IS 'Idade do animal em anos';
COMMENT ON COLUMN tab_animais.tipo_animal IS 'Tipo do animal (cão, gato, etc.)';
COMMENT ON COLUMN tab_animais.sexo IS 'Sexo do animal (Macho/Fêmea)';
COMMENT ON COLUMN tab_animais.peso IS 'Peso do animal em kg';
COMMENT ON COLUMN tab_animais.data_cadastro IS 'Data e hora de cadastro do animal';
COMMENT ON COLUMN tab_animais.cor IS 'Cor do animal';
COMMENT ON COLUMN tab_animais.raca IS 'Raça do animal';











