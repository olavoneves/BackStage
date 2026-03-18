package br.com.backstage.model;

import br.com.backstage.annotation.Coluna;
import br.com.backstage.annotation.Descricao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Descricao(descricao = "Tabela de Atores da Producao")
@Entity
@Table(name = "TAB_BACKSTAGE_ATOR")
public class Ator extends Funcionario {

    @Coluna(
            nome = "NR_CENAS_GRAVADAS",
            tipo = "NUMBER(5)"
    )
    @Column(
            name = "NR_CENAS_GRAVADAS"
    )
    private int numeroDeCenas;

    @Coluna(
            nome = "VL_CACHE_POR_CENA",
            tipo = "NUMBER(10,2)"
    )
    @Column(
            name = "VL_CACHE_POR_CENA",
            precision = 10,
            scale = 2
    )
    private BigDecimal cachePorCena;

    public Ator() {
    }

    public Ator(String nome, double horasTrabalhadas, BigDecimal valorPorHora,
                int numeroDeCenas, BigDecimal cachePorCena) {
        super(nome, horasTrabalhadas, valorPorHora);
        this.numeroDeCenas = numeroDeCenas;
        this.cachePorCena = cachePorCena;
    }

    @Override
    public BigDecimal calcularSalario() {
        BigDecimal salarioBase = super.calcularSalario();
        BigDecimal totalCache = cachePorCena.multiply(BigDecimal.valueOf(numeroDeCenas));
        return salarioBase.add(totalCache);
    }

    @Override
    public String imprimirInformacao() {
        BigDecimal totalCache = cachePorCena.multiply(BigDecimal.valueOf(numeroDeCenas));
        return String.format(
                "=== Ator ===\nNome: %s\nHoras Trabalhadas: %.1f h\nValor/Hora: R$ %.2f\n" +
                        "Cenas Gravadas: %d\nCache por Cena: R$ %.2f\nTotal Cache: R$ %.2f\nSalario Final: R$ %.2f",
                getNome(), getHorasTrabalhadas(), getValorPorHora(),
                numeroDeCenas, cachePorCena, totalCache, calcularSalario()
        );
    }

    public int getNumeroDeCenas() {
        return numeroDeCenas;
    }

    public void setNumeroDeCenas(int numeroDeCenas) {
        this.numeroDeCenas = numeroDeCenas;
    }

    public BigDecimal getCachePorCena() {
        return cachePorCena;
    }

    public void setCachePorCena(BigDecimal cachePorCena) {
        this.cachePorCena = cachePorCena;
    }
}