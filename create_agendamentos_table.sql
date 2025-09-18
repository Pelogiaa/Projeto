-- =====================================================
-- SCRIPT DE CRIAÇÃO DA TABELA DE AGENDAMENTOS
-- Projeto: Clínica Veterinária
-- =====================================================

-- Conectar ao banco de dados
\c projeto_clinica_veterinaria;

-- Criar a tabela de agendamentos
CREATE TABLE tab_agendamentos (
    id SERIAL PRIMARY KEY,
    data_agendamento DATE NOT NULL,
    hora_agendamento TIME NOT NULL,
    id_cliente INTEGER NOT NULL,
    id_animal INTEGER NOT NULL,
    id_servico INTEGER NOT NULL,
    observacoes TEXT,
    status VARCHAR(20) DEFAULT 'Agendado' CHECK (status IN ('Agendado', 'Confirmado', 'Em Andamento', 'Concluído', 'Cancelado', 'Reagendado')),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Chaves estrangeiras
    CONSTRAINT fk_agendamentos_cliente FOREIGN KEY (id_cliente) REFERENCES tab_cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_agendamentos_animal FOREIGN KEY (id_animal) REFERENCES tab_animais(id) ON DELETE CASCADE,
    CONSTRAINT fk_agendamentos_servico FOREIGN KEY (id_servico) REFERENCES tab_servicos(id) ON DELETE RESTRICT
    
    -- A validação de que o animal pertence ao cliente será feita na aplicação
);

-- Criar índices para melhorar performance
CREATE INDEX idx_agendamentos_data ON tab_agendamentos(data_agendamento);
CREATE INDEX idx_agendamentos_hora ON tab_agendamentos(hora_agendamento);
CREATE INDEX idx_agendamentos_cliente ON tab_agendamentos(id_cliente);
CREATE INDEX idx_agendamentos_animal ON tab_agendamentos(id_animal);
CREATE INDEX idx_agendamentos_servico ON tab_agendamentos(id_servico);
CREATE INDEX idx_agendamentos_status ON tab_agendamentos(status);
CREATE INDEX idx_agendamentos_data_hora ON tab_agendamentos(data_agendamento, hora_agendamento);

-- Comentários nas colunas para documentação
COMMENT ON TABLE tab_agendamentos IS 'Tabela para armazenar agendamentos e consultas da clínica veterinária';
COMMENT ON COLUMN tab_agendamentos.id IS 'Identificador único do agendamento';
COMMENT ON COLUMN tab_agendamentos.data_agendamento IS 'Data do agendamento';
COMMENT ON COLUMN tab_agendamentos.hora_agendamento IS 'Hora do agendamento';
COMMENT ON COLUMN tab_agendamentos.id_cliente IS 'ID do cliente (chave estrangeira para tab_cliente)';
COMMENT ON COLUMN tab_agendamentos.id_animal IS 'ID do animal (chave estrangeira para tab_animais)';
COMMENT ON COLUMN tab_agendamentos.id_servico IS 'ID do serviço (chave estrangeira para tab_servicos)';
COMMENT ON COLUMN tab_agendamentos.observacoes IS 'Observações adicionais sobre o agendamento';
COMMENT ON COLUMN tab_agendamentos.status IS 'Status atual do agendamento';
COMMENT ON COLUMN tab_agendamentos.data_cadastro IS 'Data e hora do cadastro do agendamento';
COMMENT ON COLUMN tab_agendamentos.data_atualizacao IS 'Data e hora da última atualização do agendamento';

-- Inserir dados de exemplo baseados na imagem
INSERT INTO tab_agendamentos (data_agendamento, hora_agendamento, id_cliente, id_animal, id_servico, observacoes, status) VALUES
('2024-03-14', '09:00:00', 1, 1, 1, 'Consulta de rotina', 'Agendado'),
('2024-03-14', '10:30:00', 2, 3, 2, 'Vacinação anual', 'Agendado'),
('2024-03-15', '14:00:00', 3, 4, 3, 'Cirurgia de castração', 'Agendado'),
('2024-03-15', '16:00:00', 1, 2, 4, 'Banho e tosa', 'Agendado');

-- Verificar se a tabela foi criada corretamente
SELECT 'Tabela tab_agendamentos criada com sucesso!' as status;
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
