# Script para inserir serviços de exemplo
Write-Host "Inserindo serviços de exemplo..." -ForegroundColor Yellow

$psqlPath = "C:\Program Files\PostgreSQL\17\bin\psql.exe"

if (-not (Test-Path $psqlPath)) {
    Write-Host "PostgreSQL não encontrado em: $psqlPath" -ForegroundColor Red
    exit 1
}

try {
    # Executar o script SQL
    & $psqlPath -h localhost -U postgres -d projeto_clinica_veterinaria -f insert_servicos.sql
    Write-Host "Serviços inseridos com sucesso!" -ForegroundColor Green
} catch {
    Write-Host "Erro ao inserir serviços: $($_.Exception.Message)" -ForegroundColor Red
}
