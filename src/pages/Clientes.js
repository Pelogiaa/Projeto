import React, { useState, useEffect } from 'react';
import { 
  Plus, 
  Search, 
  Edit, 
  Trash2, 
  Mail,
  Phone,
  MapPin
} from 'lucide-react';
import apiService from '../services/api';
import './Clientes.css';

const Clientes = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [editingClient, setEditingClient] = useState(null);
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cpfError, setCpfError] = useState('');
  const [cpfValue, setCpfValue] = useState('');
  const [telefoneError, setTelefoneError] = useState('');
  const [telefoneValue, setTelefoneValue] = useState('');
  const [emailError, setEmailError] = useState('');
  const [emailValue, setEmailValue] = useState('');
  const [cepError, setCepError] = useState('');
  const [cepValue, setCepValue] = useState('');

  // Função para formatar CPF
  const formatarCPF = (cpf) => {
    // Remove caracteres não numéricos
    const cpfLimpo = cpf.replace(/\D/g, '');
    
    // Limita a 11 dígitos
    const cpfLimitado = cpfLimpo.substring(0, 11);
    
    // Aplica a máscara 000.000.000-00
    return cpfLimitado.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  };

  // Função para formatar telefone
  const formatarTelefone = (telefone) => {
    // Remove caracteres não numéricos
    const telefoneLimpo = telefone.replace(/\D/g, '');
    
    // Limita a 12 dígitos
    const telefoneLimitado = telefoneLimpo.substring(0, 12);
    
    // Aplica a máscara (00) 0 0000-0000
    if (telefoneLimitado.length <= 2) {
      return telefoneLimitado;
    } else if (telefoneLimitado.length <= 3) {
      return `(${telefoneLimitado.substring(0, 2)}) ${telefoneLimitado.substring(2)}`;
    } else if (telefoneLimitado.length <= 7) {
      return `(${telefoneLimitado.substring(0, 2)}) ${telefoneLimitado.substring(2, 3)} ${telefoneLimitado.substring(3)}`;
    } else if (telefoneLimitado.length <= 10) {
      return `(${telefoneLimitado.substring(0, 2)}) ${telefoneLimitado.substring(2, 3)} ${telefoneLimitado.substring(3, 7)}-${telefoneLimitado.substring(7)}`;
    } else if (telefoneLimitado.length === 11) {
      return `(${telefoneLimitado.substring(0, 2)}) ${telefoneLimitado.substring(2, 3)} ${telefoneLimitado.substring(3, 7)}-${telefoneLimitado.substring(7, 11)}`;
    } else if (telefoneLimitado.length === 12) {
      return `(${telefoneLimitado.substring(0, 2)}) ${telefoneLimitado.substring(2, 3)} ${telefoneLimitado.substring(3, 7)}-${telefoneLimitado.substring(7, 11)}`;
    }
    
    return telefoneLimitado;
  };

  // Função para validar CPF
  const validarCPF = (cpf) => {
    // Remove caracteres não numéricos
    const cpfLimpo = cpf.replace(/\D/g, '');
    
    // Verifica se tem exatamente 11 dígitos
    if (cpfLimpo.length !== 11) {
      return false;
    }
    
    // Verifica se todos os dígitos são iguais (CPF inválido)
    if (/^(\d)\1{10}$/.test(cpfLimpo)) {
      return false;
    }
    
    return true;
  };

  // Função para validar telefone
  const validarTelefone = (telefone) => {
    // Remove caracteres não numéricos
    const telefoneLimpo = telefone.replace(/\D/g, '');
    
    // Verifica se tem pelo menos 11 dígitos
    if (telefoneLimpo.length < 11) {
      return false;
    }
    
    return true;
  };

  // Função para validar email
  const validarEmail = (email) => {
    // Verifica se termina com @gmail.com
    return email.toLowerCase().endsWith('@gmail.com');
  };

  // Função para formatar CEP
  const formatarCEP = (cep) => {
    // Remove caracteres não numéricos
    const cepLimpo = cep.replace(/\D/g, '');
    
    // Limita a 8 dígitos
    const cepLimitado = cepLimpo.substring(0, 8);
    
    // Aplica a máscara 00000-000
    if (cepLimitado.length <= 5) {
      return cepLimitado;
    } else {
      return cepLimitado.replace(/(\d{5})(\d{3})/, '$1-$2');
    }
  };

  // Função para validar CEP
  const validarCEP = (cep) => {
    // Remove caracteres não numéricos
    const cepLimpo = cep.replace(/\D/g, '');
    
    // Verifica se tem exatamente 8 dígitos
    if (cepLimpo.length !== 8) {
      return false;
    }
    
    return true;
  };

  // Carregar clientes ao montar o componente
  useEffect(() => {
    carregarClientes();
  }, []);

  // Carregar clientes da API
  const carregarClientes = async () => {
    try {
      setLoading(true);
      setError(null);
      const dados = await apiService.buscarClientes(searchTerm);
      setClientes(dados);
    } catch (err) {
      setError('Erro ao carregar clientes: ' + err.message);
      console.error('Erro ao carregar clientes:', err);
    } finally {
      setLoading(false);
    }
  };

  // Buscar clientes quando o termo de busca mudar
  useEffect(() => {
    const timeoutId = setTimeout(() => {
      carregarClientes();
    }, 500); // Debounce de 500ms

    return () => clearTimeout(timeoutId);
  }, [searchTerm]);

  // Função para lidar com mudança no campo CPF
  const handleCpfChange = (e) => {
    const cpf = e.target.value;
    setCpfError('');
    
    // Aplica formatação automática
    const cpfFormatado = formatarCPF(cpf);
    setCpfValue(cpfFormatado);
    
    // Valida apenas se tiver 11 dígitos
    const cpfLimpo = cpf.replace(/\D/g, '');
    if (cpfLimpo.length > 0 && cpfLimpo.length < 11) {
      setCpfError('CPF Inválido');
    } else if (cpfLimpo.length === 11 && !validarCPF(cpf)) {
      setCpfError('CPF Inválido');
    }
  };

  // Função para lidar com mudança no campo telefone
  const handleTelefoneChange = (e) => {
    const telefone = e.target.value;
    setTelefoneError('');
    
    // Aplica formatação automática
    const telefoneFormatado = formatarTelefone(telefone);
    setTelefoneValue(telefoneFormatado);
    
    // Valida apenas se tiver dígitos
    const telefoneLimpo = telefone.replace(/\D/g, '');
    if (telefoneLimpo.length > 0 && telefoneLimpo.length < 11) {
      setTelefoneError('Telefone Inválido');
    } else {
      setTelefoneError(''); // Limpa erro se tiver 11 ou mais dígitos
    }
  };

  // Função para lidar com mudança no campo email
  const handleEmailChange = (e) => {
    const email = e.target.value;
    setEmailError('');
    setEmailValue(email);
    
    // Valida apenas se tiver conteúdo
    if (email.length > 0 && !validarEmail(email)) {
      setEmailError('Email Inválido');
    } else {
      setEmailError(''); // Limpa erro se for válido
    }
  };

  // Função para lidar com mudança no campo CEP
  const handleCepChange = (e) => {
    const cep = e.target.value;
    setCepError('');
    const cepFormatado = formatarCEP(cep);
    setCepValue(cepFormatado);
    
    // Valida apenas se tiver conteúdo
    const cepLimpo = cep.replace(/\D/g, '');
    if (cepLimpo.length > 0 && cepLimpo.length < 8) {
      setCepError('CEP Inválido');
    } else if (cepLimpo.length === 8 && !validarCEP(cep)) {
      setCepError('CEP Inválido');
    } else {
      setCepError(''); // Limpa erro se for válido
    }
  };

  const filteredClientes = clientes;

  const handleEdit = (cliente) => {
    setEditingClient(cliente);
    setCpfError(''); // Limpar erro ao abrir modal
    setCpfValue(cliente?.cpf ? formatarCPF(cliente.cpf) : ''); // Inicializar CPF formatado
    setTelefoneError(''); // Limpar erro ao abrir modal
    setTelefoneValue(cliente?.telefone ? formatarTelefone(cliente.telefone) : ''); // Inicializar telefone formatado
    setEmailError(''); // Limpar erro ao abrir modal
    setEmailValue(cliente?.email || ''); // Inicializar email
    setCepError(''); // Limpar erro ao abrir modal
    setCepValue(cliente?.cep ? formatarCEP(cliente.cep) : ''); // Inicializar CEP formatado
    setShowModal(true);
  };

  const handleNewClient = () => {
    setEditingClient(null);
    setCpfError(''); // Limpar erro ao abrir modal
    setCpfValue(''); // Limpar CPF
    setTelefoneError(''); // Limpar erro ao abrir modal
    setTelefoneValue(''); // Limpar telefone
    setEmailError(''); // Limpar erro ao abrir modal
    setEmailValue(''); // Limpar email
    setCepError(''); // Limpar erro ao abrir modal
    setCepValue(''); // Limpar CEP
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este cliente?')) {
      try {
        await apiService.deletarCliente(id);
        await carregarClientes(); // Recarregar lista
        alert('Cliente excluído com sucesso!');
      } catch (err) {
        alert('Erro ao excluir cliente: ' + err.message);
        console.error('Erro ao excluir cliente:', err);
      }
    }
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    // Validar CPF antes de enviar
    if (!validarCPF(cpfValue)) {
      setCpfError('CPF Inválido');
      return;
    }
    
    // Validar telefone antes de enviar
    if (!validarTelefone(telefoneValue)) {
      setTelefoneError('Telefone Inválido');
      return;
    }
    
    // Validar email antes de enviar
    if (!validarEmail(emailValue)) {
      setEmailError('Email Inválido');
      return;
    }
    
    // Validar CEP antes de enviar
    if (!validarCEP(cepValue)) {
      setCepError('CEP Inválido');
      return;
    }
    
    const dadosCliente = {
      nome: formData.get('nome'),
      email: emailValue,
      telefone: telefoneValue.replace(/\D/g, ''), // Enviar apenas números para o backend
      cep: cepValue.replace(/\D/g, ''), // Enviar apenas números para o backend
      cpf: cpfValue.replace(/\D/g, '') // Enviar apenas números para o backend
    };

    try {
      if (editingClient) {
        await apiService.atualizarCliente(editingClient.id, dadosCliente);
        alert('Cliente atualizado com sucesso!');
      } else {
        await apiService.criarCliente(dadosCliente);
        alert('Cliente criado com sucesso!');
      }
      
      setShowModal(false);
      setEditingClient(null);
      setCpfError(''); // Limpar erro ao fechar modal
      setCpfValue(''); // Limpar CPF
      setTelefoneError(''); // Limpar erro ao fechar modal
      setTelefoneValue(''); // Limpar telefone
      setEmailError(''); // Limpar erro ao fechar modal
      setEmailValue(''); // Limpar email
      setCepError(''); // Limpar erro ao fechar modal
      setCepValue(''); // Limpar CEP
      await carregarClientes(); // Recarregar lista
    } catch (err) {
      alert('Erro ao salvar cliente: ' + err.message);
      console.error('Erro ao salvar cliente:', err);
    }
  };

  return (
    <div className="clientes">
      <div className="page-header">
        <div className="header-content">
          <h1 className="page-title">Clientes</h1>
          <p className="page-description">
            Gerencie os dados dos seus clientes e seus pets
          </p>
        </div>
        <button 
          className="btn btn-primary"
          onClick={handleNewClient}
        >
          <Plus className="btn-icon" />
          Novo Cliente
        </button>
      </div>

      <div className="page-content">
        {/* Filtros e Busca */}
        <div className="filters-section">
          <div className="search-box">
            <Search className="search-icon" />
            <input
              type="text"
              placeholder="Buscar por nome, email ou telefone..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>
        </div>


        {/* Tabela de Clientes */}
        <div className="table-container">
          <div className="table-header">
            <h3>Lista de Clientes</h3>
            <span className="table-count">
              {loading ? 'Carregando...' : `${filteredClientes.length} cliente(s) encontrado(s)`}
            </span>
          </div>
          
          {error && (
            <div className="error-message">
              <p>{error}</p>
              <button onClick={carregarClientes} className="btn btn-outline">
                Tentar Novamente
              </button>
            </div>
          )}
          
          <div className="table-wrapper">
            {loading ? (
              <div className="loading-message">
                <p>Carregando clientes...</p>
              </div>
            ) : (
              <table className="clients-table">
                <thead>
                  <tr>
                    <th>Nome</th>
                    <th>Contato</th>
                    <th>CEP</th>
                    <th>Data Cadastro</th>
                    <th>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredClientes.length === 0 ? (
                    <tr>
                      <td colSpan="5" className="no-data">
                        Nenhum cliente encontrado
                      </td>
                    </tr>
                  ) : (
                    filteredClientes.map((cliente) => (
                      <tr key={cliente.id}>
                        <td>
                          <div className="client-info">
                            <div className="client-name">{cliente.nome}</div>
                            <div className="client-cpf">CPF: {cliente.cpf}</div>
                          </div>
                        </td>
                        <td>
                          <div className="contact-info">
                            <div className="contact-item">
                              <Mail className="contact-icon" />
                              <span>{cliente.email}</span>
                            </div>
                            <div className="contact-item">
                              <Phone className="contact-icon" />
                              <span>{cliente.telefone}</span>
                            </div>
                          </div>
                        </td>
                        <td>
                          <div className="address-info">
                            <MapPin className="address-icon" />
                            <span>{cliente.cep ? formatarCEP(cliente.cep) : 'N/A'}</span>
                          </div>
                        </td>
                        <td>
                          <div className="date-info">
                            {cliente.dataCadastro ? 
                              new Date(cliente.dataCadastro).toLocaleDateString('pt-BR') : 
                              'N/A'
                            }
                          </div>
                        </td>
                        <td>
                          <div className="action-buttons">
                            <button 
                              className="action-btn edit"
                              onClick={() => handleEdit(cliente)}
                              title="Editar"
                            >
                              <Edit className="action-icon" />
                            </button>
                            <button 
                              className="action-btn delete"
                              onClick={() => handleDelete(cliente.id)}
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
            )}
          </div>
        </div>

        {/* Modal de Cliente */}
        {showModal && (
          <div className="modal-overlay">
            <div className="modal">
              <div className="modal-header">
                <h3>{editingClient ? 'Editar Cliente' : 'Novo Cliente'}</h3>
                <button 
                  className="modal-close"
                  onClick={() => {
                    setShowModal(false);
                    setEditingClient(null);
                    setCpfError(''); // Limpar erro ao fechar modal
                    setCpfValue(''); // Limpar CPF
                    setTelefoneError(''); // Limpar erro ao fechar modal
                    setTelefoneValue(''); // Limpar telefone
                    setEmailError(''); // Limpar erro ao fechar modal
                    setEmailValue(''); // Limpar email
                    setCepError(''); // Limpar erro ao fechar modal
                    setCepValue(''); // Limpar CEP
                  }}
                >
                  ×
                </button>
              </div>
              <div className="modal-content">
                <form className="client-form" onSubmit={handleSubmit}>
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="nome">Nome Completo</label>
                      <input
                        type="text"
                        id="nome"
                        name="nome"
                        defaultValue={editingClient?.nome || ''}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="cpf">CPF</label>
                      <input
                        type="text"
                        id="cpf"
                        name="cpf"
                        placeholder="000.000.000-00"
                        value={cpfValue}
                        onChange={handleCpfChange}
                        required
                        className={cpfError ? 'error' : ''}
                        maxLength={14}
                      />
                      {cpfError && (
                        <span className="error-message">{cpfError}</span>
                      )}
                    </div>
                  </div>
                  
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="email">Email</label>
                      <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="exemplo@gmail.com"
                        value={emailValue}
                        onChange={handleEmailChange}
                        required
                        className={emailError ? 'error' : ''}
                      />
                      {emailError && (
                        <span className="error-message">{emailError}</span>
                      )}
                    </div>
                    <div className="form-group">
                      <label htmlFor="telefone">Telefone</label>
                      <input
                        type="tel"
                        id="telefone"
                        name="telefone"
                        placeholder="(00) 0 0000-0000"
                        value={telefoneValue}
                        onChange={handleTelefoneChange}
                        required
                        className={telefoneError ? 'error' : ''}
                        maxLength={16}
                      />
                      {telefoneError && (
                        <span className="error-message">{telefoneError}</span>
                      )}
                    </div>
                  </div>
                  
                  <div className="form-group">
                    <label htmlFor="cep">CEP</label>
                    <input
                      type="text"
                      id="cep"
                      name="cep"
                      value={cepValue}
                      onChange={handleCepChange}
                      className={cepError ? 'error' : ''}
                      maxLength={9}
                      placeholder="00000-000"
                      required
                    />
                    {cepError && (
                      <span className="error-message">{cepError}</span>
                    )}
                  </div>
                  
                  <div className="form-actions">
                    <button 
                      type="button" 
                      className="btn btn-outline"
                      onClick={() => {
                        setShowModal(false);
                        setEditingClient(null);
                        setCpfError(''); // Limpar erro ao cancelar
                        setCpfValue(''); // Limpar CPF
                        setTelefoneError(''); // Limpar erro ao cancelar
                        setTelefoneValue(''); // Limpar telefone
                        setEmailError(''); // Limpar erro ao cancelar
                        setEmailValue(''); // Limpar email
                        setCepError(''); // Limpar erro ao cancelar
                        setCepValue(''); // Limpar CEP
                      }}
                    >
                      Cancelar
                    </button>
                    <button type="submit" className="btn btn-primary">
                      {editingClient ? 'Atualizar' : 'Cadastrar'}
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

export default Clientes;
