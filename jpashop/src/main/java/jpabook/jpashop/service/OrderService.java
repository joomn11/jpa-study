package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // select entity
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // create delivery
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // create orderItem
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // create order
        Order order = Order.createOrder(member, delivery, orderItem);

        // save order
        orderRepository.save(order); // cascade를 통해서 orderItem, deleivery가 함께 저장 된다.
        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel(); // JPA의 장점, 내부의 상태값을 바꾼것을 DB에 적용하기 위해서 비즈니스 로직에 쿼리를 날리는 등의 작업이 필요 하지만 JPA는 다해줌
    }

    // 검색
//    public List<Order> findOrders() {}
}
