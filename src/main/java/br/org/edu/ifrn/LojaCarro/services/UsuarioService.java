package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Instanciando direto
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        // Criptografando
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Usuario novo, Long id) {
        Usuario antigo = usuarioRepository.findById(id).get(); 
        
        antigo.setNome(novo.getNome());
        antigo.setEmail(novo.getEmail());
        antigo.setPerfil(novo.getPerfil());
        
        if (novo.getSenha() != null && !novo.getSenha().isEmpty()) {
            antigo.setSenha(passwordEncoder.encode(novo.getSenha()));
        }

        return usuarioRepository.save(antigo);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).get();
    }
}