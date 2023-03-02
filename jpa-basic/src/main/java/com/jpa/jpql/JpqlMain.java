package com.jpa.jpql;

import com.jpa.RoleType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpqlMain {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            String userNameParam = "member1";
//
//            Member member = new Member();
//            member.setName(userNameParam);
//            member.setAge(19);
//            em.persist(member);

//            em.flush();
//            em.clear();
//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.name=:username", Member.class);
//            query.setParameter("username", userNameParam);
//
//            Query query1 = em.createQuery("select m.name, m.age from Member m where m.name=?1");
//            query1.setParameter(1, userNameParam);
//
//            List<Member> resultList = query.getResultList();
//            for (Member member1 : resultList) {
//                System.out.println(member1.getName());
//            }
//
//            Member singleResult = query.getSingleResult();
//            System.out.println(singleResult.getName());
//
//            TypedQuery<Member> query2 = em.createQuery("select m from Member m ", Member.class);

//            Query query = em.createQuery("select m.name, m.age from ZMember m where m.name=?1");
//            TypedQuery<MemberDto> query = em.createQuery("select new com.jpa.jpql.MemberDto(m.name, m.age) from ZMember m where m.name=?1", MemberDto.class);
//            query.setParameter(1, userNameParam);

//            List resultList = query.getResultList();
//            Object o = resultList.get(0);
//            Object[] oo = (Object[]) o;

//            List<Object[]> resultList = query.getResultList();
//            Object[] oo = resultList.get(0);
//            System.out.println("oo = " + oo[0]);

//            List<MemberDto> resultList = query.getResultList();
//            MemberDto oo = resultList.get(0);
//            System.out.println("oo = " + oo.getName());

//            for (int i = 0; i < 100; i++) {
//                Member member = new Member();
//                member.setName("userNameParam" + i);
//                member.setAge(i);
//                em.persist(member);
//            }
//
//            List<Member> resultList = em.createQuery("select m from ZMember m order by m.age desc ", Member.class)
//                                        .setFirstResult(10)
//                                        .setMaxResults(10)
//                                        .getResultList();
//
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }
            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Member member = new Member();
            member.setName("userNameParam1");
            member.setAge(20);
            member.setRole(RoleType.ADMIN);
            member.setTeam(team);

            Member member2 = new Member();
            member2.setName("userNameParam2");
            member2.setAge(30);

            em.persist(member);
            em.persist(member2);

            em.flush();
            em.clear();
//            String query = "select m from ZMember m JOIN m.team t ";
//            String query = "select m from ZMember m LEFT JOIN m.team t ";
//            String query = "select m from ZMember m, ZTeam t";
//            String query = "select m from ZMember m LEFT JOIN m.team t on t.name=:tname";
//            String query = "select m from ZMember m JOIN m.team t on t.name=:tname";
            String query = "select m from ZMember m JOIN m.team t where m.role=:roleType";

            List<Member> resultList = em.createQuery(query, Member.class)
                                        .setParameter("roleType", RoleType.ADMIN)
                                        .getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }
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