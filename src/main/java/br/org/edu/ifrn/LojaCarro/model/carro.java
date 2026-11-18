package br.org.edu.ifrn.LojaCarro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carros")
@Data
public class Carro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String marca;
    private String modelo;
    private Integer ano;
    private Double preco;
    
    private String caminhoImagem;
}