# =====================================================
# CLINICA VETERINARIA - FRONTEND E BACKEND
# Script PowerShell para executar ambos os serviços
# =====================================================

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "    CLINICA VETERINARIA - FRONTEND E BACKEND" -ForegroundColor Cyan
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

# Verificar se o Node.js está instalado
Write-Host "Verificando se o Node.js está instalado..." -ForegroundColor Yellow
try {
    $nodeVersion = node --version 2>&1
    Write-Host "Node.js encontrado: $nodeVersion" -ForegroundColor Green
} catch {
    Write-Host "ERRO: Node.js não encontrado!" -ForegroundColor Red
    Write-Host "Por favor, instale o Node.js e tente novamente." -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

Write-Host ""

# Verificar se as dependências do frontend estão instaladas
if (-not (Test-Path "node_modules")) {
    Write-Host "Instalando dependências do frontend..." -ForegroundColor Yellow
    npm install
    if ($LASTEXITCODE -ne 0) {
        Write-Host "ERRO: Falha ao instalar dependências do frontend!" -ForegroundColor Red
        Read-Host "Pressione Enter para sair"
        exit 1
    }
}

Write-Host ""

# Compilar o backend
Write-Host "Compilando o backend..." -ForegroundColor Yellow
try {
    & .\mvnw.cmd clean compile
    if ($LASTEXITCODE -ne 0) {
        throw "Falha na compilação do backend"
    }
    Write-Host "Backend compilado com sucesso!" -ForegroundColor Green
} catch {
    Write-Host "ERRO: Falha na compilação do backend!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

Write-Host ""

# Iniciar o backend em background
Write-Host "Iniciando o backend na porta 3001..." -ForegroundColor Yellow
$backendJob = Start-Job -ScriptBlock {
    Set-Location $using:PWD
    & .\mvnw.cmd spring-boot:run
}

# Aguardar um pouco para o backend inicializar
Start-Sleep -Seconds 5

# Iniciar o frontend
Write-Host "Iniciando o frontend na porta 3000..." -ForegroundColor Yellow
Write-Host ""
Write-Host "=====================================================" -ForegroundColor Green
Write-Host "    SERVIÇOS INICIADOS COM SUCESSO!" -ForegroundColor Green
Write-Host "=====================================================" -ForegroundColor Green
Write-Host "Backend:  http://localhost:3001/api" -ForegroundColor Cyan
Write-Host "Frontend: http://localhost:3000" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Green
Write-Host ""
Write-Host "Pressione Ctrl+C para parar ambos os serviços" -ForegroundColor Yellow
Write-Host ""

try {
    # Iniciar o frontend (este comando irá bloquear)
    npm start
} catch {
    Write-Host "Erro ao executar o frontend: $($_.Exception.Message)" -ForegroundColor Red
} finally {
    # Parar o backend quando o frontend for encerrado
    Write-Host "Parando o backend..." -ForegroundColor Yellow
    Stop-Job $backendJob
    Remove-Job $backendJob
}

Read-Host "Pressione Enter para sair"

