import React from 'react';
import { Link } from 'react-router-dom';
import { 
  Bone
} from 'lucide-react';
import './Home.css';

const Home = () => {



  return (
    <div className="home">
      {/* Hero Section */}
      <section className="hero">
        <div className="hero-content">
          <div className="hero-text">
            <h1 className="hero-title">
              Cuidando do seu <span className="highlight">melhor amigo</span>
            </h1>
            <p className="hero-description">
              Oferecemos serviços veterinários de alta qualidade com amor, 
              dedicação e tecnologia de ponta para garantir o bem-estar do seu pet.
            </p>
          </div>
          <div className="hero-image">
            <div className="hero-card">
              <Bone className="hero-icon" />
              <h3>VetClinic</h3>
              <p>Seu pet em boas mãos</p>
            </div>
          </div>
        </div>
      </section>




    </div>
  );
};

export default Home;

















