# =====================================================
# ATUALIZAR TABELA TAB_AGENDAMENTOS
# Script PowerShell para executar atualizações no banco
# =====================================================

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    ATUALIZANDO TABELA TAB_AGENDAMENTOS" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se o PostgreSQL está rodando
Write-Host "Verificando conexão com PostgreSQL..." -ForegroundColor Yellow
try {
    $testConnection = psql -h localhost -U postgres -d projeto_clinica_veterinaria -c "SELECT 1;" 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Conexão com PostgreSQL estabelecida com sucesso!" -ForegroundColor Green
    } else {
        throw "Falha na conexão"
    }
} catch {
    Write-Host "ERRO: Não foi possível conectar ao PostgreSQL!" -ForegroundColor Red
    Write-Host "Verifique se o PostgreSQL está rodando e as credenciais estão corretas." -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

Write-Host ""

# Executar script de atualização
Write-Host "Executando atualização da tabela tab_agendamentos..." -ForegroundColor Yellow
try {
    psql -h localhost -U postgres -d projeto_clinica_veterinaria -f update_agendamentos_table_final.sql
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Atualização executada com sucesso!" -ForegroundColor Green
    } else {
        throw "Falha na execução do script"
    }
} catch {
    Write-Host "ERRO: Falha ao executar o script de atualização!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

Write-Host ""
Write-Host "=====================================================" -ForegroundColor Green
Write-Host "    ATUALIZAÇÃO CONCLUÍDA COM SUCESSO!" -ForegroundColor Green
Write-Host "=====================================================" -ForegroundColor Green
Write-Host "A tabela tab_agendamentos foi atualizada com a estrutura final." -ForegroundColor Cyan
Write-Host ""

Read-Host "Pressione Enter para sair"

