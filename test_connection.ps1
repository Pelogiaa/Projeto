# Script para testar conexão e criar tabela
Write-Host "Testando conexão com PostgreSQL..." -ForegroundColor Yellow

# Usar o psql encontrado anteriormente
$psqlPath = "C:\Program Files\PostgreSQL\17\bin\psql.exe"

# Comando para criar a tabela
$sqlCommand = @"
-- Conectar ao banco e criar tabela
\c projeto_clinica_veterinaria;

-- Criar tabela se não existir
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
);

-- Inserir dados de exemplo
INSERT INTO tab_servicos (nome, categoria, descricao, preco, observacoes, status) VALUES
('Consulta Veterinária', 'Consultas', 'Atendimento clínico completo para cães e gatos', 120.00, 'Inclui exame físico completo e orientações', 'Ativo'),
('Vacinação', 'Prevenção', 'Aplicação de vacinas essenciais para pets', 80.00, 'Vacinas V8, V10, antirrábica e outras', 'Ativo'),
('Cirurgia de Castração', 'Cirurgias', 'Procedimento cirúrgico para castração de cães e gatos', 350.00, 'Inclui anestesia e pós-operatório', 'Ativo'),
('Banho e Tosa', 'Estética', 'Serviço completo de higiene e estética animal', 60.00, 'Banho, secagem, tosa e corte de unhas', 'Ativo'),
('Exames Laboratoriais', 'Diagnóstico', 'Coleta e análise de exames de sangue e urina', 150.00, 'Hemograma completo e bioquímica', 'Ativo')
ON CONFLICT (nome) DO NOTHING;

-- Verificar se os dados foram inseridos
SELECT COUNT(*) as total_servicos FROM tab_servicos;
"@

# Salvar comando em arquivo temporário
$tempFile = "temp_script.sql"
$sqlCommand | Out-File -FilePath $tempFile -Encoding UTF8

try {
    Write-Host "Executando script SQL..." -ForegroundColor Yellow
    & $psqlPath -h localhost -U postgres -d projeto_clinica_veterinaria -f $tempFile
    Write-Host "Script executado com sucesso!" -ForegroundColor Green
} catch {
    Write-Host "Erro ao executar script: $($_.Exception.Message)" -ForegroundColor Red
} finally {
    # Limpar arquivo temporário
    if (Test-Path $tempFile) {
        Remove-Item $tempFile
    }
}
