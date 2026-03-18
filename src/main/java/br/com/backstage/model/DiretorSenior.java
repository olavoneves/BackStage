package br.com.backstage.model;

import br.com.backstage.annotation.Coluna;
import br.com.backstage.annotation.Descricao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Descricao(descricao = "Tabela de Diretores Seniors da Producao")
@Entity
@Table(name = "TAB_BACKSTAGE_DIRETOR")
public class DiretorSenior extends Funcionario {

    @Coluna(
            nome = "VL_BONUS_POR_BLOCO",
            tipo = "NUMBER(10,2)"
    )
    @Column(
            name = "VL_BONUS_POR_BLOCO",
            precision = 10,
            scale = 2
    )
    private BigDecimal bonusPorBloco;

    private static final int HORAS_POR_BLOCO = 15;

    public DiretorSenior() {
    }

    public DiretorSenior(String nome, double horasTrabalhadas, BigDecimal valorPorHora, BigDecimal bonusPorBloco) {
        super(nome, horasTrabalhadas, valorPorHora);
        this.bonusPorBloco = bonusPorBloco;
    }

    @Override
    public BigDecimal calcularSalario() {
        BigDecimal salarioBase = super.calcularSalario();
        int blocos = (int) (getHorasTrabalhadas() / HORAS_POR_BLOCO);
        BigDecimal totalBonus = bonusPorBloco.multiply(BigDecimal.valueOf(blocos));
        return salarioBase.add(totalBonus);
    }

    @Override
    public String imprimirInformacao() {
        int blocos = (int) (getHorasTrabalhadas() / HORAS_POR_BLOCO);
        BigDecimal totalBonus = bonusPorBloco.multiply(BigDecimal.valueOf(blocos));
        return String.format(
                "=== Diretor Senior ===\nNome: %s\nHoras Trabalhadas: %.1f h\nValor/Hora: R$ %.2f\n" +
                        "Blocos de %d horas: %d\nBonus por Bloco: R$ %.2f\nTotal Bonus: R$ %.2f\nSalario Final: R$ %.2f",
                getNome(), getHorasTrabalhadas(), getValorPorHora(),
                HORAS_POR_BLOCO, blocos, bonusPorBloco, totalBonus, calcularSalario()
        );
    }

    public BigDecimal getBonusPorBloco() {
        return bonusPorBloco;
    }

    public void setBonusPorBloco(BigDecimal bonusPorBloco) {
        this.bonusPorBloco = bonusPorBloco;
    }
}