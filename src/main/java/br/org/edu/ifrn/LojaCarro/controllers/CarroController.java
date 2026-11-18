package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.services.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> listar() {
        return ResponseEntity.ok(carroService.listarTodos());
    }

    // O MediaType.MULTIPART_FORM_DATA_VALUE é essencial para aceitar a foto no Postman/Insomnia
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity salvar(@ModelAttribute Carro carro, @RequestParam(value = "imagem", required = false) MultipartFile imagem) {
        try {
            Carro carroSalvo = carroService.salvar(carro, imagem);
            return ResponseEntity.ok(carroSalvo);
        } catch (IllegalArgumentException e) {
            // Retorna erro 400 se a foto for maior que 5MB (nossa regra do Service)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}