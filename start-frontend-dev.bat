@echo off
echo =====================================================
echo Iniciando Frontend - Clínica Veterinária
echo =====================================================
echo.
echo Configurando ambiente de desenvolvimento...
echo.

REM Verificar se o Node.js está instalado
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Node.js não encontrado! Instale o Node.js 16 ou superior.
    pause
    exit /b 1
)

REM Verificar se as dependências estão instaladas
if not exist "node_modules" (
    echo Instalando dependências...
    npm install
)

REM Iniciar aplicação React
echo Iniciando aplicação React...
echo URL: http://localhost:3000
echo.
echo Pressione Ctrl+C para parar a aplicação
echo.

npm start
