package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Agendamento;
import br.org.edu.ifrn.LojaCarro.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private LogService logService;

    public Agendamento agendar(Agendamento agendamento) {
        LocalDateTime horarioDesejado = agendamento.getHorario();
        
        // Define a margem de segurança de 30 minutos para antes e para depois
        LocalDateTime limiteInferior = horarioDesejado.minusMinutes(30);
        LocalDateTime limiteSuperior = horarioDesejado.plusMinutes(30);

        boolean temConflito = agendamentoRepository.existeConflitoDeHorario(
                agendamento.getCarro().getId(),
                limiteInferior,
                limiteSuperior
        );

        if (temConflito) {
            throw new IllegalArgumentException("O veículo já possui um test drive agendado neste intervalo de 30 minutos.");
        }

        Agendamento salvo = agendamentoRepository.save(agendamento);
    logService.registrarEEnviarLog(salvo.getCarro().getId(), "Novo test drive agendado");
    return salvo;
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }
}