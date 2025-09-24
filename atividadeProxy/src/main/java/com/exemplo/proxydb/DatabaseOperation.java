package com.exemplo.proxydb;

import com.exemplo.proxydb.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseOperation implements IDatabaseOperation {

    @Override
    public void execute() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proxyPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Produto p = new Produto();
            p.setNome("Camisa Esportiva");
            p.setPreco(120.0);

            em.persist(p);

            // Forçando erro para testar rollback (pode comentar depois)
            if (Math.random() > 0.5) {
                throw new RuntimeException("Erro simulado ao salvar no banco!");
            }

            em.getTransaction().commit();
            System.out.println("Operação concluída com sucesso!");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("Rollback realizado por causa de erro: " + e.getMessage());
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}
