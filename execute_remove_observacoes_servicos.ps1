# =====================================================
# CLINICA VETERINARIA - REMOVER COLUNA OBSERVACOES
# Script PowerShell para executar remoção da coluna observacoes
# =====================================================

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    REMOVENDO COLUNA OBSERVACOES DA TABELA SERVICOS" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se o PostgreSQL está instalado
Write-Host "Verificando se o PostgreSQL está instalado..." -ForegroundColor Yellow
try {
    $psqlVersion = psql --version 2>&1
    Write-Host "PostgreSQL encontrado: $psqlVersion" -ForegroundColor Green
} catch {
    Write-Host "ERRO: PostgreSQL não encontrado!" -ForegroundColor Red
    Write-Host "Tentando executar via pgAdmin ou outra ferramenta..." -ForegroundColor Yellow
    
    # Tentar executar via pgAdmin ou outra ferramenta
    Write-Host "Por favor, execute o script SQL manualmente no pgAdmin ou outra ferramenta PostgreSQL:" -ForegroundColor Yellow
    Write-Host "Arquivo: remove_observacoes_servicos.sql" -ForegroundColor Cyan
    Read-Host "Pressione Enter para continuar após executar o script"
    exit 0
}

Write-Host ""

# Executar o script SQL
Write-Host "Executando script SQL para remover coluna observacoes..." -ForegroundColor Yellow
try {
    $env:PGPASSWORD = "5720"
    psql -h localhost -U postgres -d projeto_clinica_veterinaria -f remove_observacoes_servicos.sql
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Script SQL executado com sucesso!" -ForegroundColor Green
    } else {
        throw "Falha na execução do script SQL"
    }
} catch {
    Write-Host "ERRO: Falha ao executar script SQL!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Write-Host ""
    Write-Host "Por favor, execute o script SQL manualmente no pgAdmin:" -ForegroundColor Yellow
    Write-Host "Arquivo: remove_observacoes_servicos.sql" -ForegroundColor Cyan
}

Write-Host ""
Write-Host "=====================================================" -ForegroundColor Green
Write-Host "    REMOÇÃO DA COLUNA OBSERVACOES CONCLUÍDA!" -ForegroundColor Green
Write-Host "=====================================================" -ForegroundColor Green
Write-Host ""

Read-Host "Pressione Enter para sair"

