package br.com.backstage.model;

import br.com.backstage.annotation.Coluna;
import br.com.backstage.annotation.Descricao;

import javax.persistence.*;
import java.math.BigDecimal;

@Descricao(descricao = "Tabela de Funcionarios da Producao")
@Entity
@Table(name = "TAB_BACKSTAGE_FUNC")
@Inheritance(strategy = InheritanceType.JOINED)
public class Funcionario {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_func"
    )
    @SequenceGenerator(
            name = "seq_func",
            sequenceName = "SEQ_BACKSTAGE_FUNC",
            allocationSize = 1
    )
    private Long id;

    @Coluna(
            nome = "NM_FUNCIONARIO",
            tipo = "VARCHAR2(100)"
    )
    @Column(
            name = "NM_FUNCIONARIO",
            nullable = false,
            length = 100
    )
    private String nome;

    @Coluna(
            nome = "NR_HORAS_TRABALHADAS",
            tipo = "NUMBER(10,2)"
    )
    @Column(
            name = "NR_HORAS_TRABALHADAS",
            nullable = false
    )
    private double horasTrabalhadas;

    @Coluna(
            nome = "VL_HORA",
            tipo = "NUMBER(10,2)"
    )
    @Column(
            name = "VL_HORA",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal valorPorHora;

    public Funcionario() {
    }

    public Funcionario(String nome, double horasTrabalhadas, BigDecimal valorPorHora) {
        this.nome = nome;
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorPorHora = valorPorHora;
    }

    public BigDecimal calcularSalario() {
        return valorPorHora.multiply(BigDecimal.valueOf(horasTrabalhadas));
    }

    public String imprimirInformacao() {
        return String.format(
                "=== Funcionario ===\nNome: %s\nHoras Trabalhadas: %.1f h\nValor/Hora: R$ %.2f\nSalario Final: R$ %.2f",
                nome, horasTrabalhadas, valorPorHora, calcularSalario()
        );
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(double horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public BigDecimal getValorPorHora() {
        return valorPorHora;
    }

    public void setValorPorHora(BigDecimal valorPorHora) {
        this.valorPorHora = valorPorHora;
    }
}