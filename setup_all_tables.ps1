# Script para configurar todas as tabelas e dados
Write-Host "Configurando banco de dados..." -ForegroundColor Green

# Aguardar o backend inicializar
Write-Host "Aguardando backend inicializar..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# Criar tabela de agendamentos
Write-Host "Criando tabela de agendamentos..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/data/create-agendamentos-table" -Method POST
    Write-Host "Resposta: $($response.Content)" -ForegroundColor Green
} catch {
    Write-Host "Erro ao criar tabela de agendamentos: $($_.Exception.Message)" -ForegroundColor Red
}

# Inserir dados de animais
Write-Host "Inserindo dados de animais..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/data/insert-animais" -Method POST
    Write-Host "Resposta: $($response.Content)" -ForegroundColor Green
} catch {
    Write-Host "Erro ao inserir animais: $($_.Exception.Message)" -ForegroundColor Red
}

# Inserir dados de agendamentos
Write-Host "Inserindo dados de agendamentos..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/data/insert-agendamentos" -Method POST
    Write-Host "Resposta: $($response.Content)" -ForegroundColor Green
} catch {
    Write-Host "Erro ao inserir agendamentos: $($_.Exception.Message)" -ForegroundColor Red
}

# Testar APIs
Write-Host "Testando APIs..." -ForegroundColor Yellow

# Testar agendamentos
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/agendamentos" -Method GET
    Write-Host "API de agendamentos funcionando: $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "Erro na API de agendamentos: $($_.Exception.Message)" -ForegroundColor Red
}

# Testar animais
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/animais" -Method GET
    Write-Host "API de animais funcionando: $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "Erro na API de animais: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "Configuração concluída!" -ForegroundColor Green


