-- Script para remover a coluna data_atualizacao de todas as tabelas
-- Remove a coluna data_atualizacao das tabelas: tab_cliente, tab_animais, tab_servicos e tab_agendamentos

-- Remover data_atualizacao da tabela tab_cliente
ALTER TABLE tab_cliente DROP COLUMN IF EXISTS data_atualizacao;

-- Remover data_atualizacao da tabela tab_animais
ALTER TABLE tab_animais DROP COLUMN IF EXISTS data_atualizacao;

-- Remover data_atualizacao da tabela tab_servicos
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS data_atualizacao;

-- Remover data_atualizacao da tabela tab_agendamentos
ALTER TABLE tab_agendamentos DROP COLUMN IF EXISTS data_atualizacao;

