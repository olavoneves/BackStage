package br.com.backstage;

import br.com.backstage.model.*;
import br.com.backstage.repository.TabelaFuncionarios;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        TabelaFuncionarios tabela = new TabelaFuncionarios();

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        BackStage — RH de uma Produtora   ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        // ─────────────────────────────────────────
        // Instanciando funcionários
        // ─────────────────────────────────────────
        DiretorSenior diretor = new DiretorSenior(
                "Christopher Nolan",
                60,
                new BigDecimal(
                        "350.00"
                ),
                new BigDecimal(
                        "500.00"
                )
        );

        Ator ator = new Ator(
                "Leonardo DiCaprio",
                40,
                new BigDecimal(
                        "200.00"
                ),
                12,
                new BigDecimal(
                        "1500.00"
                )
        );

        Roteirista roteirista = new Roteirista(
                "Quentin Tarantino",
                50,
                new BigDecimal(
                        "180.00"
                ),
                95,
                new BigDecimal(
                        "80.00"
                )
        );

        Produtor produtor = new Produtor(
                "Kathleen Kennedy",
                45,
                new BigDecimal(
                        "250.00"
                ),
                new BigDecimal(
                        "50000000.00"
                )
        );

        // ─────────────────────────────────────────
        // Demonstração do Reflection gerando SQL
        // ─────────────────────────────────────────
        System.out.println("══════════════════════════════════════════");
        System.out.println("  [REFLECTION] SQL gerado automaticamente");
        System.out.println("══════════════════════════════════════════");

        tabela.gerarSqlSelect(diretor);
        tabela.gerarSqlSelect(ator);
        tabela.gerarSqlCreateTable(ator);
        tabela.gerarSqlCreateTable(roteirista);

        // ─────────────────────────────────────────
        // Imprimindo informações dos funcionários
        // ─────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  [INFO] Informacoes dos Funcionarios");
        System.out.println("══════════════════════════════════════════");

        System.out.println(diretor.imprimirInformacao());
        System.out.println();
        System.out.println(ator.imprimirInformacao());
        System.out.println();
        System.out.println(roteirista.imprimirInformacao());
        System.out.println();
        System.out.println(produtor.imprimirInformacao());

        // ─────────────────────────────────────────
        // CREATE — persistindo no Oracle
        // ─────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  [CRUD] CREATE — Inserindo no banco");
        System.out.println("══════════════════════════════════════════");

        tabela.salvar(diretor);
        tabela.salvar(ator);
        tabela.salvar(roteirista);
        tabela.salvar(produtor);

        // ─────────────────────────────────────────
        // READ — buscando todos
        // ─────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  [CRUD] READ — Buscando todos");
        System.out.println("══════════════════════════════════════════");

        List<Funcionario> todos = tabela.buscarTodos();
        todos.forEach(funcionario ->
                System.out.println("-> " + funcionario.imprimirInformacao() + "\n")
        );

        // ─────────────────────────────────────────
        // READ — buscando por ID
        // ─────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  [CRUD] READ — Buscando por ID");
        System.out.println("══════════════════════════════════════════");

        Funcionario funcionario = tabela.buscarPorId(diretor.getId());

        if (funcionario != null)
            System.out.println(funcionario.imprimirInformacao());

        // ─────────────────────────────────────────
        // UPDATE — alterando salário do ator
        // ─────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  [CRUD] UPDATE — Atualizando funcionario");
        System.out.println("══════════════════════════════════════════");

        ator.setValorPorHora(new BigDecimal("250.00"));
        ator.setNumeroDeCenas(15);
        tabela.atualizar(ator);

        System.out.println("Novo salario do ator: R$ " + ator.calcularSalario());

        // ─────────────────────────────────────────
        // DELETE — removendo roteirista
        // ─────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  [CRUD] DELETE — Removendo funcionario");
        System.out.println("══════════════════════════════════════════");

        tabela.deletar(roteirista.getId());

        // ─────────────────────────────────────────
        // READ final — confirmando deleção
        // ─────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  [CRUD] READ FINAL — Estado atual do BD");
        System.out.println("══════════════════════════════════════════");

        tabela
                .buscarTodos()
                .forEach(
                        f -> System.out.println("-> " + f.getNome())
                );

        tabela.fechar();
    }
}