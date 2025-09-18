# Script PowerShell para inserir dados de exemplo na tabela de agendamentos
# Projeto: Clínica Veterinária

Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "INSERÇÃO DE DADOS DE EXEMPLO - AGENDAMENTOS" -ForegroundColor Cyan
Write-Host "Projeto: Clínica Veterinária" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan

# Configurações do banco de dados
$dbHost = "localhost"
$port = "5432"
$database = "projeto_clinica_veterinaria"
$username = "postgres"
$password = "5720"

Write-Host "Conectando ao banco de dados..." -ForegroundColor Yellow
Write-Host "Host: $dbHost" -ForegroundColor Gray
Write-Host "Porta: $port" -ForegroundColor Gray
Write-Host "Banco: $database" -ForegroundColor Gray
Write-Host "Usuario: $username" -ForegroundColor Gray

try {
    # Executar o script SQL
    $env:PGPASSWORD = $password
    $psqlPath = "C:\Program Files\PostgreSQL\17\bin\psql.exe"
    $result = & $psqlPath -h $dbHost -p $port -U $username -d $database -f "insert_agendamentos.sql"
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "`n✅ Dados de exemplo inseridos com sucesso!" -ForegroundColor Green
        Write-Host "`n📊 Agendamentos criados:" -ForegroundColor Cyan
        Write-Host "- 14/03/2024 09:00 - João Silva - Rex - Consulta Veterinária" -ForegroundColor White
        Write-Host "- 14/03/2024 10:30 - Maria Santos - Mimi - Vacinação" -ForegroundColor White
        Write-Host "- 15/03/2024 14:00 - Pedro Oliveira - Bella - Cirurgia de Castração" -ForegroundColor White
        Write-Host "- 15/03/2024 16:00 - João Silva - Luna - Banho e Tosa" -ForegroundColor White
        
        Write-Host "`n🔗 Chaves estrangeiras validadas:" -ForegroundColor Cyan
        Write-Host "- Todos os clientes existem na tab_cliente" -ForegroundColor White
        Write-Host "- Todos os animais existem na tab_animais" -ForegroundColor White
        Write-Host "- Todos os serviços existem na tab_servicos" -ForegroundColor White
    } else {
        Write-Host "`n❌ Erro ao inserir os dados!" -ForegroundColor Red
        Write-Host "Código de saída: $LASTEXITCODE" -ForegroundColor Red
    }
} catch {
    Write-Host "`n❌ Erro durante a execução: $($_.Exception.Message)" -ForegroundColor Red
} finally {
    # Limpar variável de ambiente
    Remove-Item Env:PGPASSWORD -ErrorAction SilentlyContinue
}

Write-Host "`n===============================================" -ForegroundColor Cyan
Write-Host "EXECUÇÃO CONCLUÍDA" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan

