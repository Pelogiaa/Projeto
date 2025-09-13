-- Inserir serviços de exemplo
INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) VALUES
('Consulta Veterinária', 'Consultas', 'Atendimento clínico completo para cães e gatos', 120.00, 'Inclui exame físico completo e orientações', 'Ativo'),
('Vacinação', 'Prevenção', 'Aplicação de vacinas essenciais para pets', 80.00, 'Vacinas V8, V10, antirrábica e outras', 'Ativo'),
('Cirurgia de Castração', 'Cirurgias', 'Procedimento cirúrgico para castração de cães e gatos', 350.00, 'Inclui anestesia e pós-operatório', 'Ativo'),
('Banho e Tosa', 'Estética', 'Serviço completo de higiene e estética animal', 60.00, 'Banho, secagem, tosa e corte de unhas', 'Ativo'),
('Exames Laboratoriais', 'Diagnóstico', 'Coleta e análise de exames de sangue e urina', 150.00, 'Hemograma completo e bioquímica', 'Ativo')
ON CONFLICT (nome) DO NOTHING;
