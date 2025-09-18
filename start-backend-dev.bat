@echo off
echo =====================================================
echo Iniciando Backend - Clínica Veterinária
echo =====================================================
echo.
echo Configurando ambiente de desenvolvimento...
echo.

REM Verificar se o Java está instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Java não encontrado! Instale o Java 17 ou superior.
    pause
    exit /b 1
)

REM Verificar se o PostgreSQL está rodando
echo Verificando conexão com PostgreSQL...
echo.

REM Iniciar aplicação Spring Boot
echo Iniciando aplicação Spring Boot...
echo URL: http://localhost:8080
echo API: http://localhost:8080/api
echo.
echo Pressione Ctrl+C para parar a aplicação
echo.

.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
