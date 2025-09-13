import React, { useState, useEffect } from 'react';
import { 
  Plus, 
  Search, 
  Edit, 
  Trash2
} from 'lucide-react';
import './Agendamentos.css';
import apiService from '../services/api';

const Agendamentos = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [editingAppointment, setEditingAppointment] = useState(null);
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0]);
  const [clientes, setClientes] = useState([]);
  const [animais, setAnimais] = useState([]);
  const [servicos, setServicos] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedServico, setSelectedServico] = useState(null);

  // Carregar dados da API
  useEffect(() => {
    const carregarDados = async () => {
      try {
        setLoading(true);
        const [clientesData, animaisData, servicosData] = await Promise.all([
          apiService.buscarClientesDropdown(),
          apiService.buscarAnimais(),
          apiService.buscarServicosDropdown()
        ]);
        setClientes(clientesData);
        setAnimais(animaisData);
        setServicos(servicosData);
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
      } finally {
        setLoading(false);
      }
    };

    carregarDados();
  }, []);

  // Dados de exemplo
  const [agendamentos] = useState([
    {
      id: 1,
      data: '2024-03-15',
      horario: '09:00',
      cliente: 'João Silva',
      animal: 'Rex',
      servico: 'Consulta Veterinária',
      preco: 120.00,
      veterinario: 'Dr. Maria Santos',
      status: 'Agendado',
      observacoes: 'Primeira consulta, animal nervoso'
    },
    {
      id: 2,
      data: '2024-03-15',
      horario: '10:30',
      cliente: 'Maria Santos',
      animal: 'Mimi',
      servico: 'Vacinação',
      preco: 80.00,
      veterinario: 'Dr. Pedro Oliveira',
      status: 'Confirmado',
      observacoes: 'Vacina V10'
    },
    {
      id: 3,
      data: '2024-03-16',
      horario: '14:00',
      cliente: 'Pedro Oliveira',
      animal: 'Thor',
      servico: 'Cirurgia de Castração',
      preco: 350.00,
      veterinario: 'Dr. Ana Costa',
      status: 'Realizado',
      observacoes: 'Cirurgia realizada com sucesso'
    },
    {
      id: 4,
      data: '2024-03-16',
      horario: '16:00',
      cliente: 'João Silva',
      animal: 'Rex',
      servico: 'Banho e Tosa',
      preco: 60.00,
      veterinario: 'Tosador',
      status: 'Cancelado',
      observacoes: 'Cliente cancelou por motivos pessoais'
    }
  ]);

  const filteredAgendamentos = agendamentos.filter(agendamento =>
    agendamento.cliente.toLowerCase().includes(searchTerm.toLowerCase()) ||
    agendamento.animal.toLowerCase().includes(searchTerm.toLowerCase()) ||
    agendamento.servico.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleEdit = (agendamento) => {
    setEditingAppointment(agendamento);
    setShowModal(true);
  };

  const handleDelete = (id) => {
    if (window.confirm('Tem certeza que deseja excluir este agendamento?')) {
      console.log('Excluir agendamento:', id);
    }
  };

  // Função para lidar com mudança de serviço
  const handleServicoChange = (servicoId) => {
    const servico = servicos.find(s => s.id === parseInt(servicoId));
    setSelectedServico(servico);
  };

  // Função para formatar preço
  const formatPrice = (price) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(price);
  };



  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  const formatTime = (timeString) => {
    return timeString;
  };


  return (
    <div className="agendamentos">
      <div className="page-header">
        <div className="header-content">
          <h1 className="page-title">Agendamentos</h1>
          <p className="page-description">
            Gerencie os agendamentos e consultas da clínica
          </p>
        </div>
        <button 
          className="btn btn-primary"
          onClick={() => setShowModal(true)}
        >
          <Plus className="btn-icon" />
          Novo Agendamento
        </button>
      </div>

      <div className="page-content">
        {/* Filtros e Busca */}
        <div className="filters-section">
          <div className="search-box">
            <Search className="search-icon" />
            <input
              type="text"
              placeholder="Buscar por cliente, animal ou serviço..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>
          <div className="filter-actions">
            <div className="date-filter">
              <label htmlFor="date-filter">Data:</label>
              <input
                type="date"
                id="date-filter"
                value={selectedDate}
                onChange={(e) => setSelectedDate(e.target.value)}
                className="date-input"
              />
            </div>
          </div>
        </div>


        {/* Tabela de Agendamentos */}
        <div className="appointments-table-container">
          <table className="appointments-table">
            <thead>
              <tr>
                <th>Data/Hora</th>
                <th>Cliente</th>
                <th>Animal</th>
                <th>Serviço</th>
                <th>Preço</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {filteredAgendamentos.length === 0 ? (
                <tr>
                  <td colSpan="6" className="no-data">
                    Nenhum agendamento encontrado
                  </td>
                </tr>
              ) : (
                filteredAgendamentos.map((agendamento) => (
                  <tr key={agendamento.id}>
                    <td>
                      <div className="appointment-datetime">
                        <div className="datetime-info">
                          <div className="datetime-details">
                            <span className="time-value">{formatTime(agendamento.horario)}</span>
                            <span className="date-value">{formatDate(agendamento.data)}</span>
                          </div>
                        </div>
                      </div>
                    </td>
                    <td>
                      <div className="appointment-client">
                        <span className="client-name">{agendamento.cliente}</span>
                      </div>
                    </td>
                    <td>
                      <div className="appointment-animal">
                        <span className="animal-name">{agendamento.animal}</span>
                      </div>
                    </td>
                    <td>
                      <span className="service-name">{agendamento.servico}</span>
                    </td>
                    <td>
                      <span className="service-price">{formatPrice(agendamento.preco)}</span>
                    </td>
                    <td>
                      <div className="appointment-actions">
                        <button 
                          className="action-btn edit"
                          onClick={() => handleEdit(agendamento)}
                          title="Editar"
                        >
                          <Edit className="action-icon" />
                        </button>
                        <button 
                          className="action-btn delete"
                          onClick={() => handleDelete(agendamento.id)}
                          title="Excluir"
                        >
                          <Trash2 className="action-icon" />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        {/* Modal de Agendamento */}
        {showModal && (
          <div className="modal-overlay">
            <div className="modal">
              <div className="modal-header">
                <h3>{editingAppointment ? 'Editar Agendamento' : 'Novo Agendamento'}</h3>
                <button 
                  className="modal-close"
                  onClick={() => {
                    setShowModal(false);
                    setEditingAppointment(null);
                  }}
                >
                  ×
                </button>
              </div>
              <div className="modal-content">
                <form className="appointment-form">
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="data">Data</label>
                      <input
                        type="date"
                        id="data"
                        defaultValue={editingAppointment?.data || ''}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="horario">Horário</label>
                      <input
                        type="time"
                        id="horario"
                        defaultValue={editingAppointment?.horario || ''}
                        required
                      />
                    </div>
                  </div>
                  
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="cliente">Cliente</label>
                      <select
                        id="cliente"
                        defaultValue={editingAppointment?.cliente || ''}
                        required
                        disabled={loading}
                      >
                        <option value="">{loading ? 'Carregando...' : 'Selecione...'}</option>
                        {clientes.map((cliente) => (
                          <option key={cliente.id} value={cliente.nome}>
                            {cliente.nome} - {cliente.email}
                          </option>
                        ))}
                      </select>
                    </div>
                    <div className="form-group">
                      <label htmlFor="animal">Animal</label>
                      <select
                        id="animal"
                        defaultValue={editingAppointment?.animal || ''}
                        required
                        disabled={loading}
                      >
                        <option value="">{loading ? 'Carregando...' : 'Selecione...'}</option>
                        {animais.map((animal) => (
                          <option key={animal.id} value={animal.nomeAnimal}>
                            {animal.nomeAnimal} - {animal.tipoAnimal}
                          </option>
                        ))}
                      </select>
                    </div>
                  </div>
                  
                  <div className="form-group">
                    <label htmlFor="servico">Serviço</label>
                    <select
                      id="servico"
                      defaultValue={editingAppointment?.servico || ''}
                      onChange={(e) => handleServicoChange(e.target.value)}
                      required
                      disabled={loading}
                    >
                      <option value="">{loading ? 'Carregando...' : 'Selecione...'}</option>
                      {servicos.map((servico) => (
                        <option key={servico.id} value={servico.id}>
                          {servico.nome} - {formatPrice(servico.preco)}
                        </option>
                      ))}
                    </select>
                  </div>
                  
                  {/* Exibir preço do serviço selecionado */}
                  {selectedServico && (
                    <div className="form-group">
                      <label>Preço do Serviço</label>
                      <div className="price-display">
                        <span className="price-value">{formatPrice(selectedServico.preco)}</span>
                        <span className="price-category">({selectedServico.categoria})</span>
                      </div>
                    </div>
                  )}
                  
                  
                  
                  <div className="form-actions">
                    <button 
                      type="button" 
                      className="btn btn-outline"
                      onClick={() => {
                        setShowModal(false);
                        setEditingAppointment(null);
                      }}
                    >
                      Cancelar
                    </button>
                    <button type="submit" className="btn btn-primary">
                      {editingAppointment ? 'Atualizar' : 'Agendar'}
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

export default Agendamentos;

















