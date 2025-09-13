-- Criar tabela de animais
CREATE TABLE tab_animais (
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

-- Criar índices para melhorar performance
CREATE INDEX idx_animais_cliente ON tab_animais(id_cliente);
CREATE INDEX idx_animais_nome ON tab_animais(nome_animal);
CREATE INDEX idx_animais_tipo ON tab_animais(tipo_animal);

-- Inserir dados de exemplo
INSERT INTO tab_animais (id_cliente, nome_animal, idade, tipo_animal, sexo, peso) VALUES 
(1, 'Rex', 3, 'Cachorro', 'Macho', 25.5),
(1, 'Mimi', 2, 'Gato', 'Fêmea', 4.2),
(2, 'Thor', 5, 'Cachorro', 'Macho', 30.0),
(3, 'Luna', 1, 'Gato', 'Fêmea', 3.8);

-- Verificar se a tabela foi criada
SELECT 'Tabela tab_animais criada com sucesso!' as status;
SELECT COUNT(*) as total_animais FROM tab_animais;
















