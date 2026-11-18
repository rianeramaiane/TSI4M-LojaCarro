package br.org.edu.ifrn.LojaCarro.model;

public enum PerfilUsuario {
    ADMIN("admin"),
    CLIENTE("cliente");

    private String perfil;

    PerfilUsuario(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }
}