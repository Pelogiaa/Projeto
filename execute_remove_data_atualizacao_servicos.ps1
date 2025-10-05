# Script PowerShell para executar a remoção da coluna data_atualizacao da tabela tab_servicos
# Executa o script SQL remove_data_atualizacao_servicos.sql

Write-Host "=== REMOÇÃO DA COLUNA DATA_ATUALIZACAO DA TABELA TAB_SERVICOS ===" -ForegroundColor Yellow
Write-Host ""

# Configurações do banco
$DB_HOST = "localhost"
$DB_PORT = "5432"
$DB_NAME = "projeto_clinica_veterinaria"
$DB_USER = "postgres"

Write-Host "Configurações do banco:" -ForegroundColor Cyan
Write-Host "- Host: $DB_HOST" -ForegroundColor White
Write-Host "- Porta: $DB_PORT" -ForegroundColor White
Write-Host "- Banco: $DB_NAME" -ForegroundColor White
Write-Host "- Usuário: $DB_USER" -ForegroundColor White
Write-Host ""

# Verificar se o arquivo SQL existe
$SQL_FILE = "remove_data_atualizacao_servicos.sql"
if (-not (Test-Path $SQL_FILE)) {
    Write-Host "ERRO: Arquivo $SQL_FILE não encontrado!" -ForegroundColor Red
    exit 1
}

Write-Host "Arquivo SQL encontrado: $SQL_FILE" -ForegroundColor Green
Write-Host ""

# Executar o script SQL
Write-Host "Executando script SQL..." -ForegroundColor Cyan
try {
    $env:PGPASSWORD = Get-Content "pgpass.txt" -ErrorAction SilentlyContinue
    if (-not $env:PGPASSWORD) {
        Write-Host "Digite a senha do PostgreSQL:" -ForegroundColor Yellow
        $env:PGPASSWORD = Read-Host -AsSecureString | ConvertFrom-SecureString -AsPlainText
    }
    
    psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f $SQL_FILE
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "✅ SUCESSO: Coluna data_atualizacao removida da tabela tab_servicos!" -ForegroundColor Green
        Write-Host ""
        Write-Host "Estrutura final da tabela tab_servicos:" -ForegroundColor Cyan
        Write-Host "- id (SERIAL PRIMARY KEY)" -ForegroundColor White
        Write-Host "- nome (VARCHAR(255) NOT NULL)" -ForegroundColor White
        Write-Host "- preco (DECIMAL(10,2) NOT NULL)" -ForegroundColor White
        Write-Host "- descricao (TEXT)" -ForegroundColor White
        Write-Host "- observacoes (TEXT)" -ForegroundColor White
        Write-Host "- status (VARCHAR(20) DEFAULT 'Ativo')" -ForegroundColor White
        Write-Host "- data_cadastro (TIMESTAMP DEFAULT CURRENT_TIMESTAMP)" -ForegroundColor White
        Write-Host ""
        Write-Host "⚠️  PRÓXIMOS PASSOS:" -ForegroundColor Yellow
        Write-Host "1. Atualizar o modelo Servico.java no backend" -ForegroundColor White
        Write-Host "2. Atualizar o ServicoDTO.java no backend" -ForegroundColor White
        Write-Host "3. Atualizar o ServicoService.java no backend" -ForegroundColor White
        Write-Host "4. Recompilar e reiniciar o backend" -ForegroundColor White
    } else {
        Write-Host "❌ ERRO: Falha ao executar o script SQL!" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "❌ ERRO: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
} finally {
    # Limpar senha da memória
    Remove-Variable -Name PGPASSWORD -Scope Global -ErrorAction SilentlyContinue
}

Write-Host ""
Write-Host "=== SCRIPT CONCLUÍDO ===" -ForegroundColor Yellow

