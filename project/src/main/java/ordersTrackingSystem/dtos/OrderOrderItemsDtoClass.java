package ordersTrackingSystem.dtos;

import ordersTrackingSystem.entities.Order;
import ordersTrackingSystem.entities.OrderItem;

public class OrderOrderItemsDtoClass implements OrderOrderItemDto {

	private Order order;
	private OrderItem orderItem;

	@Override
	public Order getOrder() {
		return order;
	}

	@Override
	public OrderItem getOrderItem() {
		return orderItem;
	}

	@Override
	public Order setOrder() {
		return order;
	}

	@Override
	public OrderItem setOrderItem() {
		return orderItem;
	}
}
