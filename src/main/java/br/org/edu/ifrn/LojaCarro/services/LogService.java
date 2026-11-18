package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Log;
import br.org.edu.ifrn.LojaCarro.repository.LogRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    // O required = false evita que o Spring trave caso a biblioteca do RabbitMQ não esteja no pom.xml
    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    public void registrarEEnviarLog(Long idCarro, String acao) {
        Log log = new Log();
        log.setIdCarro(idCarro);
        log.setHorario(LocalDate.now());
        logRepository.save(log);

        if (rabbitTemplate != null) {
            rabbitTemplate.convertAndSend("logs.fila", acao + " - ID Carro: " + idCarro);
        }
    }
}