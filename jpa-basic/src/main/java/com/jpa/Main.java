package com.jpa;

import com.jpa.doamin.Member;
import com.jpa.doamin.Team;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//
//            em.persist(member);

//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id=" + findMember.getId());
//            System.out.println("findMember.name=" + findMember.getName());

//            em.remove(findMember);

//            findMember.setName("HelloJPA");

            // JPQL
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                                    .setFirstResult(1)
//                                    .setMaxResults(5)
//                                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.name = " + member.getName());
//            }

//            MemberTest test = new MemberTest();
//            test.setName("aa");
//            System.out.println("===========");
//            em.persist(test);
//            System.out.println("===========");

            Team teamA = new Team();
            teamA.setName("TeamA");
            em.persist(teamA);

            Member member = new Member();
            member.setName("member1");
//            member.setTeamId(team.getId());
            member.setTeam(teamA);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
//            Long findMemberTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findMemberTeamId);
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);

            findMember.setTeam(teamB);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

//        Main.persistence_context();
    }

    public static void persistence_context() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloB");

            // 영속
//            em.persist(member);
//
//            Member findMember = em.find(Member.class, 101L);
//
//            System.out.println("member.id = " + findMember.getId());
//            System.out.println("member.name = " + findMember.getName());

//            Member findMember1 = em.find(Member.class, 101L);
//            Member findMember2 = em.find(Member.class, 101L);
//
//            System.out.println("result =  " + (findMember2 == findMember1));

//            Member memberA = new Member(150L, "A");
//            Member memberB = new Member(160L, "B");
//
//            em.persist(memberA);
//            em.persist(memberB);
//            System.out.println("============"); // 버퍼링 기능 사용 ..

//            Member member = em.find(Member.class, 150L);
//            member.setName("ZZZZZ");

            MemberTest test = new MemberTest();
            test.setName("aa");
            System.out.println("===========");
            em.persist(test);
            System.out.println("===========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }


}