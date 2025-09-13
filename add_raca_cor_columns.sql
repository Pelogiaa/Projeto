-- Adicionar colunas raça e cor à tabela tab_animais
ALTER TABLE tab_animais 
ADD COLUMN raca VARCHAR(50),
ADD COLUMN cor VARCHAR(30);

-- Comentários para documentar as novas colunas
COMMENT ON COLUMN tab_animais.raca IS 'Raça do animal (opcional)';
COMMENT ON COLUMN tab_animais.cor IS 'Cor do animal (opcional)';

-- Verificar se as colunas foram adicionadas
SELECT column_name, data_type, character_maximum_length, is_nullable
FROM information_schema.columns 
WHERE table_name = 'tab_animais' 
AND column_name IN ('raca', 'cor');















