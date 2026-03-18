package br.com.backstage.repository;

import br.com.backstage.annotation.Coluna;
import br.com.backstage.annotation.Descricao;
import br.com.backstage.model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.lang.reflect.Field;
import java.util.List;

public class TabelaFuncionarios {

    private static final String PERSISTENCE_UNIT = "BACKSTAGE_PU";

    private EntityManagerFactory emf;
    private EntityManager em;

    public TabelaFuncionarios() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        em  = emf.createEntityManager();
    }

    public String gerarSqlSelect(Object obj) {
        Class<?> clazz = obj.getClass();

        if (!clazz.isAnnotationPresent(Descricao.class)) {
            throw new IllegalArgumentException("Classe sem @Descricao: " + clazz.getSimpleName());
        }

        Descricao descricao = clazz.getAnnotation(Descricao.class);
        String nomeTabela = "TAB_" + descricao.descricao()
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "_");

        String sql = "SELECT * FROM " + nomeTabela;
        System.out.println("[SQL GERADO - SELECT] " + sql);
        return sql;
    }

    public String gerarSqlCreateTable(Object obj) {
        Class<?> clazz = obj.getClass();

        if (!clazz.isAnnotationPresent(Descricao.class)) {
            throw new IllegalArgumentException("Classe sem @Descricao: " + clazz.getSimpleName());
        }

        Descricao descricao = clazz.getAnnotation(Descricao.class);
        String nomeTabela = "TAB_" + descricao.descricao()
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "_");

        StringBuilder sql = new StringBuilder("CREATE TABLE " + nomeTabela + " (\n");
        sql.append("  ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,\n");

        Class<?> cursor = clazz;
        while (cursor != null && cursor != Object.class) {
            for (Field field : cursor.getDeclaredFields()) {
                if (field.isAnnotationPresent(Coluna.class)) {
                    Coluna coluna = field.getAnnotation(Coluna.class);
                    sql.append("  ").append(coluna.nome())
                            .append(" ").append(coluna.tipo()).append(",\n");
                }
            }
            cursor = cursor.getSuperclass();
        }

        int lastComma = sql.lastIndexOf(",");
        sql.deleteCharAt(lastComma);
        sql.append(")");

        System.out.println("[SQL GERADO - CREATE TABLE]\n" + sql);
        return sql.toString();
    }

    public void salvar(Funcionario funcionario) {
        System.out.println("\n[SQL - INSERT] Persistindo: " + funcionario.getNome());

        em.getTransaction().begin();
        em.persist(funcionario);
        em.getTransaction().commit();

        System.out.println("[INSERT] Funcionario salvo com sucesso!");
    }

    public Funcionario buscarPorId(Long id) {
        System.out.println("\n[SQL - SELECT] SELECT * FROM TAB_BACKSTAGE_FUNC WHERE ID = " + id);

        Funcionario funcionario = em.find(Funcionario.class, id);

        if (funcionario != null)
            System.out.println("[SELECT] Encontrado: " + funcionario.getNome());
        else
            System.out.println("[SELECT] Nenhum funcionario encontrado com ID " + id);

        return funcionario;
    }

    public List<Funcionario> buscarTodos() {
        System.out.println("\n[SQL - SELECT] SELECT * FROM TAB_BACKSTAGE_FUNC");

        List<Funcionario> funcionarios = em.createQuery("SELECT funcionario FROM Funcionario funcionario", Funcionario.class)
                .getResultList();

        System.out.println("[SELECT] Total encontrado: " + funcionarios.size());

        return funcionarios;
    }

    public void atualizar(Funcionario funcionario) {
        System.out.println("\n[SQL - UPDATE] UPDATE TAB_BACKSTAGE_FUNC SET ... WHERE ID = " + funcionario.getNome());

        em.getTransaction().begin();
        em.merge(funcionario);
        em.getTransaction().commit();

        System.out.println("[UPDATE] Funcionario atualizado com sucesso!");
    }

    public void deletar(Long id) {
        System.out.println("\n[SQL - DELETE] DELETE FROM TAB_BACKSTAGE_FUNC WHERE ID = " + id);

        Funcionario f = em.find(Funcionario.class, id);

        if (f != null) {
            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();

            System.out.println("[DELETE] Funcionario removido com sucesso!");

        } else {
            System.out.println("[DELETE] Funcionario com ID " + id + " nao encontrado.");
        }
    }

    public void fechar() {
        if (em != null && em.isOpen())   em.close();
        if (emf != null && emf.isOpen()) emf.close();

        System.out.println("\n[JPA] Conexao encerrada.");
    }
}