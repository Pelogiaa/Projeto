# Script para inserir dados de exemplo no banco de dados
$baseUrl = "http://localhost:8080/api"

# Dados de clientes de exemplo
$clientes = @(
    @{
        nome = "João Silva"
        email = "joao.silva@gmail.com"
        telefone = "11987654321"
        cep = "01234-567"
        cpf = "12345678901"
    },
    @{
        nome = "Maria Santos"
        email = "maria.santos@gmail.com"
        telefone = "11987654322"
        cep = "04567-890"
        cpf = "98765432109"
    },
    @{
        nome = "Pedro Oliveira"
        email = "pedro.oliveira@gmail.com"
        telefone = "11987654323"
        cep = "07890-123"
        cpf = "11122233344"
    }
)

# Inserir clientes
Write-Host "Inserindo clientes..."
foreach ($cliente in $clientes) {
    try {
        $response = Invoke-RestMethod -Uri "$baseUrl/clientes" -Method POST -Body ($cliente | ConvertTo-Json) -ContentType "application/json"
        Write-Host "Cliente inserido: $($cliente.nome) - ID: $($response.id)"
    } catch {
        Write-Host "Erro ao inserir cliente $($cliente.nome): $($_.Exception.Message)"
    }
}

# Aguardar um pouco para os clientes serem inseridos
Start-Sleep -Seconds 2

# Buscar clientes para obter os IDs
Write-Host "Buscando clientes inseridos..."
try {
    $clientesInseridos = Invoke-RestMethod -Uri "$baseUrl/clientes" -Method GET
    Write-Host "Clientes encontrados: $($clientesInseridos.Count)"
    
    # Dados de animais de exemplo
    $animais = @(
        @{
            nomeAnimal = "Rex"
            tipoAnimal = "Cão"
            raca = "Golden Retriever"
            idade = 3
            peso = 25.5
            cor = "Dourado"
            sexo = "Macho"
            idCliente = $clientesInseridos[0].id
            observacoes = "Animal muito dócil"
        },
        @{
            nomeAnimal = "Mimi"
            tipoAnimal = "Gato"
            raca = "Persa"
            idade = 2
            peso = 4.2
            cor = "Branco"
            sexo = "Fêmea"
            idCliente = $clientesInseridos[1].id
            observacoes = "Gata tranquila"
        },
        @{
            nomeAnimal = "Thor"
            tipoAnimal = "Cão"
            raca = "Pastor Alemão"
            idade = 5
            peso = 30.0
            cor = "Preto e Marrom"
            sexo = "Macho"
            idCliente = $clientesInseridos[2].id
            observacoes = "Cão muito ativo"
        }
    )
    
    # Inserir animais
    Write-Host "Inserindo animais..."
    foreach ($animal in $animais) {
        try {
            $response = Invoke-RestMethod -Uri "$baseUrl/animais" -Method POST -Body ($animal | ConvertTo-Json) -ContentType "application/json"
            Write-Host "Animal inserido: $($animal.nomeAnimal) - ID: $($response.id)"
        } catch {
            Write-Host "Erro ao inserir animal $($animal.nomeAnimal): $($_.Exception.Message)"
        }
    }
    
} catch {
    Write-Host "Erro ao buscar clientes: $($_.Exception.Message)"
}

Write-Host "Script concluído!"















