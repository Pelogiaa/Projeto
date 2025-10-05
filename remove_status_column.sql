-- Script simples para remover a coluna status da tabela tab_servicos
ALTER TABLE tab_servicos DROP COLUMN IF EXISTS status;
