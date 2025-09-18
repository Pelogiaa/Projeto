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
  const [animaisFiltrados, setAnimaisFiltrados] = useState([]);
  const [servicos, setServicos] = useState([]);
  const [agendamentos, setAgendamentos] = useState([]);
  const [clienteSelecionado, setClienteSelecionado] = useState('');
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showErrorModal, setShowErrorModal] = useState(false);

  // Função para filtrar animais por cliente
  const filtrarAnimaisPorCliente = (idCliente) => {
    if (idCliente) {
      const animaisDoCliente = animais.filter(animal => animal.idCliente === parseInt(idCliente));
      setAnimaisFiltrados(animaisDoCliente);
    } else {
      setAnimaisFiltrados(animais);
    }
  };

  // Função para lidar com mudança de cliente
  const handleClienteChange = (e) => {
    const idCliente = e.target.value;
    setClienteSelecionado(idCliente);
    filtrarAnimaisPorCliente(idCliente);
  };

  // Carregar dados da API
  useEffect(() => {
    const carregarDados = async () => {
      try {
        setLoading(true);
        const [clientesData, animaisData, servicosData, agendamentosData] = await Promise.all([
          apiService.buscarClientesDropdown(),
          apiService.buscarAnimais(),
          apiService.buscarServicosDropdown(),
          apiService.buscarAgendamentos()
        ]);
        setClientes(clientesData);
        setAnimais(animaisData);
        setAnimaisFiltrados(animaisData);
        setServicos(servicosData);
        setAgendamentos(agendamentosData);
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
        setErrorMessage('Erro ao carregar dados. Tente novamente.');
        setShowErrorModal(true);
      } finally {
        setLoading(false);
      }
    };

    carregarDados();
  }, []);

  // Filtrar agendamentos
  const filteredAgendamentos = agendamentos.filter(agendamento =>
    (agendamento.nomeCliente && agendamento.nomeCliente.toLowerCase().includes(searchTerm.toLowerCase())) ||
    (agendamento.nomeAnimal && agendamento.nomeAnimal.toLowerCase().includes(searchTerm.toLowerCase())) ||
    (agendamento.nomeServico && agendamento.nomeServico.toLowerCase().includes(searchTerm.toLowerCase()))
  );

  const handleEdit = (agendamento) => {
    setEditingAppointment(agendamento);
    // Encontrar o cliente do animal selecionado
    const animal = animais.find(a => a.id === agendamento.idAnimal);
    if (animal) {
      setClienteSelecionado(animal.idCliente.toString());
      filtrarAnimaisPorCliente(animal.idCliente.toString());
    }
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este agendamento?')) {
      try {
        await apiService.deletarAgendamento(id);
        // Recarregar a lista de agendamentos
        const agendamentosData = await apiService.buscarAgendamentos();
        setAgendamentos(agendamentosData);
      } catch (error) {
        console.error('Erro ao excluir agendamento:', error);
        setErrorMessage(error.message || 'Erro ao excluir agendamento. Tente novamente.');
        setShowErrorModal(true);
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      setLoading(true);
      
      // Coletar dados do formulário
      const formData = new FormData(e.target);
      const dataAgendamento = formData.get('data');
      const horaAgendamento = formData.get('horario');
      const idAnimal = parseInt(formData.get('animal'));
      const idServico = parseInt(formData.get('servico'));

      // Validar se todos os campos foram preenchidos
      if (!dataAgendamento || !horaAgendamento || !clienteSelecionado || !idAnimal || !idServico) {
        setErrorMessage('Por favor, preencha todos os campos obrigatórios.');
        setShowErrorModal(true);
        return;
      }

      // Combinar data e hora em um LocalDateTime
      const dataHoraCombinada = `${dataAgendamento}T${horaAgendamento}:00`;
      
      const data = {
        dataAgendamento: dataHoraCombinada,
        idCliente: parseInt(clienteSelecionado),
        idAnimal: idAnimal,
        idServico: idServico
      };

      if (editingAppointment) {
        // Atualizar agendamento existente
        await apiService.atualizarAgendamento(editingAppointment.id, data);
      } else {
        // Criar novo agendamento
        await apiService.criarAgendamento(data);
      }

      // Recarregar a lista de agendamentos
      const agendamentosData = await apiService.buscarAgendamentos();
      setAgendamentos(agendamentosData);

      // Fechar modal e limpar dados
      setShowModal(false);
      setEditingAppointment(null);
      setClienteSelecionado('');
      setAnimaisFiltrados(animais);
      
    } catch (error) {
      console.error('Erro ao salvar agendamento:', error);
      setErrorMessage(error.message || 'Erro ao salvar agendamento. Tente novamente.');
      setShowErrorModal(true);
    } finally {
      setLoading(false);
    }
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

  const formatTime = (dateTimeString) => {
    if (!dateTimeString) return '';
    const date = new Date(dateTimeString);
    return date.toLocaleTimeString('pt-BR', {
      hour: '2-digit',
      minute: '2-digit'
    });
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
                            <span className="time-value">{formatTime(agendamento.dataAgendamento)}</span>
                            <span className="date-value">{formatDate(agendamento.dataAgendamento)}</span>
                          </div>
                        </div>
                      </div>
                    </td>
                    <td>
                      <div className="appointment-client">
                        <span className="client-name">{agendamento.nomeCliente}</span>
                      </div>
                    </td>
                    <td>
                      <div className="appointment-animal">
                        <span className="animal-name">{agendamento.nomeAnimal}</span>
                      </div>
                    </td>
                    <td>
                      <span className="service-name">{agendamento.nomeServico}</span>
                    </td>
                    <td>
                      <span className="service-price">{formatPrice(agendamento.precoServico)}</span>
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
                    setClienteSelecionado('');
                    setAnimaisFiltrados(animais);
                  }}
                >
                  ×
                </button>
              </div>
              <div className="modal-content">
                <form className="appointment-form" onSubmit={handleSubmit}>
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="data">Data</label>
                      <input
                        type="date"
                        id="data"
                        name="data"
                        defaultValue={editingAppointment?.dataAgendamento ? editingAppointment.dataAgendamento.split('T')[0] : ''}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="horario">Horário</label>
                      <input
                        type="time"
                        id="horario"
                        name="horario"
                        defaultValue={editingAppointment?.dataAgendamento ? editingAppointment.dataAgendamento.split('T')[1]?.substring(0, 5) : ''}
                        required
                      />
                    </div>
                  </div>
                  
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="cliente">Cliente</label>
                      <select
                        id="cliente"
                        name="cliente"
                        value={clienteSelecionado}
                        onChange={handleClienteChange}
                        required
                        disabled={loading}
                      >
                        <option value="">{loading ? 'Carregando...' : 'Selecione um cliente'}</option>
                        {clientes.map((cliente) => (
                          <option key={cliente.id} value={cliente.id}>
                            {cliente.nome} - {cliente.email}
                          </option>
                        ))}
                      </select>
                    </div>
                    <div className="form-group">
                      <label htmlFor="animal">Animal</label>
                      <select
                        id="animal"
                        name="animal"
                        defaultValue={editingAppointment?.idAnimal || ''}
                        required
                        disabled={loading || !clienteSelecionado}
                      >
                        <option value="">{!clienteSelecionado ? 'Selecione um cliente primeiro' : loading ? 'Carregando...' : 'Selecione um animal'}</option>
                        {animaisFiltrados.map((animal) => (
                          <option key={animal.id} value={animal.id}>
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
                      name="servico"
                      defaultValue={editingAppointment?.idServico || ''}
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
                  
                  
                  
                  
                  <div className="form-actions">
                    <button 
                      type="button" 
                      className="btn btn-outline"
                      onClick={() => {
                        setShowModal(false);
                        setEditingAppointment(null);
                        setClienteSelecionado('');
                        setAnimaisFiltrados(animais);
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

        {/* Modal de Erro */}
        {showErrorModal && (
          <div className="modal-overlay">
            <div className="modal-content">
              <div className="modal-header">
                <h3>Erro</h3>
              </div>
              <div className="modal-body">
                <p>{errorMessage}</p>
              </div>
              <div className="modal-footer">
                <button 
                  className="btn btn-primary"
                  onClick={() => setShowErrorModal(false)}
                >
                  OK
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Agendamentos;

















