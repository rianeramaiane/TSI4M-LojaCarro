package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    private static final Logger log = LogManager.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listarTodos() {
        log.info("Listando todos os usuarios do sistema.");
        return repository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isEmpty()) {
            log.warn("Tentativa de buscar um usuario inexistente. ID: " + id);
        } else {
            log.info("Usuario ID: " + id + " encontrado com sucesso.");
        }
        return usuario;
    }

    public Usuario salvar(Usuario usuario) {
        try {
            Usuario salvo = repository.save(usuario);
            log.info("Usuario salvo com sucesso: " + salvo.getNome() + " - Cargo: " + salvo.getCargo());
            return salvo;
        } catch (Exception e) {
            log.error("Erro ao tentar salvar o usuario: " + usuario.getNome(), e);
            throw e;
        }
    }

    public void deletar(Long id) {
        try {
            repository.deleteById(id);
            log.info("Usuario deletado. ID: " + id);
        } catch (Exception e) {
            log.error("Erro ao tentar deletar o usuario ID: " + id, e);
            throw e;
        }
    }
}