# Script para executar o SQL de remoção da coluna categoria
# Tenta diferentes caminhos do PostgreSQL

$sqlFile = "remove_categoria_column.sql"
$database = "projeto_clinica_veterinaria"
$username = "postgres"

# Possíveis caminhos do psql
$psqlPaths = @(
    "C:\Program Files\PostgreSQL\17\bin\psql.exe",
    "C:\Program Files\PostgreSQL\16\bin\psql.exe",
    "C:\Program Files\PostgreSQL\15\bin\psql.exe",
    "C:\Program Files\PostgreSQL\14\bin\psql.exe",
    "C:\Program Files\PostgreSQL\13\bin\psql.exe",
    "C:\Program Files\PostgreSQL\12\bin\psql.exe",
    "C:\Program Files\PostgreSQL\11\bin\psql.exe",
    "C:\Program Files\PostgreSQL\10\bin\psql.exe"
)

# Possíveis senhas
$passwords = @("postgres", "123456", "admin", "password", "")

Write-Host "🔍 Procurando PostgreSQL instalado..." -ForegroundColor Yellow

$psqlFound = $false
foreach ($path in $psqlPaths) {
    if (Test-Path $path) {
        Write-Host "✅ PostgreSQL encontrado em: $path" -ForegroundColor Green
        $psqlFound = $true
        
        foreach ($password in $passwords) {
            Write-Host "🔐 Tentando conectar com senha: '$password'" -ForegroundColor Cyan
            
            if ($password -eq "") {
                $env:PGPASSWORD = $null
                $result = & $path -h localhost -U $username -d $database -f $sqlFile 2>&1
            } else {
                $env:PGPASSWORD = $password
                $result = & $path -h localhost -U $username -d $database -f $sqlFile 2>&1
            }
            
            if ($LASTEXITCODE -eq 0) {
                Write-Host "✅ Script executado com sucesso!" -ForegroundColor Green
                Write-Host "📋 Resultado:" -ForegroundColor White
                Write-Host $result
                exit 0
            } else {
                Write-Host "❌ Falha na conexão com senha: '$password'" -ForegroundColor Red
                Write-Host "Erro: $result" -ForegroundColor Red
            }
        }
        break
    }
}

if (-not $psqlFound) {
    Write-Host "❌ PostgreSQL não encontrado nos caminhos padrão!" -ForegroundColor Red
    Write-Host "📋 Instruções manuais:" -ForegroundColor Yellow
    Write-Host "1. Abra o pgAdmin 4" -ForegroundColor White
    Write-Host "2. Conecte ao banco 'projeto_clinica_veterinaria'" -ForegroundColor White
    Write-Host "3. Abra o Query Tool (F5)" -ForegroundColor White
    Write-Host "4. Execute o arquivo: $sqlFile" -ForegroundColor White
}
