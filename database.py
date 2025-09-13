# =====================================================
# MÓDULO DE CONEXÃO COM BANCO DE DADOS
# Projeto: Clínica Veterinária
# =====================================================

import psycopg2
import psycopg2.extras
from contextlib import contextmanager
from typing import List, Dict, Any, Optional
import logging
from config import DatabaseConfig

# Configurar logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class DatabaseManager:
    """Gerenciador de conexões e operações com o banco de dados"""
    
    def __init__(self):
        self.config = DatabaseConfig()
    
    @contextmanager
    def get_connection(self):
        """Context manager para gerenciar conexões com o banco"""
        conn = None
        try:
            conn = psycopg2.connect(**self.config.get_connection_params())
            yield conn
        except psycopg2.Error as e:
            logger.error(f"Erro ao conectar com o banco de dados: {e}")
            if conn:
                conn.rollback()
            raise
        finally:
            if conn:
                conn.close()
    
    def execute_sql_file(self, file_path: str) -> bool:
        """Executa um arquivo SQL"""
        try:
            with open(file_path, 'r', encoding='utf-8') as file:
                sql_commands = file.read()
            
            with self.get_connection() as conn:
                with conn.cursor() as cursor:
                    cursor.execute(sql_commands)
                    conn.commit()
                    logger.info(f"Arquivo SQL {file_path} executado com sucesso!")
                    return True
        except Exception as e:
            logger.error(f"Erro ao executar arquivo SQL {file_path}: {e}")
            return False
    
    def test_connection(self) -> bool:
        """Testa a conexão com o banco de dados"""
        try:
            with self.get_connection() as conn:
                with conn.cursor() as cursor:
                    cursor.execute("SELECT version();")
                    version = cursor.fetchone()
                    logger.info(f"Conexão estabelecida! Versão do PostgreSQL: {version[0]}")
                    return True
        except Exception as e:
            logger.error(f"Erro ao conectar com o banco: {e}")
            return False

class ClienteDAO:
    """Data Access Object para operações com clientes"""
    
    def __init__(self, db_manager: DatabaseManager):
        self.db = db_manager
    
    def criar_cliente(self, nome: str, email: str, telefone: str, endereco: str, cpf: str) -> bool:
        """Cria um novo cliente"""
        try:
            with self.db.get_connection() as conn:
                with conn.cursor() as cursor:
                    cursor.execute("""
                        INSERT INTO tab_clientes (nome, email, telefone, endereco, cpf)
                        VALUES (%s, %s, %s, %s, %s)
                    """, (nome, email, telefone, endereco, cpf))
                    conn.commit()
                    logger.info(f"Cliente {nome} criado com sucesso!")
                    return True
        except psycopg2.IntegrityError as e:
            logger.error(f"Erro de integridade ao criar cliente: {e}")
            return False
        except Exception as e:
            logger.error(f"Erro ao criar cliente: {e}")
            return False
    
    def buscar_cliente_por_id(self, cliente_id: int) -> Optional[Dict[str, Any]]:
        """Busca um cliente pelo ID"""
        try:
            with self.db.get_connection() as conn:
                with conn.cursor(cursor_factory=psycopg2.extras.RealDictCursor) as cursor:
                    cursor.execute("SELECT * FROM tab_clientes WHERE id = %s", (cliente_id,))
                    cliente = cursor.fetchone()
                    return dict(cliente) if cliente else None
        except Exception as e:
            logger.error(f"Erro ao buscar cliente por ID: {e}")
            return None
    
    def buscar_todos_clientes(self) -> List[Dict[str, Any]]:
        """Busca todos os clientes"""
        try:
            with self.db.get_connection() as conn:
                with conn.cursor(cursor_factory=psycopg2.extras.RealDictCursor) as cursor:
                    cursor.execute("SELECT * FROM tab_clientes ORDER BY nome")
                    clientes = cursor.fetchall()
                    return [dict(cliente) for cliente in clientes]
        except Exception as e:
            logger.error(f"Erro ao buscar todos os clientes: {e}")
            return []
    
    def atualizar_cliente(self, cliente_id: int, **kwargs) -> bool:
        """Atualiza dados de um cliente"""
        try:
            # Construir query dinamicamente baseada nos campos fornecidos
            campos = []
            valores = []
            for campo, valor in kwargs.items():
                if campo in ['nome', 'email', 'telefone', 'endereco', 'cpf']:
                    campos.append(f"{campo} = %s")
                    valores.append(valor)
            
            if not campos:
                logger.warning("Nenhum campo válido fornecido para atualização")
                return False
            
            # Adicionar data_atualizacao
            campos.append("data_atualizacao = CURRENT_TIMESTAMP")
            valores.append(cliente_id)
            
            query = f"UPDATE tab_clientes SET {', '.join(campos)} WHERE id = %s"
            
            with self.db.get_connection() as conn:
                with conn.cursor() as cursor:
                    cursor.execute(query, valores)
                    conn.commit()
                    logger.info(f"Cliente ID {cliente_id} atualizado com sucesso!")
                    return True
        except Exception as e:
            logger.error(f"Erro ao atualizar cliente: {e}")
            return False
    
    def deletar_cliente(self, cliente_id: int) -> bool:
        """Deleta um cliente"""
        try:
            with self.db.get_connection() as conn:
                with conn.cursor() as cursor:
                    cursor.execute("DELETE FROM tab_clientes WHERE id = %s", (cliente_id,))
                    conn.commit()
                    logger.info(f"Cliente ID {cliente_id} deletado com sucesso!")
                    return True
        except Exception as e:
            logger.error(f"Erro ao deletar cliente: {e}")
            return False

def main():
    """Função principal para testar a conexão e executar o setup"""
    print("=== CLÍNICA VETERINÁRIA - SETUP DO BANCO DE DADOS ===\n")
    
    # Criar instância do gerenciador de banco
    db_manager = DatabaseManager()
    
    # Testar conexão
    print("1. Testando conexão com o banco de dados...")
    if db_manager.test_connection():
        print("✅ Conexão estabelecida com sucesso!\n")
    else:
        print("❌ Falha na conexão. Verifique as configurações.\n")
        return
    
    # Executar script de setup
    print("2. Executando script de criação do banco e tabelas...")
    if db_manager.execute_sql_file('setup_database.sql'):
        print("✅ Banco de dados e tabelas criados com sucesso!\n")
    else:
        print("❌ Falha ao executar script de setup.\n")
        return
    
    # Testar operações CRUD
    print("3. Testando operações com clientes...")
    cliente_dao = ClienteDAO(db_manager)
    
    # Listar clientes existentes
    clientes = cliente_dao.buscar_todos_clientes()
    print(f"📋 Total de clientes cadastrados: {len(clientes)}")
    
    if clientes:
        print("\n📝 Clientes cadastrados:")
        for cliente in clientes:
            print(f"   - ID: {cliente['id']} | Nome: {cliente['nome']} | Email: {cliente['email']}")
    
    print("\n✅ Setup concluído com sucesso!")

if __name__ == "__main__":
    main()

