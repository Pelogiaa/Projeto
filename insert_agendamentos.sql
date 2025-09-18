-- Inserir dados de exemplo na tabela tab_agendamentos
-- Usando os IDs que existem no banco de dados

INSERT INTO tab_agendamentos (data_agendamento, hora_agendamento, id_cliente, id_animal, id_servico, observacoes, status) VALUES
('2024-03-14', '09:00:00', 2, 6, 21, 'Consulta de rotina', 'Agendado'),
('2024-03-14', '10:30:00', 3, 9, 22, 'Vacinação anual', 'Agendado'),
('2024-03-15', '14:00:00', 4, 10, 23, 'Cirurgia de castração', 'Agendado'),
('2024-03-15', '16:00:00', 2, 7, 24, 'Banho e tosa', 'Agendado');

-- Verificar se os dados foram inseridos
SELECT 'Agendamentos inseridos com sucesso!' as status;
SELECT COUNT(*) as total_agendamentos FROM tab_agendamentos;

-- Consulta para verificar os dados inseridos com JOINs
SELECT 
    a.id,
    a.data_agendamento,
    a.hora_agendamento,
    c.nome as cliente,
    an.nome_animal as animal,
    s.nome as servico,
    s.preco,
    a.status
FROM tab_agendamentos a
JOIN tab_cliente c ON a.id_cliente = c.id
JOIN tab_animais an ON a.id_animal = an.id
JOIN tab_servicos s ON a.id_servico = s.id
ORDER BY a.data_agendamento, a.hora_agendamento;

