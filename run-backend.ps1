# =====================================================
# CLINICA VETERINARIA - BACKEND JAVA
# Script PowerShell para executar o backend
# =====================================================

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    CLINICA VETERINARIA - BACKEND JAVA" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se o Java está instalado
Write-Host "Verificando se o Java está instalado..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java encontrado: $($javaVersion[0])" -ForegroundColor Green
} catch {
    Write-Host "ERRO: Java não encontrado!" -ForegroundColor Red
    Write-Host "Por favor, instale o Java 17 ou superior e tente novamente." -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

Write-Host ""

# Verificar se o arquivo mvnw.cmd existe
if (-not (Test-Path "mvnw.cmd")) {
    Write-Host "ERRO: Arquivo mvnw.cmd não encontrado!" -ForegroundColor Red
    Write-Host "Certifique-se de estar na pasta correta do projeto." -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

# Compilar o projeto
Write-Host "Compilando o projeto..." -ForegroundColor Yellow
try {
    & .\mvnw.cmd clean compile
    if ($LASTEXITCODE -ne 0) {
        throw "Falha na compilação"
    }
    Write-Host "Compilação concluída com sucesso!" -ForegroundColor Green
} catch {
    Write-Host "ERRO: Falha na compilação!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

Write-Host ""

# Executar o servidor
Write-Host "Iniciando o servidor..." -ForegroundColor Yellow
Write-Host "O backend estará disponível em: http://localhost:3000/api" -ForegroundColor Green
Write-Host "Pressione Ctrl+C para parar o servidor" -ForegroundColor Yellow
Write-Host ""

try {
    & .\mvnw.cmd spring-boot:run
} catch {
    Write-Host "Erro ao executar o servidor: $($_.Exception.Message)" -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

Read-Host "Pressione Enter para sair"
































