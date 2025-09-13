@echo off
echo =====================================================
echo    CLINICA VETERINARIA - BACKEND JAVA
echo =====================================================
echo.

echo Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Java nao encontrado!
    echo Instale o Java 17+ e tente novamente.
    pause
    exit /b 1
)

echo Java OK!
echo.

echo Compilando projeto...
call mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ERRO: Falha na compilacao!
    pause
    exit /b 1
)

echo.
echo Iniciando servidor...
echo Backend: http://localhost:8080/api
echo Pressione Ctrl+C para parar
echo.

call mvnw.cmd spring-boot:run

pause
































