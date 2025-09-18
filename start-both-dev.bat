@echo off
echo =====================================================
echo Iniciando Clínica Veterinária - Full Stack
echo =====================================================
echo.
echo Iniciando Backend e Frontend simultaneamente...
echo.

REM Verificar se o Java está instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Java não encontrado! Instale o Java 17 ou superior.
    pause
    exit /b 1
)

REM Verificar se o Node.js está instalado
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Node.js não encontrado! Instale o Node.js 16 ou superior.
    pause
    exit /b 1
)

REM Verificar se as dependências do frontend estão instaladas
if not exist "node_modules" (
    echo Instalando dependências do frontend...
    npm install
)

echo.
echo Iniciando Backend (Spring Boot)...
echo URL Backend: http://localhost:8080
echo API: http://localhost:8080/api
echo.

REM Iniciar backend em uma nova janela
start "Backend - Clínica Veterinária" cmd /k ".\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"

REM Aguardar um pouco para o backend inicializar
timeout /t 10 /nobreak >nul

echo.
echo Iniciando Frontend (React)...
echo URL Frontend: http://localhost:3000
echo.

REM Iniciar frontend
npm start

echo.
echo Aplicação iniciada com sucesso!
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
echo.
pause
