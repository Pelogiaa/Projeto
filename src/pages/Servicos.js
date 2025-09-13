import React, { useState, useEffect } from 'react';
import { 
  Plus, 
  Search, 
  Edit, 
  Trash2
} from 'lucide-react';
import './Servicos.css';
import apiService from '../services/api';

const Servicos = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [editingService, setEditingService] = useState(null);
  const [servicos, setServicos] = useState([]);
  const [loading, setLoading] = useState(false);

  // Carregar dados da API
  useEffect(() => {
    const carregarServicos = async () => {
      try {
        setLoading(true);
        const servicosData = await apiService.buscarServicos();
        setServicos(servicosData);
      } catch (error) {
        console.error('Erro ao carregar serviços:', error);
        // Fallback para dados de exemplo em caso de erro
        setServicos([
          {
            id: 1,
            nome: 'Consulta Veterinária',
            descricao: 'Atendimento clínico completo para cães e gatos',
            categoria: 'Consultas',
            preco: 120.00,
            status: 'Ativo',
            observacoes: 'Inclui exame físico completo e orientações'
          },
          {
            id: 2,
            nome: 'Vacinação',
            descricao: 'Aplicação de vacinas essenciais para pets',
            categoria: 'Prevenção',
            preco: 80.00,
            status: 'Ativo',
            observacoes: 'Vacinas V8, V10, antirrábica e outras'
          },
          {
            id: 3,
            nome: 'Cirurgia de Castração',
            descricao: 'Procedimento cirúrgico para castração de cães e gatos',
            categoria: 'Cirurgias',
            preco: 350.00,
            status: 'Ativo',
            observacoes: 'Inclui anestesia e pós-operatório'
          },
          {
            id: 4,
            nome: 'Banho e Tosa',
            descricao: 'Serviço completo de higiene e estética animal',
            categoria: 'Estética',
            preco: 60.00,
            status: 'Ativo',
            observacoes: 'Banho, secagem, tosa e corte de unhas'
          },
          {
            id: 5,
            nome: 'Exames Laboratoriais',
            descricao: 'Coleta e análise de exames de sangue e urina',
            categoria: 'Diagnóstico',
            preco: 150.00,
            status: 'Inativo',
            observacoes: 'Hemograma completo e bioquímica'
          }
        ]);
      } finally {
        setLoading(false);
      }
    };

    carregarServicos();
  }, []);

  const filteredServicos = servicos.filter(servico =>
    servico.nome.toLowerCase().includes(searchTerm.toLowerCase())
  );


  const handleEdit = (servico) => {
    setEditingService(servico);
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este serviço?')) {
      try {
        await apiService.deletarServico(id);
        // Recarregar a lista de serviços
        const servicosData = await apiService.buscarServicos();
        setServicos(servicosData);
      } catch (error) {
        console.error('Erro ao excluir serviço:', error);
        alert('Erro ao excluir serviço. Tente novamente.');
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const dadosServico = {
      nome: formData.get('nome'),
      preco: parseFloat(formData.get('preco')),
      observacoes: formData.get('observacoes'),
      status: editingService ? editingService.status : 'Ativo'
    };

    try {
      if (editingService) {
        await apiService.atualizarServico(editingService.id, dadosServico);
      } else {
        await apiService.criarServico(dadosServico);
      }
      
      // Recarregar a lista de serviços
      const servicosData = await apiService.buscarServicos();
      setServicos(servicosData);
      
      // Fechar modal e limpar estado
      setShowModal(false);
      setEditingService(null);
    } catch (error) {
      console.error('Erro ao salvar serviço:', error);
      alert('Erro ao salvar serviço. Tente novamente.');
    }
  };

  const formatPrice = (price) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(price);
  };

  return (
    <div className="servicos">
      <div className="page-header">
        <div className="header-content">
          <h1 className="page-title">Serviços</h1>
          <p className="page-description">
            Gerencie os serviços oferecidos pela clínica
          </p>
        </div>
        <button 
          className="btn btn-primary"
          onClick={() => setShowModal(true)}
        >
          <Plus className="btn-icon" />
          Novo Serviço
        </button>
      </div>

      <div className="page-content">
        {/* Filtros e Busca */}
        <div className="filters-section">
          <div className="search-box">
            <Search className="search-icon" />
            <input
              type="text"
              placeholder="Buscar por nome..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>
        </div>

        {/* Tabela de Serviços */}
        <div className="services-table-container">
          <table className="services-table">
            <thead>
              <tr>
                <th>Nome</th>
                <th>Preço</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {filteredServicos.length === 0 ? (
                <tr>
                  <td colSpan="3" className="no-data">
                    Nenhum serviço encontrado
                  </td>
                </tr>
              ) : (
                filteredServicos.map((servico) => {
                  return (
                    <tr key={servico.id}>
                      <td>
                        <div className="service-name-cell">
                          <div className="service-info">
                            <span className="service-name">{servico.nome}</span>
                          </div>
                        </div>
                      </td>
                      <td>
                        <span className="service-price">{formatPrice(servico.preco)}</span>
                      </td>
                      <td>
                        <div className="service-actions">
                          <button 
                            className="action-btn edit"
                            onClick={() => handleEdit(servico)}
                            title="Editar"
                          >
                            <Edit className="action-icon" />
                          </button>
                          <button 
                            className="action-btn delete"
                            onClick={() => handleDelete(servico.id)}
                            title="Excluir"
                          >
                            <Trash2 className="action-icon" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  );
                })
              )}
            </tbody>
          </table>
        </div>

        {/* Modal de Serviço */}
        {showModal && (
          <div className="modal-overlay">
            <div className="modal">
              <div className="modal-header">
                <h3>{editingService ? 'Editar Serviço' : 'Novo Serviço'}</h3>
                <button 
                  className="modal-close"
                  onClick={() => {
                    setShowModal(false);
                    setEditingService(null);
                  }}
                >
                  ×
                </button>
              </div>
              <div className="modal-content">
                <form className="service-form" onSubmit={handleSubmit}>
                  <div className="form-group">
                    <label htmlFor="nome">Nome do Serviço</label>
                    <input
                      type="text"
                      id="nome"
                      name="nome"
                      defaultValue={editingService?.nome || ''}
                      required
                    />
                  </div>
                  
                  <div className="form-group">
                    <label htmlFor="preco">Preço (R$)</label>
                    <input
                      type="number"
                      id="preco"
                      name="preco"
                      min="0"
                      step="0.01"
                      defaultValue={editingService?.preco || ''}
                      required
                    />
                  </div>
                  
                  <div className="form-group">
                    <label htmlFor="observacoes">Observações</label>
                    <textarea
                      id="observacoes"
                      name="observacoes"
                      rows="3"
                      defaultValue={editingService?.observacoes || ''}
                    />
                  </div>
                  
                  <div className="form-actions">
                    <button 
                      type="button" 
                      className="btn btn-outline"
                      onClick={() => {
                        setShowModal(false);
                        setEditingService(null);
                      }}
                    >
                      Cancelar
                    </button>
                    <button type="submit" className="btn btn-primary">
                      {editingService ? 'Atualizar' : 'Cadastrar'}
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Servicos;