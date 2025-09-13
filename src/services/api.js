// =====================================================
// SERVIÇO DE API - CLINICA VETERINARIA
// =====================================================

const API_BASE_URL = 'http://localhost:3001/api';

class ApiService {
  // Método genérico para fazer requisições
  async request(endpoint, options = {}) {
    const url = `${API_BASE_URL}${endpoint}`;
    const config = {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    };

    try {
      const response = await fetch(url, config);
      
      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.message || `Erro HTTP: ${response.status}`);
      }

      // Se a resposta for vazia (204 No Content), retorna null
      if (response.status === 204) {
        return null;
      }

      return await response.json();
    } catch (error) {
      console.error('Erro na requisição:', error);
      throw error;
    }
  }

  // =====================================================
  // CLIENTES
  // =====================================================

  // Buscar todos os clientes
  async buscarClientes(termo = '') {
    const endpoint = termo ? `/clientes?busca=${encodeURIComponent(termo)}` : '/clientes';
    return this.request(endpoint);
  }

  // Buscar cliente por ID
  async buscarClientePorId(id) {
    return this.request(`/clientes/${id}`);
  }

  // Criar novo cliente
  async criarCliente(dadosCliente) {
    return this.request('/clientes', {
      method: 'POST',
      body: JSON.stringify(dadosCliente),
    });
  }

  // Atualizar cliente
  async atualizarCliente(id, dadosCliente) {
    return this.request(`/clientes/${id}`, {
      method: 'PUT',
      body: JSON.stringify(dadosCliente),
    });
  }

  // Deletar cliente
  async deletarCliente(id) {
    return this.request(`/clientes/${id}`, {
      method: 'DELETE',
    });
  }

  // Contar total de clientes
  async contarClientes() {
    return this.request('/clientes/contar');
  }

  // Verificar se cliente existe
  async clienteExiste(id) {
    return this.request(`/clientes/${id}/existe`);
  }

  // Buscar clientes para dropdown (apenas id, nome e email)
  async buscarClientesDropdown() {
    return this.request('/clientes/dropdown');
  }

  // =====================================================
  // ANIMAIS
  // =====================================================

  // Buscar todos os animais
  async buscarAnimais() {
    return this.request('/animais');
  }

  // Buscar animal por ID
  async buscarAnimalPorId(id) {
    return this.request(`/animais/${id}`);
  }

  // Criar novo animal
  async criarAnimal(dadosAnimal) {
    return this.request('/animais', {
      method: 'POST',
      body: JSON.stringify(dadosAnimal),
    });
  }

  // Atualizar animal
  async atualizarAnimal(id, dadosAnimal) {
    return this.request(`/animais/${id}`, {
      method: 'PUT',
      body: JSON.stringify(dadosAnimal),
    });
  }

  // Deletar animal
  async deletarAnimal(id) {
    return this.request(`/animais/${id}`, {
      method: 'DELETE',
    });
  }

  // Buscar animais por cliente
  async buscarAnimaisPorCliente(idCliente) {
    return this.request(`/animais/cliente/${idCliente}`);
  }

  // =====================================================
  // SERVIÇOS
  // =====================================================

  // Buscar todos os serviços
  async buscarServicos(termo = '') {
    const endpoint = termo ? `/servicos?termo=${encodeURIComponent(termo)}` : '/servicos';
    return this.request(endpoint);
  }

  // Buscar serviços ativos
  async buscarServicosAtivos(termo = '') {
    const endpoint = termo ? `/servicos/ativos?termo=${encodeURIComponent(termo)}` : '/servicos/ativos';
    return this.request(endpoint);
  }

  // Buscar serviço por ID
  async buscarServicoPorId(id) {
    return this.request(`/servicos/${id}`);
  }

  // Criar novo serviço
  async criarServico(dadosServico) {
    return this.request('/servicos', {
      method: 'POST',
      body: JSON.stringify(dadosServico),
    });
  }

  // Atualizar serviço
  async atualizarServico(id, dadosServico) {
    return this.request(`/servicos/${id}`, {
      method: 'PUT',
      body: JSON.stringify(dadosServico),
    });
  }

  // Deletar serviço
  async deletarServico(id) {
    return this.request(`/servicos/${id}`, {
      method: 'DELETE',
    });
  }

  // Buscar serviços para dropdown (com preço)
  async buscarServicosDropdown() {
    return this.request('/servicos/dropdown');
  }

  // Buscar serviços por categoria
  async buscarServicosPorCategoria(categoria) {
    return this.request(`/servicos/categoria/${encodeURIComponent(categoria)}`);
  }

  // Alterar status do serviço
  async alterarStatusServico(id) {
    return this.request(`/servicos/${id}/status`, {
      method: 'PATCH',
    });
  }

  // Contar total de serviços
  async contarServicos() {
    return this.request('/servicos/contar');
  }

  // Contar serviços ativos
  async contarServicosAtivos() {
    return this.request('/servicos/contar/ativos');
  }
}

// Instância única do serviço
const apiService = new ApiService();

export default apiService;
















