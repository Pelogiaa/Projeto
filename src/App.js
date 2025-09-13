import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Home from './pages/Home';
import Clientes from './pages/Clientes';
import Animais from './pages/Animais';
import Servicos from './pages/Servicos';
import Agendamentos from './pages/Agendamentos';

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/clientes" element={<Clientes />} />
          <Route path="/animais" element={<Animais />} />
          <Route path="/servicos" element={<Servicos />} />
          <Route path="/agendamentos" element={<Agendamentos />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;


