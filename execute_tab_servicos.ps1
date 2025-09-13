# Script PowerShell para executar SQL no PostgreSQL
$psqlPath = "C:\Program Files\PostgreSQL\16\bin\psql.exe"

# Verificar se o psql existe
if (Test-Path $psqlPath) {
    Write-Host "Executando script SQL para criar tabela tab_servicos..."
    
    # Executar o script SQL
    & $psqlPath -U postgres -d projeto_clinica_veterinaria -f create_tab_servicos.sql
    
    Write-Host "Script executado com sucesso!"
} else {
    Write-Host "PostgreSQL não encontrado no caminho padrão. Verificando outras versões..."
    
    # Tentar outras versões comuns
    $possiblePaths = @(
        "C:\Program Files\PostgreSQL\15\bin\psql.exe",
        "C:\Program Files\PostgreSQL\14\bin\psql.exe",
        "C:\Program Files\PostgreSQL\13\bin\psql.exe",
        "C:\Program Files\PostgreSQL\12\bin\psql.exe"
    )
    
    foreach ($path in $possiblePaths) {
        if (Test-Path $path) {
            Write-Host "Encontrado PostgreSQL em: $path"
            & $path -U postgres -d projeto_clinica_veterinaria -f create_tab_servicos.sql
            break
        }
    }
}
