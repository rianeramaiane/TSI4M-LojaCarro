package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;
    @Autowired
    private LogService logService;

    private final String PASTA_IMAGENS = "IMG/";

    public List<Carro> listarTodos() {
        return carroRepository.findAll();
    }

    public Carro salvar(Carro carro, MultipartFile imagem) throws IOException {
        if (imagem != null && !imagem.isEmpty()) {
            if (imagem.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("A imagem excede o limite de 5MB");
            }
            
            Path diretorio = Paths.get(PASTA_IMAGENS);
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }
            
            String nomeArquivo = UUID.randomUUID().toString() + "_" + imagem.getOriginalFilename();
            Path caminhoCompleto = diretorio.resolve(nomeArquivo);
            
            Files.copy(imagem.getInputStream(), caminhoCompleto);
            carro.setCaminhoImagem(nomeArquivo);
        }
        
        Carro salvo = carroRepository.save(carro);
    logService.registrarEEnviarLog(salvo.getId(), "Novo veículo cadastrado na loja");
    return salvo;
    }
}