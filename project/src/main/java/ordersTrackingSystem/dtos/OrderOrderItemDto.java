package ordersTrackingSystem.dtos;

import ordersTrackingSystem.entities.Order;
import ordersTrackingSystem.entities.OrderItem;

public interface OrderOrderItemDto {
	Order getOrder();

	OrderItem getOrderItem();

	Order setOrder();

	OrderItem setOrderItem();
}
