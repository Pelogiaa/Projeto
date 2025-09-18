-- Script para atualizar o status dos serviços para ATIVO
-- Verificar o status atual dos serviços
SELECT id, nome, status FROM tab_servicos;

-- Atualizar todos os serviços para status ATIVO
UPDATE tab_servicos SET status = 'ATIVO' WHERE status IS NULL OR status != 'ATIVO';

-- Verificar o resultado
SELECT id, nome, status FROM tab_servicos;

