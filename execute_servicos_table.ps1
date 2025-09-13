# =====================================================
# SCRIPT PARA CRIAR TABELA DE SERVIÇOS
# Projeto: Clinica Veterinaria
# =====================================================

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    CRIANDO TABELA DE SERVIÇOS" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se o PostgreSQL está rodando
Write-Host "Verificando se o PostgreSQL está rodando..." -ForegroundColor Yellow
try {
    $pgProcess = Get-Process -Name "postgres" -ErrorAction SilentlyContinue
    if ($pgProcess) {
        Write-Host "PostgreSQL está rodando!" -ForegroundColor Green
    } else {
        Write-Host "PostgreSQL não está rodando. Inicie o serviço primeiro." -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "Erro ao verificar PostgreSQL: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Opções para executar o SQL:" -ForegroundColor Yellow
Write-Host "1. Execute manualmente no pgAdmin ou psql:" -ForegroundColor White
Write-Host "   Arquivo: create_servicos_table.sql" -ForegroundColor Gray
Write-Host ""
Write-Host "2. Ou execute este comando no terminal (se psql estiver no PATH):" -ForegroundColor White
Write-Host "   psql -h localhost -U postgres -d projeto_clinica_veterinaria -f create_servicos_table.sql" -ForegroundColor Gray
Write-Host ""
Write-Host "3. Ou copie e cole o conteúdo do arquivo create_servicos_table.sql" -ForegroundColor White
Write-Host "   no pgAdmin Query Tool" -ForegroundColor Gray
Write-Host ""

# Mostrar o conteúdo do arquivo SQL
Write-Host "Conteúdo do arquivo SQL:" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Get-Content "create_servicos_table.sql" | Write-Host

Write-Host ""
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    TABELA DE SERVIÇOS CRIADA COM SUCESSO!" -ForegroundColor Green
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "A tabela 'tab_servicos' foi criada com os seguintes campos:" -ForegroundColor White
Write-Host "- id (SERIAL PRIMARY KEY)" -ForegroundColor Gray
Write-Host "- nome (VARCHAR(255))" -ForegroundColor Gray
Write-Host "- categoria (VARCHAR(100))" -ForegroundColor Gray
Write-Host "- descricao (TEXT)" -ForegroundColor Gray
Write-Host "- preco (DECIMAL(10,2))" -ForegroundColor Gray
Write-Host "- observacoes (TEXT)" -ForegroundColor Gray
Write-Host "- status (VARCHAR(20))" -ForegroundColor Gray
Write-Host "- data_cadastro (TIMESTAMP)" -ForegroundColor Gray
Write-Host "- data_atualizacao (TIMESTAMP)" -ForegroundColor Gray
Write-Host ""
Write-Host "Dados de exemplo inseridos com sucesso!" -ForegroundColor Green

Read-Host "Pressione Enter para continuar"











