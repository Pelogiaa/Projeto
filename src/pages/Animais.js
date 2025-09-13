import React, { useState, useEffect } from 'react';
import { 
  Plus, 
  Search, 
  Edit, 
  Trash2, 
  Bone,
  Calendar,
  User,
  Tag
} from 'lucide-react';
import './Animais.css';
import apiService from '../services/api';

const Animais = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [editingAnimal, setEditingAnimal] = useState(null);
  const [clientes, setClientes] = useState([]);
  const [animais, setAnimais] = useState([]);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    nomeAnimal: '',
    especie: '',
    raca: '',
    idade: '',
    peso: '',
    cor: '',
    sexo: '',
    idCliente: ''
  });

  // Carregar dados iniciais
  useEffect(() => {
    const carregarDados = async () => {
      try {
        setLoading(true);
        const [clientesData, animaisData] = await Promise.all([
          apiService.buscarClientesDropdown(),
          apiService.buscarAnimais()
        ]);
        setClientes(clientesData);
        setAnimais(animaisData);
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
      } finally {
        setLoading(false);
      }
    };

    carregarDados();
  }, []);

  const filteredAnimais = animais.filter(animal => {
    const cliente = clientes.find(c => c.id === animal.idCliente);
    const nomeCliente = cliente ? cliente.nome : '';
    
    return animal.nomeAnimal?.toLowerCase().includes(searchTerm.toLowerCase()) ||
           animal.tipoAnimal?.toLowerCase().includes(searchTerm.toLowerCase()) ||
           animal.raca?.toLowerCase().includes(searchTerm.toLowerCase()) ||
           nomeCliente?.toLowerCase().includes(searchTerm.toLowerCase());
  });

  // Função para resetar o formulário
  const resetForm = () => {
    setFormData({
      nomeAnimal: '',
      especie: '',
      raca: '',
      idade: '',
      peso: '',
      cor: '',
      sexo: '',
      idCliente: ''
    });
    setEditingAnimal(null);
  };

  // Função para abrir modal
  const abrirModal = (animal = null) => {
    if (animal) {
      setEditingAnimal(animal);
      setFormData({
        nomeAnimal: animal.nomeAnimal || '',
        especie: animal.tipoAnimal || '',
        raca: animal.raca || '',
        idade: animal.idade || '',
        peso: animal.peso || '',
        cor: animal.cor || '',
        sexo: animal.sexo || '',
        idCliente: animal.idCliente || ''
      });
    } else {
      resetForm();
    }
    setShowModal(true);
  };

  // Função para fechar modal
  const fecharModal = () => {
    setShowModal(false);
    resetForm();
  };

  // Função para lidar com mudanças no formulário
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  // Função para submeter o formulário
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      setLoading(true);
      
      const dadosAnimal = {
        nomeAnimal: formData.nomeAnimal,
        tipoAnimal: formData.especie,
        raca: formData.raca,
        idade: parseInt(formData.idade),
        peso: parseFloat(formData.peso),
        cor: formData.cor,
        sexo: formData.sexo,
        idCliente: parseInt(formData.idCliente)
      };

      if (editingAnimal) {
        // Atualizar animal existente
        await apiService.atualizarAnimal(editingAnimal.id, dadosAnimal);
        // Atualizar lista local
        setAnimais(prev => prev.map(animal => 
          animal.id === editingAnimal.id ? { ...animal, ...dadosAnimal } : animal
        ));
      } else {
        // Criar novo animal
        const novoAnimal = await apiService.criarAnimal(dadosAnimal);
        // Adicionar à lista local
        setAnimais(prev => [...prev, novoAnimal]);
      }

      fecharModal();
    } catch (error) {
      console.error('Erro ao salvar animal:', error);
      alert('Erro ao salvar animal. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (animal) => {
    abrirModal(animal);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este animal?')) {
      try {
        setLoading(true);
        await apiService.deletarAnimal(id);
        // Remover da lista local
        setAnimais(prev => prev.filter(animal => animal.id !== id));
      } catch (error) {
        console.error('Erro ao excluir animal:', error);
        alert('Erro ao excluir animal. Tente novamente.');
      } finally {
        setLoading(false);
      }
    }
  };


  const getEspecieIcon = (especie) => {
    return null;
  };

  const getSexoIcon = (sexo) => {
    return sexo === 'Macho' ? '♂' : '♀';
  };

  return (
    <div className="animais">
      <div className="page-header">
        <div className="header-content">
          <h1 className="page-title">Animais</h1>
          <p className="page-description">
            Gerencie o cadastro dos animais de estimação
          </p>
        </div>
        <button 
          className="btn btn-primary"
          onClick={() => abrirModal()}
        >
          <Plus className="btn-icon" />
          Novo Animal
        </button>
      </div>

      <div className="page-content">
        {/* Filtros e Busca */}
        <div className="filters-section">
          <div className="search-box">
            <Search className="search-icon" />
            <input
              type="text"
              placeholder="Buscar por nome, espécie, raça ou cliente..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>
        </div>


        {/* Tabela de Animais */}
        <div className="animals-table-container">
          <div className="table-header">
            <h3>Lista de Animais</h3>
            <span className="table-count">{filteredAnimais.length} animal(is) encontrado(s)</span>
          </div>
          
          <div className="table-wrapper">
            <table className="animals-table">
              <thead>
                <tr>
                  <th>Nome</th>
                  <th>Espécie</th>
                  <th>Raça</th>
                  <th>Idade</th>
                  <th>Peso</th>
                  <th>Cor</th>
                  <th>Sexo</th>
                  <th>Cliente</th>
                  <th>Data Cadastro</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {filteredAnimais.map((animal) => (
                  <tr key={animal.id}>
                    <td>
                      <span className="animal-name">{animal.nomeAnimal}</span>
                    </td>
                    <td>{animal.tipoAnimal}</td>
                    <td>{animal.raca}</td>
                    <td>{animal.idade} anos</td>
                    <td>{animal.peso} kg</td>
                    <td>{animal.cor}</td>
                    <td>
                      <span className="sexo-cell">
                        <span className="sexo-icon">{getSexoIcon(animal.sexo)}</span>
                        {animal.sexo}
                      </span>
                    </td>
                    <td>
                      {(() => {
                        const cliente = clientes.find(c => c.id === animal.idCliente);
                        return cliente ? cliente.nome : 'Cliente não encontrado';
                      })()}
                    </td>
                    <td>{new Date(animal.dataCadastro).toLocaleDateString('pt-BR')}</td>
                    <td>
                      <div className="action-buttons">
                        <button 
                          className="action-btn edit"
                          onClick={() => handleEdit(animal)}
                          title="Editar"
                        >
                          <Edit className="action-icon" />
                        </button>
                        <button 
                          className="action-btn delete"
                          onClick={() => handleDelete(animal.id)}
                          title="Excluir"
                        >
                          <Trash2 className="action-icon" />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        {/* Modal de Animal */}
        {showModal && (
          <div className="modal-overlay">
            <div className="modal">
              <div className="modal-header">
                <h3>{editingAnimal ? 'Editar Animal' : 'Novo Animal'}</h3>
                <button 
                  className="modal-close"
                  onClick={fecharModal}
                >
                  ×
                </button>
              </div>
              <div className="modal-content">
                <form className="animal-form" onSubmit={handleSubmit}>
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="nomeAnimal">Nome do Animal</label>
                      <input
                        type="text"
                        id="nomeAnimal"
                        name="nomeAnimal"
                        value={formData.nomeAnimal}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="especie">Espécie</label>
                      <select
                        id="especie"
                        name="especie"
                        value={formData.especie}
                        onChange={handleInputChange}
                        required
                      >
                        <option value="">Selecione...</option>
                        <option value="Cão">Cão</option>
                        <option value="Gato">Gato</option>
                        <option value="Ave">Ave</option>
                      </select>
                    </div>
                  </div>
                  
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="raca">Raça</label>
                      <input
                        type="text"
                        id="raca"
                        name="raca"
                        value={formData.raca}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="idade">Idade (anos)</label>
                      <input
                        type="number"
                        id="idade"
                        name="idade"
                        min="0"
                        max="30"
                        value={formData.idade}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                  </div>
                  
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="peso">Peso (kg)</label>
                      <input
                        type="number"
                        id="peso"
                        name="peso"
                        min="0"
                        step="0.1"
                        value={formData.peso}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="sexo">Sexo</label>
                      <select
                        id="sexo"
                        name="sexo"
                        value={formData.sexo}
                        onChange={handleInputChange}
                        required
                      >
                        <option value="">Selecione...</option>
                        <option value="Macho">Macho</option>
                        <option value="Fêmea">Fêmea</option>
                      </select>
                    </div>
                  </div>
                  
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="cor">Cor</label>
                      <input
                        type="text"
                        id="cor"
                        name="cor"
                        value={formData.cor}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="idCliente">Cliente</label>
                      <select
                        id="idCliente"
                        name="idCliente"
                        value={formData.idCliente}
                        onChange={handleInputChange}
                        required
                        disabled={loading}
                      >
                        <option value="">{loading ? 'Carregando...' : 'Selecione...'}</option>
                        {clientes.map((cliente) => (
                          <option key={cliente.id} value={cliente.id}>
                            {cliente.nome} - {cliente.email}
                          </option>
                        ))}
                      </select>
                    </div>
                  </div>
                  
                  
                  <div className="form-actions">
                    <button 
                      type="button" 
                      className="btn btn-outline"
                      onClick={fecharModal}
                      disabled={loading}
                    >
                      Cancelar
                    </button>
                    <button 
                      type="submit" 
                      className="btn btn-primary"
                      disabled={loading}
                    >
                      {loading ? 'Salvando...' : (editingAnimal ? 'Atualizar' : 'Cadastrar')}
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

export default Animais;
















