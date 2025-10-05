package com.clinicaveterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Controller temporário para inserir dados de exemplo
 */
@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "http://localhost:3000")
public class DataController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PostMapping("/insert-servicos")
    public ResponseEntity<?> inserirServicos() {
        try {
            // Verificar se já existem dados
            String countQuery = "SELECT COUNT(*) FROM tab_servicos";
            Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class);
            
            if (count != null && count > 0) {
                return ResponseEntity.ok().body("Dados já existem na tabela");
            }
            
            // Inserir dados de exemplo
            String insertQuery = """
                INSERT INTO tab_servicos (nome, preco, descricao, observacoes, status, data_cadastro) VALUES
                ('Consulta Veterinária', 120.00, 'Atendimento clínico completo para cães e gatos', 'Inclui exame físico completo e orientações', 'ATIVO', CURRENT_TIMESTAMP),
                ('Vacinação', 80.00, 'Aplicação de vacinas essenciais para pets', 'Vacinas V8, V10, antirrábica e outras', 'ATIVO', CURRENT_TIMESTAMP),
                ('Cirurgia de Castração', 350.00, 'Procedimento cirúrgico para castração de cães e gatos', 'Inclui anestesia e pós-operatório', 'ATIVO', CURRENT_TIMESTAMP),
                ('Banho e Tosa', 60.00, 'Serviço completo de higiene e estética animal', 'Banho, secagem, tosa e corte de unhas', 'ATIVO', CURRENT_TIMESTAMP),
                ('Exames Laboratoriais', 150.00, 'Coleta e análise de exames de sangue e urina', 'Hemograma completo e bioquímica', 'ATIVO', CURRENT_TIMESTAMP)
                """;
            
            jdbcTemplate.execute(insertQuery);
            
            return ResponseEntity.ok().body("Dados inseridos com sucesso");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao inserir dados: " + e.getMessage());
        }
    }
    
    @GetMapping("/servicos")
    public ResponseEntity<List<Map<String, Object>>> listarServicos() {
        try {
            String query = "SELECT * FROM tab_servicos ORDER BY nome";
            List<Map<String, Object>> servicos = jdbcTemplate.queryForList(query);
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/create-cliente-table")
    public ResponseEntity<?> criarTabelaCliente() {
        try {
            // Verificar se a tabela já existe
            String checkQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'tab_cliente'";
            Integer tableExists = jdbcTemplate.queryForObject(checkQuery, Integer.class);
            
            if (tableExists != null && tableExists > 0) {
                return ResponseEntity.ok().body("Tabela tab_cliente já existe");
            }
            
            // Criar a tabela de clientes
            String createTableQuery = """
                CREATE TABLE tab_cliente (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    telefone VARCHAR(20) NOT NULL,
                    cep VARCHAR(10) NOT NULL,
                    cpf VARCHAR(14) UNIQUE NOT NULL,
                    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            
            jdbcTemplate.execute(createTableQuery);
            
            // Criar índices
            jdbcTemplate.execute("CREATE INDEX idx_cliente_cpf ON tab_cliente(cpf)");
            jdbcTemplate.execute("CREATE INDEX idx_cliente_email ON tab_cliente(email)");
            jdbcTemplate.execute("CREATE INDEX idx_cliente_nome ON tab_cliente(nome)");
            
            return ResponseEntity.ok().body("Tabela tab_cliente criada com sucesso");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar tabela: " + e.getMessage());
        }
    }
    
    @PostMapping("/insert-clientes")
    public ResponseEntity<?> inserirClientes() {
        try {
            // Verificar se já existem dados
            String countQuery = "SELECT COUNT(*) FROM tab_cliente";
            Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class);
            
            if (count != null && count > 0) {
                return ResponseEntity.ok().body("Dados já existem na tabela de clientes");
            }
            
            // Inserir dados de exemplo
            String insertQuery = """
                INSERT INTO tab_cliente (nome, email, telefone, cep, cpf, data_cadastro) VALUES
                ('João Silva', 'joao.silva@email.com', '(11) 99999-9999', '01234-567', '123.456.789-00', CURRENT_TIMESTAMP),
                ('Maria Santos', 'maria.santos@email.com', '(11) 88888-8888', '04567-890', '987.654.321-00', CURRENT_TIMESTAMP),
                ('Pedro Oliveira', 'pedro.oliveira@email.com', '(11) 77777-7777', '07890-123', '456.789.123-00', CURRENT_TIMESTAMP)
                """;
            
            jdbcTemplate.execute(insertQuery);
            
            return ResponseEntity.ok().body("Dados de clientes inseridos com sucesso");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao inserir dados: " + e.getMessage());
        }
    }
    
    @GetMapping("/clientes")
    public ResponseEntity<List<Map<String, Object>>> listarClientes() {
        try {
            String query = "SELECT * FROM tab_cliente ORDER BY data_cadastro DESC";
            List<Map<String, Object>> clientes = jdbcTemplate.queryForList(query);
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/create-animais-table")
    public ResponseEntity<?> criarTabelaAnimais() {
        try {
            // Verificar se a tabela já existe
            String checkQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'tab_animais'";
            Integer tableExists = jdbcTemplate.queryForObject(checkQuery, Integer.class);
            
            if (tableExists != null && tableExists > 0) {
                return ResponseEntity.ok().body("Tabela tab_animais já existe");
            }
            
            // Criar a tabela de animais
            String createTableQuery = """
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
                )
                """;
            
            jdbcTemplate.execute(createTableQuery);
            
            // Criar índices
            jdbcTemplate.execute("CREATE INDEX idx_animais_cliente ON tab_animais(id_cliente)");
            jdbcTemplate.execute("CREATE INDEX idx_animais_nome ON tab_animais(nome_animal)");
            jdbcTemplate.execute("CREATE INDEX idx_animais_tipo ON tab_animais(tipo_animal)");
            
            return ResponseEntity.ok().body("Tabela tab_animais criada com sucesso");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar tabela: " + e.getMessage());
        }
    }
    
    @PostMapping("/create-agendamentos-table")
    public ResponseEntity<?> criarTabelaAgendamentos() {
        try {
            // Verificar se a tabela já existe
            String checkQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'tab_agendamentos'";
            Integer tableExists = jdbcTemplate.queryForObject(checkQuery, Integer.class);
            
            if (tableExists != null && tableExists > 0) {
                return ResponseEntity.ok().body("Tabela tab_agendamentos já existe");
            }
            
            // Criar a tabela de agendamentos
            String createTableQuery = """
                CREATE TABLE tab_agendamentos (
                    id SERIAL PRIMARY KEY,
                    id_animal INTEGER NOT NULL,
                    id_servico INTEGER NOT NULL,
                    data_agendamento TIMESTAMP NOT NULL,
                    CONSTRAINT fk_agendamentos_animal FOREIGN KEY (id_animal) REFERENCES tab_animais(id) ON DELETE CASCADE,
                    CONSTRAINT fk_agendamentos_servico FOREIGN KEY (id_servico) REFERENCES tab_servicos(id) ON DELETE CASCADE
                )
                """;
            
            jdbcTemplate.execute(createTableQuery);
            
            // Criar índices
            jdbcTemplate.execute("CREATE INDEX idx_agendamentos_animal ON tab_agendamentos(id_animal)");
            jdbcTemplate.execute("CREATE INDEX idx_agendamentos_servico ON tab_agendamentos(id_servico)");
            jdbcTemplate.execute("CREATE INDEX idx_agendamentos_data ON tab_agendamentos(data_agendamento)");
            
            return ResponseEntity.ok().body("Tabela tab_agendamentos criada com sucesso");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar tabela: " + e.getMessage());
        }
    }
    
    @PostMapping("/insert-animais")
    public ResponseEntity<?> inserirAnimais() {
        try {
            // Verificar se já existem dados
            String countQuery = "SELECT COUNT(*) FROM tab_animais";
            Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class);
            
            if (count != null && count > 0) {
                return ResponseEntity.ok().body("Dados já existem na tabela de animais");
            }
            
            // Inserir dados de exemplo
            String insertQuery = """
                INSERT INTO tab_animais (id_cliente, nome_animal, idade, tipo_animal, sexo, peso, data_cadastro) VALUES
                (1, 'Rex', 3, 'Cão', 'Macho', 25.5, CURRENT_TIMESTAMP),
                (1, 'Luna', 2, 'Gato', 'Fêmea', 4.2, CURRENT_TIMESTAMP),
                (2, 'Thor', 5, 'Cão', 'Macho', 30.0, CURRENT_TIMESTAMP),
                (2, 'Mimi', 1, 'Gato', 'Fêmea', 3.8, CURRENT_TIMESTAMP),
                (3, 'Bella', 4, 'Cão', 'Fêmea', 22.3, CURRENT_TIMESTAMP)
                """;
            
            jdbcTemplate.execute(insertQuery);
            
            return ResponseEntity.ok().body("Dados de animais inseridos com sucesso");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao inserir dados: " + e.getMessage());
        }
    }
    
    @PostMapping("/insert-agendamentos")
    public ResponseEntity<?> inserirAgendamentos() {
        try {
            // Verificar se já existem dados
            String countQuery = "SELECT COUNT(*) FROM tab_agendamentos";
            Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class);
            
            if (count != null && count > 0) {
                return ResponseEntity.ok().body("Dados já existem na tabela de agendamentos");
            }
            
            // Inserir dados de exemplo
            String insertQuery = """
                INSERT INTO tab_agendamentos (id_animal, id_servico, data_agendamento) VALUES
                (1, 1, '2025-09-20 10:00:00'),
                (2, 2, '2025-09-21 14:30:00'),
                (3, 3, '2025-09-22 09:00:00'),
                (4, 4, '2025-09-23 15:00:00'),
                (5, 5, '2025-09-24 11:30:00')
                """;
            
            jdbcTemplate.execute(insertQuery);
            
            return ResponseEntity.ok().body("Dados de agendamentos inseridos com sucesso");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao inserir dados: " + e.getMessage());
        }
    }
    
    @GetMapping("/animais")
    public ResponseEntity<List<Map<String, Object>>> listarAnimais() {
        try {
            String query = "SELECT * FROM tab_animais ORDER BY data_cadastro DESC";
            List<Map<String, Object>> animais = jdbcTemplate.queryForList(query);
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping("/agendamentos")
    public ResponseEntity<List<Map<String, Object>>> listarAgendamentos() {
        try {
            String query = """
                SELECT a.*, c.nome as cliente_nome, an.nome_animal, s.nome as servico_nome 
                FROM tab_agendamentos a
                JOIN tab_cliente c ON a.id_cliente = c.id
                JOIN tab_animais an ON a.id_animal = an.id
                JOIN tab_servicos s ON a.id_servico = s.id
                ORDER BY a.data_agendamento DESC
                """;
            List<Map<String, Object>> agendamentos = jdbcTemplate.queryForList(query);
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/remove-servicos-duplicados")
    public ResponseEntity<?> removerServicosDuplicados() {
        try {
            // Primeiro, verificar quantos serviços existem
            String countQuery = "SELECT COUNT(*) FROM tab_servicos";
            Integer totalAntes = jdbcTemplate.queryForObject(countQuery, Integer.class);
            
            // Remover duplicados baseado no nome (manter apenas o primeiro)
            String removeDuplicadosQuery = """
                DELETE FROM tab_servicos 
                WHERE id NOT IN (
                    SELECT MIN(id) 
                    FROM tab_servicos 
                    GROUP BY nome
                )
                """;
            
            int removidos = jdbcTemplate.update(removeDuplicadosQuery);
            
            // Verificar quantos serviços restaram
            Integer totalDepois = jdbcTemplate.queryForObject(countQuery, Integer.class);
            
            return ResponseEntity.ok().body(String.format(
                "Serviços duplicados removidos com sucesso! " +
                "Total antes: %d, Removidos: %d, Total depois: %d", 
                totalAntes, removidos, totalDepois
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao remover duplicados: " + e.getMessage());
        }
    }
    
    @GetMapping("/verificar-servicos-duplicados")
    public ResponseEntity<?> verificarServicosDuplicados() {
        try {
            // Buscar serviços duplicados
            String duplicadosQuery = """
                SELECT nome, COUNT(*) as quantidade
                FROM tab_servicos 
                GROUP BY nome 
                HAVING COUNT(*) > 1
                ORDER BY quantidade DESC
                """;
            
            List<Map<String, Object>> duplicados = jdbcTemplate.queryForList(duplicadosQuery);
            
            if (duplicados.isEmpty()) {
                return ResponseEntity.ok().body("Nenhum serviço duplicado encontrado");
            }
            
            return ResponseEntity.ok(duplicados);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao verificar duplicados: " + e.getMessage());
        }
    }
    
    @PostMapping("/criar-servicos-duplicados-teste")
    public ResponseEntity<?> criarServicosDuplicadosTeste() {
        try {
            // Inserir alguns serviços duplicados para teste
            String insertQuery = """
                INSERT INTO tab_servicos (nome, preco, descricao, observacoes, status, data_cadastro) VALUES
                ('Consulta Veterinária', 120.00, 'Atendimento clínico completo para cães e gatos', 'Inclui exame físico completo e orientações', 'ATIVO', CURRENT_TIMESTAMP),
                ('Vacinação', 80.00, 'Aplicação de vacinas essenciais para pets', 'Vacinas V8, V10, antirrábica e outras', 'ATIVO', CURRENT_TIMESTAMP),
                ('Banho e Tosa', 60.00, 'Serviço completo de higiene e estética animal', 'Banho, secagem, tosa e corte de unhas', 'ATIVO', CURRENT_TIMESTAMP)
                """;
            
            jdbcTemplate.execute(insertQuery);
            
            return ResponseEntity.ok().body("Serviços duplicados criados para teste");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar duplicados: " + e.getMessage());
        }
    }
    
    @PostMapping("/add-raca-cor-columns")
    public ResponseEntity<?> adicionarColunasRacaCor() {
        try {
            // Verificar se as colunas já existem
            String checkQuery = """
                SELECT COUNT(*) FROM information_schema.columns 
                WHERE table_name = 'tab_animais' 
                AND column_name IN ('raca', 'cor')
                """;
            Integer columnCount = jdbcTemplate.queryForObject(checkQuery, Integer.class);
            
            if (columnCount != null && columnCount >= 2) {
                return ResponseEntity.ok().body("Colunas raça e cor já existem na tabela tab_animais");
            }
            
            // Adicionar as colunas
            String alterQuery = """
                ALTER TABLE tab_animais 
                ADD COLUMN IF NOT EXISTS raca VARCHAR(50),
                ADD COLUMN IF NOT EXISTS cor VARCHAR(30)
                """;
            
            jdbcTemplate.execute(alterQuery);
            
            return ResponseEntity.ok().body("Colunas raça e cor adicionadas com sucesso à tabela tab_animais");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao adicionar colunas: " + e.getMessage());
        }
    }
    
    @PostMapping("/test-animal-insert")
    public ResponseEntity<?> testarInsercaoAnimal() {
        try {
            // Inserir animal diretamente via SQL
            String insertQuery = """
                INSERT INTO tab_animais (id_cliente, nome_animal, idade, tipo_animal, sexo, peso, raca, cor, data_cadastro) 
                VALUES (1, 'Nina', 8, 'Cão', 'Fêmea', 23.0, 'Border', 'Caramelo', CURRENT_TIMESTAMP)
                """;
            
            jdbcTemplate.execute(insertQuery);
            
            return ResponseEntity.ok().body("Animal inserido com sucesso via SQL direto");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao inserir animal: " + e.getMessage());
        }
    }
    
    @PostMapping("/fix-sexo-constraint")
    public ResponseEntity<?> corrigirConstraintSexo() {
        try {
            // Primeiro, verificar quais valores existem
            String checkQuery = "SELECT DISTINCT sexo FROM tab_animais";
            List<Map<String, Object>> sexos = jdbcTemplate.queryForList(checkQuery);
            
            // Remover a constraint atual
            String dropConstraintQuery = """
                ALTER TABLE tab_animais DROP CONSTRAINT IF EXISTS tab_animais_sexo_check
                """;
            
            jdbcTemplate.execute(dropConstraintQuery);
            
            // Adicionar nova constraint que aceita acentos
            String addConstraintQuery = """
                ALTER TABLE tab_animais ADD CONSTRAINT tab_animais_sexo_check 
                CHECK (sexo IN ('Macho', 'Fêmea', 'Macho', 'Fêmea'))
                """;
            
            jdbcTemplate.execute(addConstraintQuery);
            
            return ResponseEntity.ok().body("Constraint de sexo corrigida com sucesso. Valores encontrados: " + sexos.toString());
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao corrigir constraint: " + e.getMessage());
        }
    }
    
    @PostMapping("/check-sexo-values")
    public ResponseEntity<?> verificarValoresSexo() {
        try {
            String checkQuery = "SELECT DISTINCT sexo FROM tab_animais";
            List<Map<String, Object>> sexos = jdbcTemplate.queryForList(checkQuery);
            
            return ResponseEntity.ok(sexos);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao verificar valores: " + e.getMessage());
        }
    }
    
    @PostMapping("/fix-sexo-data")
    public ResponseEntity<?> corrigirDadosSexo() {
        try {
            // Corrigir os dados existentes
            String updateQuery = """
                UPDATE tab_animais 
                SET sexo = CASE 
                    WHEN sexo = 'FÃÂªmea' THEN 'Fêmea'
                    WHEN sexo = 'Macho' THEN 'Macho'
                    ELSE sexo
                END
                """;
            
            int updated = jdbcTemplate.update(updateQuery);
            
            return ResponseEntity.ok().body("Dados de sexo corrigidos. Registros atualizados: " + updated);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao corrigir dados: " + e.getMessage());
        }
    }
    
    @PostMapping("/test-femea-insert")
    public ResponseEntity<?> testarInsercaoFemea() {
        try {
            // Inserir animal com Fêmea diretamente via SQL
            String insertQuery = """
                INSERT INTO tab_animais (id_cliente, nome_animal, idade, tipo_animal, sexo, peso, raca, cor, data_cadastro) 
                VALUES (1, 'Luna', 5, 'Cao', 'Fêmea', 20.5, 'Border', 'Caramelo', CURRENT_TIMESTAMP)
                """;
            
            jdbcTemplate.execute(insertQuery);
            
            return ResponseEntity.ok().body("Animal Fêmea inserido com sucesso via SQL direto");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao inserir animal Fêmea: " + e.getMessage());
        }
    }
}