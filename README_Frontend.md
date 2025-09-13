# 🐾 Frontend - Clínica Veterinária

Frontend React para o sistema de gerenciamento da clínica veterinária.

## 🚀 Funcionalidades

- ✅ **Landing Page** - Página inicial com informações da clínica
- ✅ **Menu Lateral** - Navegação intuitiva entre as seções
- ✅ **Gestão de Clientes** - CRUD completo de clientes
- ✅ **Gestão de Animais** - Cadastro e gerenciamento de pets
- ✅ **Serviços** - Catálogo de serviços oferecidos
- ✅ **Agendamentos** - Sistema de agendamento de consultas
- ✅ **Design Responsivo** - Interface adaptável para mobile e desktop
- ✅ **UI Moderna** - Interface limpa e profissional

## 📋 Páginas Disponíveis

### 🏠 Home

- Apresentação da clínica
- Estatísticas em tempo real
- Serviços oferecidos
- Ações rápidas
- Depoimentos e avaliações

### 👥 Clientes

- Listagem de clientes com busca
- Cadastro e edição de clientes
- Visualização de detalhes
- Filtros e exportação

### 🐕 Animais

- Cadastro de pets por cliente
- Informações detalhadas (espécie, raça, idade, etc.)
- Status e observações
- Interface em cards

### 🏥 Serviços

- Catálogo de serviços
- Preços e durações
- Categorização
- Gestão completa

### 📅 Agendamentos

- Calendário de consultas
- Status dos agendamentos
- Filtros por data
- Informações completas

## 🛠️ Tecnologias Utilizadas

- **React 18** - Biblioteca principal
- **React Router DOM** - Roteamento
- **Lucide React** - Ícones
- **CSS3** - Estilização moderna
- **HTML5** - Estrutura semântica

## 📦 Instalação

### Pré-requisitos

- Node.js 16 ou superior
- npm ou yarn

### 1. Instalar dependências

```bash
npm install
```

### 2. Executar o projeto

```bash
npm start
```

O projeto estará disponível em `http://localhost:3000`

### 3. Build para produção

```bash
npm run build
```

## 🎨 Características do Design

### Paleta de Cores

- **Primária**: Gradiente azul/roxo (#667eea → #764ba2)
- **Secundária**: Dourado (#fbbf24)
- **Neutras**: Tons de cinza para textos e fundos
- **Status**: Verde (ativo), Vermelho (inativo), Azul (pendente)

### Componentes

- **Layout Responsivo** - Menu lateral colapsável
- **Cards Modernos** - Design limpo com sombras sutis
- **Botões Interativos** - Efeitos hover e transições
- **Formulários** - Validação visual e UX otimizada
- **Modais** - Overlay para edição e criação

### Responsividade

- **Desktop** - Layout completo com menu lateral fixo
- **Tablet** - Menu lateral colapsável
- **Mobile** - Menu lateral em overlay

## 📱 Funcionalidades por Dispositivo

### Desktop (1024px+)

- Menu lateral fixo
- Grid de 3-4 colunas
- Hover effects completos
- Todas as funcionalidades visíveis

### Tablet (768px - 1023px)

- Menu lateral colapsável
- Grid de 2 colunas
- Botões de ação em linha
- Layout adaptado

### Mobile (até 767px)

- Menu lateral em overlay
- Grid de 1 coluna
- Botões de ação empilhados
- Interface otimizada para toque

## 🔧 Estrutura do Projeto

```text
src/
├── components/
│   ├── Layout.js          # Layout principal com menu
│   └── Layout.css         # Estilos do layout
├── pages/
│   ├── Home.js            # Página inicial
│   ├── Home.css           # Estilos da home
│   ├── Clientes.js        # Gestão de clientes
│   ├── Clientes.css       # Estilos dos clientes
│   ├── Animais.js         # Gestão de animais
│   ├── Animais.css        # Estilos dos animais
│   ├── Servicos.js        # Gestão de serviços
│   ├── Servicos.css       # Estilos dos serviços
│   ├── Agendamentos.js    # Gestão de agendamentos
│   └── Agendamentos.css   # Estilos dos agendamentos
├── App.js                 # Componente principal
├── index.js               # Ponto de entrada
└── index.css              # Estilos globais
```

## 🎯 Próximas Funcionalidades

- [ ] Integração com API backend
- [ ] Autenticação de usuários
- [ ] Dashboard com gráficos
- [ ] Notificações em tempo real
- [ ] Upload de fotos dos animais
- [ ] Relatórios em PDF
- [ ] Sistema de backup
- [ ] PWA (Progressive Web App)

## 📞 Suporte

Para dúvidas ou problemas:

1. Verifique os logs do console
2. Confirme se todas as dependências estão instaladas
3. Teste em diferentes navegadores
4. Verifique a responsividade em diferentes dispositivos

---

**Desenvolvido para o projeto Clínica Veterinária** 🐕🐱
