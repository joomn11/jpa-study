package com.jpa.jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class JpqlMain {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            String userNameParam = "member1";

            Member member = new Member();
            member.setName(userNameParam);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m where m.name=:username", Member.class);
            query.setParameter("username", userNameParam);

            Query query1 = em.createQuery("select m.name, m.age from Member m where m.name=?1");
            query1.setParameter(1, userNameParam);

            List<Member> resultList = query.getResultList();
            for (Member member1 : resultList) {
                System.out.println(member1.getName());
            }

            Member singleResult = query.getSingleResult();
            System.out.println(singleResult.getName());

            TypedQuery<Member> query2 = em.createQuery("select m from Member m ", Member.class);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }


}