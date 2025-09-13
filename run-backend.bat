@echo off
echo =====================================================
echo    CLINICA VETERINARIA - BACKEND JAVA
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
echo Compilando o projeto...
mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ERRO: Falha na compilacao!
    pause
    exit /b 1
)

echo.
echo Iniciando o servidor...
echo O backend estara disponivel em: http://localhost:3000/api
echo Pressione Ctrl+C para parar o servidor
echo.
mvnw.cmd spring-boot:run

pause
