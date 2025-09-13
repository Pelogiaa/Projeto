import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { 
  Home, 
  Users, 
  Bone, 
  Stethoscope, 
  Calendar,
  Menu,
  X
} from 'lucide-react';
import './Layout.css';

const Layout = ({ children }) => {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const location = useLocation();

  const menuItems = [
    { path: '/', label: 'Home', icon: Home },
    { path: '/clientes', label: 'Clientes', icon: Users },
    { path: '/animais', label: 'Animais', icon: Bone },
    { path: '/servicos', label: 'Serviços', icon: Stethoscope },
    { path: '/agendamentos', label: 'Agendamentos', icon: Calendar },
  ];

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  return (
    <div className="layout">
      {/* Sidebar */}
      <aside className={`sidebar ${sidebarOpen ? 'sidebar-open' : ''}`}>
        <div className="sidebar-header">
          <div className="logo">
            <Bone className="logo-icon" />
            <span className="logo-text">VetClinic</span>
          </div>
          <button className="sidebar-toggle" onClick={toggleSidebar}>
            <X className="close-icon" />
          </button>
        </div>
        
        <nav className="sidebar-nav">
          <ul className="nav-list">
            {menuItems.map((item) => {
              const Icon = item.icon;
              const isActive = location.pathname === item.path;
              
              return (
                <li key={item.path} className="nav-item">
                  <Link
                    to={item.path}
                    className={`nav-link ${isActive ? 'active' : ''}`}
                    onClick={() => setSidebarOpen(false)}
                  >
                    <Icon className="nav-icon" />
                    <span className="nav-label">{item.label}</span>
                  </Link>
                </li>
              );
            })}
          </ul>
        </nav>
      </aside>

      {/* Main Content */}
      <main className="main-content">
        <header className="main-header">
          <button className="mobile-menu-toggle" onClick={toggleSidebar}>
            <Menu className="menu-icon" />
          </button>
          <h1 className="page-title">
            {menuItems.find(item => item.path === location.pathname)?.label || 'Home'}
          </h1>
        </header>
        
        <div className="content">
          {children}
        </div>
      </main>

      {/* Overlay for mobile */}
      {sidebarOpen && (
        <div 
          className="sidebar-overlay" 
          onClick={() => setSidebarOpen(false)}
        />
      )}
    </div>
  );
};

export default Layout;


