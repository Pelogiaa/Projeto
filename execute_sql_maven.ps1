# =====================================================
# SCRIPT PARA EXECUTAR SQL VIA MAVEN
# Projeto: Clinica Veterinaria
# =====================================================

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    EXECUTANDO SQL VIA MAVEN" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se o Maven está disponível
Write-Host "Verificando Maven..." -ForegroundColor Yellow
try {
    $mvnVersion = & .\mvnw.cmd --version
    Write-Host "Maven encontrado!" -ForegroundColor Green
    Write-Host "Versão: $($mvnVersion[0])" -ForegroundColor Gray
} catch {
    Write-Host "Maven não encontrado!" -ForegroundColor Red
    exit 1
}

# Executar SQL usando Maven
Write-Host "Executando SQL para criar tabela de serviços..." -ForegroundColor Yellow

# Criar um arquivo SQL temporário
$sqlContent = @"
-- Criar tabela de serviços
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
ON CONFLICT DO NOTHING;
"@

$sqlContent | Out-File -FilePath "temp_servicos.sql" -Encoding UTF8

Write-Host "Arquivo SQL temporário criado: temp_servicos.sql" -ForegroundColor Green
Write-Host ""
Write-Host "Para executar o SQL:" -ForegroundColor White
Write-Host "1. Abra o pgAdmin" -ForegroundColor Gray
Write-Host "2. Conecte ao banco 'projeto_clinica_veterinaria'" -ForegroundColor Gray
Write-Host "3. Abra o Query Tool" -ForegroundColor Gray
Write-Host "4. Copie e cole o conteúdo do arquivo 'temp_servicos.sql'" -ForegroundColor Gray
Write-Host "5. Execute o script (F5)" -ForegroundColor Gray
Write-Host ""

# Mostrar o conteúdo do arquivo
Write-Host "Conteúdo do arquivo SQL:" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Get-Content "temp_servicos.sql" | Write-Host

Write-Host ""
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    ARQUIVO SQL CRIADO COM SUCESSO!" -ForegroundColor Green
Write-Host "=====================================================" -ForegroundColor Cyan

Read-Host "Pressione Enter para continuar"








