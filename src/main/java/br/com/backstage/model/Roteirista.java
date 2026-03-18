package br.com.backstage.model;

import br.com.backstage.annotation.Coluna;
import br.com.backstage.annotation.Descricao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Descricao(descricao = "Tabela de Roteiristas da Producao")
@Entity
@Table(name = "TAB_BACKSTAGE_ROTEIRISTA")
public class Roteirista extends Funcionario {

    @Coluna(
            nome = "NR_PAGINAS_ENTREGUES",
            tipo = "NUMBER(5)"
    )
    @Column(
            name = "NR_PAGINAS_ENTREGUES"
    )
    private int numeroDePaginas;

    @Coluna(
            nome = "VL_POR_PAGINA",
            tipo = "NUMBER(10,2)"
    )
    @Column(
            name = "VL_POR_PAGINA",
            precision = 10,
            scale = 2
    )
    private BigDecimal valorPorPagina;

    public Roteirista() {
    }

    public Roteirista(String nome, double horasTrabalhadas, BigDecimal valorPorHora,
                      int numeroDePaginas, BigDecimal valorPorPagina) {
        super(nome, horasTrabalhadas, valorPorHora);
        this.numeroDePaginas = numeroDePaginas;
        this.valorPorPagina = valorPorPagina;
    }

    @Override
    public BigDecimal calcularSalario() {
        BigDecimal salarioBase = super.calcularSalario();
        BigDecimal totalPaginas = valorPorPagina.multiply(BigDecimal.valueOf(numeroDePaginas));
        return salarioBase.add(totalPaginas);
    }

    @Override
    public String imprimirInformacao() {
        BigDecimal totalPaginas = valorPorPagina.multiply(BigDecimal.valueOf(numeroDePaginas));
        return String.format(
                "=== Roteirista ===\nNome: %s\nHoras Trabalhadas: %.1f h\nValor/Hora: R$ %.2f\n" +
                        "Paginas Entregues: %d\nValor por Pagina: R$ %.2f\nTotal Paginas: R$ %.2f\nSalario Final: R$ %.2f",
                getNome(), getHorasTrabalhadas(), getValorPorHora(),
                numeroDePaginas, valorPorPagina, totalPaginas, calcularSalario()
        );
    }


}