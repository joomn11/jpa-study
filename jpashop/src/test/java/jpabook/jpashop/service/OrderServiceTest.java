package jpabook.jpashop.service;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughtStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("JPA Study", 10000, 10);

        int orderCnt = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCnt);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        Assert.assertEquals("order status is ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("order Total Count", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("order price is price * orderCnt", 10000 * 2, getOrder.getTotalPrice());
        Assert.assertEquals("stock is minus orderCnt", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughtStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("JPA Study", 10000, 10);

        int orderCnt = 11;

        // when
        orderService.order(member.getId(), book.getId(), orderCnt);

        // then
        Assert.fail("exception should be occur, not enough stock");
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("JPA Study", 10000, 10);

        int orderCnt = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCnt);
        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        Assert.assertEquals("order status is CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("stock is plus orderCnt", 10, book.getStockQuantity());
    }


    private Item createBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("Seoul", "moon Street", "123-123"));
        em.persist(member);
        return member;
    }

}