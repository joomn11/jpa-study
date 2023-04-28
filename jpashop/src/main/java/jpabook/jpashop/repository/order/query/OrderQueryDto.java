package jpabook.jpashop.repository.order.query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public static OrderQueryDto create(OrderFlatDto o) {
        return new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress());
    }

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItemQueryDto> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }

    public static OrderQueryDto create(Entry<OrderQueryDto, List<OrderItemQueryDto>> e) {
        return new OrderQueryDto(e.getKey().getOrderId(),
                                 e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                                 e.getKey().getAddress(), e.getValue());
    }
}
