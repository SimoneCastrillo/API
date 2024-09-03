package buffet.app_web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dataEvento;
    private Integer qtdConvidados;
    private String status;
    private LocalTime inicio;
    private LocalTime fim;
    private String sugestao;
    private String fkUsuario;
    private String fkTipoEvento;
    private String fkDecoracao;
    private String fkCardapio;

    public Orcamento(Integer id, LocalDate dataEvento, Integer qtdConvidados, String status, LocalTime inicio, LocalTime fim, String sugestao, String fkUsuario, String fkTipoEvento, String fkDecoracao, String fkCardapio) {
        this.id = id;
        this.dataEvento = dataEvento;
        this.qtdConvidados = qtdConvidados;
        this.status = status;
        this.inicio = inicio;
        this.fim = fim;
        this.sugestao = sugestao;
        this.fkUsuario = fkUsuario;
        this.fkTipoEvento = fkTipoEvento;
        this.fkDecoracao = fkDecoracao;
        this.fkCardapio = fkCardapio;
    }

    public Orcamento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Integer getQtdConvidados() {
        return qtdConvidados;
    }

    public void setQtdConvidados(Integer qtdConvidados) {
        this.qtdConvidados = qtdConvidados;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFim() {
        return fim;
    }

    public void setFim(LocalTime fim) {
        this.fim = fim;
    }

    public String getSugestao() {
        return sugestao;
    }

    public void setSugestao(String sugestao) {
        this.sugestao = sugestao;
    }

    public String getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(String fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public String getFkTipoEvento() {
        return fkTipoEvento;
    }

    public void setFkTipoEvento(String fkTipoEvento) {
        this.fkTipoEvento = fkTipoEvento;
    }

    public String getFkDecoracao() {
        return fkDecoracao;
    }

    public void setFkDecoracao(String fkDecoracao) {
        this.fkDecoracao = fkDecoracao;
    }

    public String getFkCardapio() {
        return fkCardapio;
    }

    public void setFkCardapio(String fkCardapio) {
        this.fkCardapio = fkCardapio;
    }

    @Override
    public String toString() {
        return "Orcamento{" +
                "id=" + id +
                ", dataEvento=" + dataEvento +
                ", qtdConvidados=" + qtdConvidados +
                ", status='" + status + '\'' +
                ", inicio=" + inicio +
                ", fim=" + fim +
                ", sugestao='" + sugestao + '\'' +
                ", fkUsuario='" + fkUsuario + '\'' +
                ", fkTipoEvento='" + fkTipoEvento + '\'' +
                ", fkDecoracao='" + fkDecoracao + '\'' +
                ", fkCardapio='" + fkCardapio + '\'' +
                '}';
    }
}
