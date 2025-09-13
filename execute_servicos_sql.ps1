# Script para executar SQL de criação da tabela de serviços
Write-Host "Executando script SQL para criar tabela de serviços..." -ForegroundColor Yellow

# Tentar encontrar o psql
$psqlPath = Get-Command psql -ErrorAction SilentlyContinue
if (-not $psqlPath) {
    Write-Host "psql não encontrado. Tentando localizar PostgreSQL..." -ForegroundColor Yellow
    
    # Procurar em locais comuns
    $possiblePaths = @(
        "C:\Program Files\PostgreSQL\*\bin\psql.exe",
        "C:\Program Files (x86)\PostgreSQL\*\bin\psql.exe",
        "C:\PostgreSQL\*\bin\psql.exe"
    )
    
    foreach ($path in $possiblePaths) {
        $found = Get-ChildItem -Path $path -ErrorAction SilentlyContinue | Select-Object -First 1
        if ($found) {
            $psqlPath = $found.FullName
            break
        }
    }
}

if (-not $psqlPath) {
    Write-Host "ERRO: PostgreSQL não encontrado!" -ForegroundColor Red
    Write-Host "Por favor, instale o PostgreSQL ou adicione o psql ao PATH." -ForegroundColor Red
    exit 1
}

Write-Host "Usando psql: $psqlPath" -ForegroundColor Green

# Executar o script SQL
try {
    & $psqlPath -h localhost -U postgres -d projeto_clinica_veterinaria -f create_servicos_table.sql
    Write-Host "Script executado com sucesso!" -ForegroundColor Green
} catch {
    Write-Host "Erro ao executar script: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host "Tabela de serviços criada com sucesso!" -ForegroundColor Green
