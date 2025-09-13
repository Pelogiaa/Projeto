# =====================================================
# CONFIGURAÇÕES DO BANCO DE DADOS
# Projeto: Clínica Veterinária
# =====================================================

import os
from dotenv import load_dotenv

# Carregar variáveis de ambiente do arquivo .env
load_dotenv()

class DatabaseConfig:
    """Configurações de conexão com o banco de dados PostgreSQL"""
    
    # Configurações do banco de dados
    DB_HOST = os.getenv('DB_HOST', 'localhost')
    DB_PORT = os.getenv('DB_PORT', '5432')
    DB_NAME = os.getenv('DB_NAME', 'projeto_clinica_veterinaria')
    DB_USER = os.getenv('DB_USER', 'postgres')
    DB_PASSWORD = os.getenv('DB_PASSWORD', '')
    
    # String de conexão
    @classmethod
    def get_connection_string(cls):
        """Retorna a string de conexão com o banco de dados"""
        return f"postgresql://{cls.DB_USER}:{cls.DB_PASSWORD}@{cls.DB_HOST}:{cls.DB_PORT}/{cls.DB_NAME}"
    
    @classmethod
    def get_connection_params(cls):
        """Retorna os parâmetros de conexão como dicionário"""
        return {
            'host': cls.DB_HOST,
            'port': cls.DB_PORT,
            'database': cls.DB_NAME,
            'user': cls.DB_USER,
            'password': cls.DB_PASSWORD
        }

# Configurações da aplicação
class AppConfig:
    """Configurações gerais da aplicação"""
    
    APP_NAME = "Clínica Veterinária"
    VERSION = "1.0.0"
    DEBUG = os.getenv('DEBUG', 'False').lower() == 'true'
    
    # Configurações de logging
    LOG_LEVEL = os.getenv('LOG_LEVEL', 'INFO')
    LOG_FILE = 'logs/clinica_veterinaria.log'

