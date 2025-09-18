# Script PowerShell para executar a criação da tabela de agendamentos
# Projeto: Clínica Veterinária

Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "CRIAÇÃO DA TABELA DE AGENDAMENTOS" -ForegroundColor Cyan
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
    $result = & $psqlPath -h $dbHost -p $port -U $username -d $database -f "create_agendamentos_table.sql"
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "`n✅ Tabela tab_agendamentos criada com sucesso!" -ForegroundColor Green
        Write-Host "`nEstrutura da tabela:" -ForegroundColor Cyan
        Write-Host "- id (PK): Identificador único" -ForegroundColor White
        Write-Host "- data_agendamento: Data do agendamento" -ForegroundColor White
        Write-Host "- hora_agendamento: Hora do agendamento" -ForegroundColor White
        Write-Host "- id_cliente (FK): Referência para tab_cliente" -ForegroundColor White
        Write-Host "- id_animal (FK): Referência para tab_animais" -ForegroundColor White
        Write-Host "- id_servico (FK): Referência para tab_servicos" -ForegroundColor White
        Write-Host "- observacoes: Observações adicionais" -ForegroundColor White
        Write-Host "- status: Status do agendamento" -ForegroundColor White
        Write-Host "- data_cadastro: Data de criação" -ForegroundColor White
        Write-Host "- data_atualizacao: Data de atualização" -ForegroundColor White
        
        Write-Host "`n🔗 Chaves estrangeiras configuradas:" -ForegroundColor Cyan
        Write-Host "- Cliente -> tab_cliente(id)" -ForegroundColor White
        Write-Host "- Animal -> tab_animais(id)" -ForegroundColor White
        Write-Host "- Servico -> tab_servicos(id)" -ForegroundColor White
        
        Write-Host "`n📊 Dados de exemplo inseridos com sucesso!" -ForegroundColor Green
    } else {
        Write-Host "`n❌ Erro ao criar a tabela!" -ForegroundColor Red
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
