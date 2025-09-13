@echo off
echo =====================================================
echo    CLINICA VETERINARIA - FRONTEND E BACKEND
echo =====================================================
echo.

echo Verificando se o Java esta instalado...
java -version
if %errorlevel% neq 0 (
    echo ERRO: Java nao encontrado!
    echo Por favor, instale o Java 17 ou superior e tente novamente.
    pause
    exit /b 1
)

echo.
echo Verificando se o Node.js esta instalado...
node --version
if %errorlevel% neq 0 (
    echo ERRO: Node.js nao encontrado!
    echo Por favor, instale o Node.js e tente novamente.
    pause
    exit /b 1
)

echo.

REM Verificar se as dependencias do frontend estao instaladas
if not exist "node_modules" (
    echo Instalando dependencias do frontend...
    npm install
    if %errorlevel% neq 0 (
        echo ERRO: Falha ao instalar dependencias do frontend!
        pause
        exit /b 1
    )
)

echo.

REM Compilar o backend
echo Compilando o backend...
mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ERRO: Falha na compilacao do backend!
    pause
    exit /b 1
)

echo.
echo Backend compilado com sucesso!

echo.
echo Iniciando o backend na porta 3001...
start "Backend" cmd /k "mvnw.cmd spring-boot:run"

REM Aguardar um pouco para o backend inicializar
timeout /t 5 /nobreak >nul

echo.
echo Iniciando o frontend na porta 3000...
echo.
echo =====================================================
echo    SERVICOS INICIADOS COM SUCESSO!
echo =====================================================
echo Backend:  http://localhost:3001/api
echo Frontend: http://localhost:3000
echo =====================================================
echo.

REM Iniciar o frontend
npm start

pause

