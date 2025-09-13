#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Exemplo de uso do sistema Clínica Veterinária
"""

from database import DatabaseManager, ClienteDAO

def exemplo_crud_clientes():
    """Exemplo de operações CRUD com clientes"""
    print("🐾 EXEMPLO DE USO - CLÍNICA VETERINÁRIA")
    print("=" * 50)
    
    # Criar instâncias
    db_manager = DatabaseManager()
    cliente_dao = ClienteDAO(db_manager)
    
    # Testar conexão
    print("1. Testando conexão...")
    if not db_manager.test_connection():
        print("❌ Erro na conexão. Verifique as configurações.")
        return
    
    print("✅ Conexão estabelecida!\n")
    
    # Criar um novo cliente
    print("2. Criando novo cliente...")
    sucesso = cliente_dao.criar_cliente(
        nome="Ana Costa",
        email="ana.costa@email.com",
        telefone="(11) 66666-6666",
        endereco="Rua dos Animais, 456 - São Paulo/SP",
        cpf="111.222.333-44"
    )
    
    if sucesso:
        print("✅ Cliente criado com sucesso!")
    else:
        print("❌ Erro ao criar cliente")
    
    # Listar todos os clientes
    print("\n3. Listando todos os clientes...")
    clientes = cliente_dao.buscar_todos_clientes()
    print(f"📋 Total de clientes: {len(clientes)}")
    
    for cliente in clientes:
        print(f"   - ID: {cliente['id']} | Nome: {cliente['nome']} | Email: {cliente['email']}")
    
    # Buscar cliente específico
    if clientes:
        print(f"\n4. Buscando cliente ID {clientes[0]['id']}...")
        cliente = cliente_dao.buscar_cliente_por_id(clientes[0]['id'])
        if cliente:
            print(f"✅ Cliente encontrado: {cliente['nome']} - {cliente['telefone']}")
        
        # Atualizar cliente
        print(f"\n5. Atualizando telefone do cliente ID {cliente['id']}...")
        sucesso = cliente_dao.atualizar_cliente(
            cliente['id'], 
            telefone="(11) 99999-0000"
        )
        
        if sucesso:
            print("✅ Cliente atualizado com sucesso!")
            
            # Verificar atualização
            cliente_atualizado = cliente_dao.buscar_cliente_por_id(cliente['id'])
            print(f"   Novo telefone: {cliente_atualizado['telefone']}")
    
    print("\n✅ Exemplo concluído!")

if __name__ == "__main__":
    exemplo_crud_clientes()
