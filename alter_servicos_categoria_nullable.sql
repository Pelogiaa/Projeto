-- Script para tornar a coluna categoria opcional na tabela tab_servicos
-- Remove a restrição NOT NULL da coluna categoria

ALTER TABLE tab_servicos ALTER COLUMN categoria DROP NOT NULL;

