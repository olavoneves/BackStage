package br.com.backstage.model;

import br.com.backstage.annotation.Coluna;
import br.com.backstage.annotation.Descricao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Descricao(descricao = "Tabela de Produtores da Producao")
@Entity
@Table(name = "TAB_BACKSTAGE_PRODUTOR")
public class Produtor extends Funcionario {

    @Coluna(
            nome = "VL_ORCAMENTO_GERENCIADO",
            tipo = "NUMBER(15,2)")
    @Column(
            name = "VL_ORCAMENTO_GERENCIADO",
            precision = 15,
            scale = 2
    )
    private BigDecimal orcamentoGerenciado;

    private static final double PERCENTUAL_COMISSAO = 0.005;

    public Produtor() {
    }

    public Produtor(String nome, double horasTrabalhadas, BigDecimal valorPorHora,
                    BigDecimal orcamentoGerenciado) {
        super(nome, horasTrabalhadas, valorPorHora);
        this.orcamentoGerenciado = orcamentoGerenciado;
    }

    @Override
    public BigDecimal calcularSalario() {
        BigDecimal salarioBase = super.calcularSalario();
        BigDecimal comissao = orcamentoGerenciado.multiply(BigDecimal.valueOf(PERCENTUAL_COMISSAO));
        return salarioBase.add(comissao);
    }

    @Override
    public String imprimirInformacao() {
        BigDecimal comissao = orcamentoGerenciado.multiply(BigDecimal.valueOf(PERCENTUAL_COMISSAO));
        return String.format(
                "=== Produtor ===\nNome: %s\nHoras Trabalhadas: %.1f h\nValor/Hora: R$ %.2f\n" +
                        "Orcamento Gerenciado: R$ %.2f\nComissao (0.5%%): R$ %.2f\nSalario Final: R$ %.2f",
                getNome(), getHorasTrabalhadas(), getValorPorHora(),
                orcamentoGerenciado, comissao, calcularSalario()
        );
    }

    public BigDecimal getOrcamentoGerenciado() { return orcamentoGerenciado; }
    public void setOrcamentoGerenciado(BigDecimal orcamentoGerenciado) { this.orcamentoGerenciado = orcamentoGerenciado; }
}