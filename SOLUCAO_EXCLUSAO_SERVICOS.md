# Solução para Erro ao Excluir Serviços

## Problema Identificado
O erro ao excluir serviços ocorria porque a tabela `tab_agendamentos` possui uma chave estrangeira (`id_servico`) que referencia a tabela `tab_servicos` com a constraint `ON DELETE RESTRICT`. Isso impede a exclusão de serviços que possuem agendamentos vinculados.

## Solução Implementada

### 1. Criação do Modelo Agendamento
- **Arquivo**: `src/main/java/com/clinicaveterinaria/model/Agendamento.java`
- Mapeia a tabela `tab_agendamentos` para JPA
- Inclui todos os campos necessários com anotações apropriadas

### 2. Criação do Repository de Agendamentos
- **Arquivo**: `src/main/java/com/clinicaveterinaria/repository/AgendamentoRepository.java`
- Métodos para verificar e contar agendamentos vinculados a serviços
- Queries otimizadas para verificação de integridade referencial

### 3. Atualização do ServicoService
- **Arquivo**: `src/main/java/com/clinicaveterinaria/service/ServicoService.java`
- Adicionada verificação antes da exclusão
- Mensagem de erro informativa com quantidade de agendamentos vinculados
- Métodos auxiliares para verificação de integridade

## Funcionalidades Implementadas

### ✅ Verificação de Agendamentos Vinculados
- Antes de excluir um serviço, o sistema verifica se existem agendamentos vinculados
- Retorna erro informativo com a quantidade de agendamentos encontrados

### ✅ Mensagem de Erro Clara
```
"Não é possível excluir o serviço. Existem X agendamento(s) vinculado(s) a este serviço. 
Primeiro, exclua ou altere os agendamentos relacionados, ou desative o serviço em vez de excluí-lo."
```

### ✅ Alternativa: Desativar Serviço
- Em vez de excluir, o usuário pode desativar o serviço
- Serviços inativos não aparecem em dropdowns e listas ativas
- Mantém a integridade dos dados históricos

## Testes Realizados

### ✅ Teste 1: Exclusão de Serviço com Agendamentos
- **Serviço**: Consulta Veterinária (ID: 21)
- **Resultado**: Erro 400 com mensagem informativa
- **Status**: ✅ Funcionando

### ✅ Teste 2: Exclusão de Serviço sem Agendamentos
- **Serviço**: Teste Exclusão (ID: 273)
- **Resultado**: Exclusão bem-sucedida
- **Status**: ✅ Funcionando

### ✅ Teste 3: Desativação de Serviço
- **Serviço**: Consulta Veterinária (ID: 21)
- **Resultado**: Status alterado para "Inativo"
- **Status**: ✅ Funcionando

## Estrutura do Banco de Dados

### Tabela tab_agendamentos
```sql
CREATE TABLE tab_agendamentos (
    id SERIAL PRIMARY KEY,
    data_agendamento DATE NOT NULL,
    hora_agendamento TIME NOT NULL,
    id_cliente INTEGER NOT NULL,
    id_animal INTEGER NOT NULL,
    id_servico INTEGER NOT NULL,
    observacoes TEXT,
    status VARCHAR(20) DEFAULT 'Agendado',
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_agendamentos_cliente FOREIGN KEY (id_cliente) REFERENCES tab_cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_agendamentos_animal FOREIGN KEY (id_animal) REFERENCES tab_animais(id) ON DELETE CASCADE,
    CONSTRAINT fk_agendamentos_servico FOREIGN KEY (id_servico) REFERENCES tab_servicos(id) ON DELETE RESTRICT
);
```

## Benefícios da Solução

1. **Integridade de Dados**: Preserva a integridade referencial do banco
2. **Experiência do Usuário**: Mensagens claras sobre por que a exclusão falhou
3. **Flexibilidade**: Oferece alternativa (desativação) em vez de exclusão
4. **Auditoria**: Mantém histórico de serviços mesmo quando "removidos"
5. **Performance**: Verificações otimizadas com queries específicas

## Como Usar

### Para Excluir um Serviço:
1. O sistema verifica automaticamente se há agendamentos vinculados
2. Se houver agendamentos: retorna erro com orientações
3. Se não houver agendamentos: exclui normalmente

### Para "Remover" um Serviço com Agendamentos:
1. Use a funcionalidade de desativar serviço (`PATCH /api/servicos/{id}/status`)
2. O serviço ficará inativo e não aparecerá em listas ativas
3. Os agendamentos existentes continuam válidos

## Arquivos Modificados/Criados

- ✅ `src/main/java/com/clinicaveterinaria/model/Agendamento.java` (NOVO)
- ✅ `src/main/java/com/clinicaveterinaria/repository/AgendamentoRepository.java` (NOVO)
- ✅ `src/main/java/com/clinicaveterinaria/service/ServicoService.java` (MODIFICADO)
- ✅ `create_agendamentos_table.sql` (NOVO)
- ✅ `insert_agendamentos.sql` (NOVO)
- ✅ `execute_agendamentos_table.ps1` (NOVO)
- ✅ `insert_sample_agendamentos.ps1` (NOVO)

