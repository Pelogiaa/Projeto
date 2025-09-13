package com.clinicaveterinaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateServicosTable {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/projeto_clinica_veterinaria";
        String username = "postgres";
        String password = "postgres"; // Altere se necessário
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Conectado ao banco de dados!");
            
            // Criar tabela
            String createTable = """
                CREATE TABLE IF NOT EXISTS tab_servicos (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL,
                    categoria VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    preco DECIMAL(10,2) NOT NULL,
                    observacoes TEXT,
                    status VARCHAR(20) DEFAULT 'Ativo' CHECK (status IN ('Ativo', 'Inativo')),
                    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            
            stmt.execute(createTable);
            System.out.println("Tabela tab_servicos criada com sucesso!");
            
            // Inserir dados de exemplo
            String insertData = """
                INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) VALUES
                ('Consulta Veterinária', 'Consultas', 'Atendimento clínico completo para cães e gatos', 120.00, 'Inclui exame físico completo e orientações', 'Ativo'),
                ('Vacinação', 'Prevenção', 'Aplicação de vacinas essenciais para pets', 80.00, 'Vacinas V8, V10, antirrábica e outras', 'Ativo'),
                ('Cirurgia de Castração', 'Cirurgias', 'Procedimento cirúrgico para castração de cães e gatos', 350.00, 'Inclui anestesia e pós-operatório', 'Ativo'),
                ('Banho e Tosa', 'Estética', 'Serviço completo de higiene e estética animal', 60.00, 'Banho, secagem, tosa e corte de unhas', 'Ativo'),
                ('Exames Laboratoriais', 'Diagnóstico', 'Coleta e análise de exames de sangue e urina', 150.00, 'Hemograma completo e bioquímica', 'Ativo')
                ON CONFLICT DO NOTHING
                """;
            
            stmt.execute(insertData);
            System.out.println("Dados de exemplo inseridos com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao executar SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
