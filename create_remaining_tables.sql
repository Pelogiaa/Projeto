-- Criar tabela de animais se não existir
CREATE TABLE IF NOT EXISTS tab_animais (
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER NOT NULL,
    nome_animal VARCHAR(100) NOT NULL,
    idade INTEGER NOT NULL,
    tipo_animal VARCHAR(50) NOT NULL,
    sexo VARCHAR(10) NOT NULL CHECK (sexo IN ('Macho', 'Fêmea')),
    peso DECIMAL(5,2) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_animais_cliente FOREIGN KEY (id_cliente) REFERENCES tab_cliente(id) ON DELETE CASCADE
);

-- Criar tabela de agendamentos se não existir
CREATE TABLE IF NOT EXISTS tab_agendamentos (
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER NOT NULL,
    id_animal INTEGER NOT NULL,
    id_servico INTEGER NOT NULL,
    data_agendamento TIMESTAMP NOT NULL,
    observacoes TEXT,
    status VARCHAR(20) DEFAULT 'Agendado' CHECK (status IN ('Agendado', 'Confirmado', 'Realizado', 'Cancelado')),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_agendamentos_cliente FOREIGN KEY (id_cliente) REFERENCES tab_cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_agendamentos_animal FOREIGN KEY (id_animal) REFERENCES tab_animais(id) ON DELETE CASCADE,
    CONSTRAINT fk_agendamentos_servico FOREIGN KEY (id_servico) REFERENCES tab_servicos(id) ON DELETE CASCADE
);

-- Inserir dados de exemplo para animais
INSERT INTO tab_animais (id_cliente, nome_animal, idade, tipo_animal, sexo, peso, data_cadastro) VALUES
(1, 'Rex', 3, 'Cão', 'Macho', 25.5, CURRENT_TIMESTAMP),
(1, 'Luna', 2, 'Gato', 'Fêmea', 4.2, CURRENT_TIMESTAMP),
(2, 'Thor', 5, 'Cão', 'Macho', 30.0, CURRENT_TIMESTAMP),
(2, 'Mimi', 1, 'Gato', 'Fêmea', 3.8, CURRENT_TIMESTAMP),
(3, 'Bella', 4, 'Cão', 'Fêmea', 22.3, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- Inserir dados de exemplo para agendamentos
INSERT INTO tab_agendamentos (id_cliente, id_animal, id_servico, data_agendamento, observacoes, status, data_cadastro) VALUES
(1, 1, 1, '2025-09-20 10:00:00', 'Primeira consulta do Rex', 'Agendado', CURRENT_TIMESTAMP),
(1, 2, 2, '2025-09-21 14:30:00', 'Vacinação anual da Luna', 'Agendado', CURRENT_TIMESTAMP),
(2, 3, 3, '2025-09-22 09:00:00', 'Cirurgia de castração do Thor', 'Agendado', CURRENT_TIMESTAMP),
(2, 4, 4, '2025-09-23 15:00:00', 'Banho e tosa da Mimi', 'Agendado', CURRENT_TIMESTAMP),
(3, 5, 5, '2025-09-24 11:30:00', 'Exames de rotina da Bella', 'Agendado', CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;


