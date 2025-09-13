-- Corrigir problemas de schema do banco de dados

-- 1. Remover coluna endereco se existir
ALTER TABLE tab_cliente DROP COLUMN IF EXISTS endereco;

-- 2. Adicionar coluna cep se não existir
ALTER TABLE tab_cliente ADD COLUMN IF NOT EXISTS cep VARCHAR(10);

-- 3. Adicionar colunas raca e cor na tabela tab_animais
ALTER TABLE tab_animais ADD COLUMN IF NOT EXISTS raca VARCHAR(50);
ALTER TABLE tab_animais ADD COLUMN IF NOT EXISTS cor VARCHAR(30);

-- 4. Verificar estrutura das tabelas
\d tab_cliente
\d tab_animais















