package com.jpa;

import com.jpa.sample.Member;
import com.jpa.sample.Team;
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
//
            Member member = new Member();
            member.setName("member1");
            member.setTeam(teamA);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember.getTeam() = " + findMember.getTeam().getClass());

            System.out.println("===========");
            System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());
            System.out.println("===========");

//            teamA.getMembers().add(member);

//            Album album = new Album();
//            album.setName("A");
//            album.setArtist("ARTI");
//            album.setPrice(100);
//            em.persist(album);
//
//            em.flush();
//            em.clear();
//
//            System.out.println("== find first ==");
//            Member findMember = em.find(Member.class, member.getId());
//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember = " + refMember.getClass());
//            System.out.println("findMember = " + findMember.getClass());
//
//            System.out.println("findMember == refMember  " + (findMember == refMember)); // eventually , this value should be equal !
//
//            em.flush();
//            em.clear();
//
//            System.out.println("== ref first ==");
//            Member refMember1 = em.getReference(Member.class, member.getId());
//            System.out.println("isLoaded : " + emf.getPersistenceUnitUtil().isLoaded(refMember1));
//            Member findMember1 = em.find(Member.class, member.getId());
//            System.out.println("isLoaded : " + emf.getPersistenceUnitUtil().isLoaded(refMember1));
//
//            System.out.println("findMember.id=" + refMember.getId());
//            System.out.println("findMember.name=" + refMember.getName());
//
//            System.out.println("refMember1 = " + refMember1.getClass());
//            System.out.println("refMember1-name = " + refMember1.getClass().getName());
//            System.out.println("findMember1 = " + findMember1.getClass());
//
//            em.flush();
//            em.clear();
//
//            System.out.println("== detech test ==");
//            Member refMember2 = em.getReference(Member.class, member.getId());
//
//            em.detach(refMember2);
//
//            refMember2.getName();

//            Album findAlbum = em.find(Album.class, album.getId());
//            System.out.println("findAlbum = " + findAlbum.getName());

//            Member member = new Member();
//            member.setName("user1");
//            member.setCreatedBy("userA");
//            member.setCreatedDate(LocalDateTime.now());
//
//            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
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