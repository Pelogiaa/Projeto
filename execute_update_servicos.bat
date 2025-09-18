@echo off
echo =====================================================
echo ATUALIZANDO TABELA TAB_SERVICOS
echo =====================================================
echo.

echo Executando atualizacao da tabela tab_servicos...
echo.

REM Tentar executar via pgAdmin ou psql se disponível
echo Por favor, execute o seguinte script SQL no pgAdmin:
echo.
echo 1. Abra o pgAdmin
echo 2. Conecte ao banco 'projeto_clinica_veterinaria'
echo 3. Abra o Query Tool
echo 4. Execute o conteudo do arquivo 'update_servicos_table_final.sql'
echo.

echo Ou execute via linha de comando se psql estiver configurado:
echo psql -h localhost -U postgres -d projeto_clinica_veterinaria -f update_servicos_table_final.sql
echo.

pause

