package buffet.app_web.service.autenticacao.dto;

import buffet.app_web.enums.UserRole;

public class UsuarioTokenDto {

    private long id;

    private String nome;

    private String email;

    private String telefone;

    private Integer qtdOrcamento;

    private UserRole role;

    private String token;

    private String foto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getQtdOrcamento() {
        return qtdOrcamento;
    }

    public void setQtdOrcamento(Integer qtdOrcamento) {
        this.qtdOrcamento = qtdOrcamento;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
