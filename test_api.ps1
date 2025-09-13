# Script para testar a API da Clínica Veterinária

Write-Host "=== TESTANDO API DA CLÍNICA VETERINÁRIA ===" -ForegroundColor Green

# Testar se o backend está rodando
Write-Host "`n1. Testando se o backend está rodando..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/clientes" -Method GET -TimeoutSec 5
    Write-Host "✅ Backend está rodando!" -ForegroundColor Green
    Write-Host "Clientes encontrados: $($response.Count)"
} catch {
    Write-Host "❌ Backend não está rodando ou não respondeu" -ForegroundColor Red
    Write-Host "Erro: $($_.Exception.Message)"
    exit 1
}

# Testar endpoint de clientes para dropdown
Write-Host "`n2. Testando endpoint de clientes para dropdown..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/clientes/dropdown" -Method GET -TimeoutSec 5
    Write-Host "✅ Endpoint de dropdown funcionando!" -ForegroundColor Green
    Write-Host "Clientes para dropdown:"
    $response | ForEach-Object { Write-Host "  - ID: $($_.id), Nome: $($_.nome), Email: $($_.email)" }
} catch {
    Write-Host "❌ Endpoint de dropdown não funcionou" -ForegroundColor Red
    Write-Host "Erro: $($_.Exception.Message)"
}

# Testar endpoint de animais
Write-Host "`n3. Testando endpoint de animais..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/animais" -Method GET -TimeoutSec 5
    Write-Host "✅ Endpoint de animais funcionando!" -ForegroundColor Green
    Write-Host "Animais encontrados: $($response.Count)"
} catch {
    Write-Host "❌ Endpoint de animais não funcionou" -ForegroundColor Red
    Write-Host "Erro: $($_.Exception.Message)"
}

Write-Host "`n=== TESTE CONCLUÍDO ===" -ForegroundColor Green
















