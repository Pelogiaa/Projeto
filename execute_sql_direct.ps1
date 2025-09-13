# =====================================================
# SCRIPT PARA EXECUTAR SQL DIRETAMENTE
# Projeto: Clinica Veterinaria
# =====================================================

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    EXECUTANDO SCRIPT SQL PARA CRIAR TABELA" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se o PostgreSQL está rodando
Write-Host "Verificando PostgreSQL..." -ForegroundColor Yellow
$pgProcess = Get-Process -Name "postgres" -ErrorAction SilentlyContinue
if (-not $pgProcess) {
    Write-Host "PostgreSQL não está rodando. Inicie o serviço primeiro." -ForegroundColor Red
    exit 1
}

Write-Host "PostgreSQL está rodando!" -ForegroundColor Green

# Tentar encontrar o psql
$psqlPath = $null
$possiblePaths = @(
    "C:\Program Files\PostgreSQL\*\bin\psql.exe",
    "C:\Program Files (x86)\PostgreSQL\*\bin\psql.exe",
    "C:\PostgreSQL\*\bin\psql.exe"
)

foreach ($path in $possiblePaths) {
    $found = Get-ChildItem -Path $path -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($found) {
        $psqlPath = $found.FullName
        break
    }
}

if ($psqlPath) {
    Write-Host "psql encontrado em: $psqlPath" -ForegroundColor Green
    
    # Executar o script SQL
    Write-Host "Executando script SQL..." -ForegroundColor Yellow
    try {
        & $psqlPath -h localhost -U postgres -d projeto_clinica_veterinaria -f create_servicos_table.sql
        Write-Host "Script executado com sucesso!" -ForegroundColor Green
    } catch {
        Write-Host "Erro ao executar script: $($_.Exception.Message)" -ForegroundColor Red
    }
} else {
    Write-Host "psql não encontrado. Executando manualmente..." -ForegroundColor Yellow
    
    # Mostrar instruções manuais
    Write-Host ""
    Write-Host "Para executar o script manualmente:" -ForegroundColor White
    Write-Host "1. Abra o pgAdmin" -ForegroundColor Gray
    Write-Host "2. Conecte ao banco 'projeto_clinica_veterinaria'" -ForegroundColor Gray
    Write-Host "3. Abra o Query Tool" -ForegroundColor Gray
    Write-Host "4. Copie e cole o conteúdo do arquivo 'create_servicos_table.sql'" -ForegroundColor Gray
    Write-Host "5. Execute o script (F5)" -ForegroundColor Gray
    Write-Host ""
    
    # Mostrar o conteúdo do arquivo
    Write-Host "Conteúdo do arquivo SQL:" -ForegroundColor Cyan
    Write-Host "=====================================================" -ForegroundColor Cyan
    Get-Content "create_servicos_table.sql" | Write-Host
}

Write-Host ""
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    PROCESSO CONCLUÍDO!" -ForegroundColor Green
Write-Host "=====================================================" -ForegroundColor Cyan

Read-Host "Pressione Enter para continuar"











