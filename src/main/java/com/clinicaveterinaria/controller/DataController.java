package com.clinicaveterinaria.controller;

import com.clinicaveterinaria.model.Servico;
import com.clinicaveterinaria.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller para operações de dados de exemplo
 */
@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "http://localhost:3000")
public class DataController {
    
    @Autowired
    private ServicoRepository servicoRepository;
    
    /**
     * Inserir dados de exemplo de serviços
     */
    @PostMapping("/servicos/exemplo")
    public ResponseEntity<?> inserirServicosExemplo() {
        try {
            // Verificar se já existem serviços
            if (servicoRepository.count() > 0) {
                return ResponseEntity.ok().body(Map.of("message", "Serviços já existem no banco de dados"));
            }
            
            // Criar serviços de exemplo
            Servico servico1 = new Servico();
            servico1.setNome("Consulta Veterinária");
            servico1.setCategoria("Consultas");
            servico1.setDescricao("Atendimento clínico completo para cães e gatos");
            servico1.setPreco(new BigDecimal("120.00"));
            servico1.setObservacoes("Inclui exame físico completo e orientações");
            servico1.setStatus(Servico.StatusServico.ATIVO);
            servicoRepository.save(servico1);
            
            Servico servico2 = new Servico();
            servico2.setNome("Vacinação");
            servico2.setCategoria("Prevenção");
            servico2.setDescricao("Aplicação de vacinas essenciais para pets");
            servico2.setPreco(new BigDecimal("80.00"));
            servico2.setObservacoes("Vacinas V8, V10, antirrábica e outras");
            servico2.setStatus(Servico.StatusServico.ATIVO);
            servicoRepository.save(servico2);
            
            Servico servico3 = new Servico();
            servico3.setNome("Cirurgia de Castração");
            servico3.setCategoria("Cirurgias");
            servico3.setDescricao("Procedimento cirúrgico para castração de cães e gatos");
            servico3.setPreco(new BigDecimal("350.00"));
            servico3.setObservacoes("Inclui anestesia e pós-operatório");
            servico3.setStatus(Servico.StatusServico.ATIVO);
            servicoRepository.save(servico3);
            
            Servico servico4 = new Servico();
            servico4.setNome("Banho e Tosa");
            servico4.setCategoria("Estética");
            servico4.setDescricao("Serviço completo de higiene e estética animal");
            servico4.setPreco(new BigDecimal("60.00"));
            servico4.setObservacoes("Banho, secagem, tosa e corte de unhas");
            servico4.setStatus(Servico.StatusServico.ATIVO);
            servicoRepository.save(servico4);
            
            Servico servico5 = new Servico();
            servico5.setNome("Exames Laboratoriais");
            servico5.setCategoria("Diagnóstico");
            servico5.setDescricao("Coleta e análise de exames de sangue e urina");
            servico5.setPreco(new BigDecimal("150.00"));
            servico5.setObservacoes("Hemograma completo e bioquímica");
            servico5.setStatus(Servico.StatusServico.ATIVO);
            servicoRepository.save(servico5);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Serviços de exemplo inseridos com sucesso");
            response.put("count", servicoRepository.count());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erro ao inserir serviços: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
