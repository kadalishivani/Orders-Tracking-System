package ordersTrackingSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ordersTrackingSystem.dtos.OrderItemsByProdId;
import ordersTrackingSystem.dtos.OrderOrderItemDto;
import ordersTrackingSystem.dtos.ProductsAndOrderItemsDto;
import ordersTrackingSystem.entities.Order;
import ordersTrackingSystem.entities.OrderItem;
import ordersTrackingSystem.entities.OrderItemCompositePk;
import ordersTrackingSystem.entities.Product;

public interface OrderItemRepo extends JpaRepository<OrderItem, OrderItemCompositePk> {

	// 9 using DTO
	@Query("select oi.product.prodName as prodName, oi.qty as qty, oi.price as price from OrderItem as oi where oi.orderItemKey.orderId=:orderId")
	List<ProductsAndOrderItemsDto> findByorderitemsInTheOrder(@Param("orderId") String orderId);

	// 11
	@Query("select oi.product.prodName as prodName ,oi.order.customer.custName as custName,oi.qty as qty,oi.price as price,oi.order.orderDate as orderDate from OrderItem oi where oi.orderItemKey.prodId=:prodId")
	List<OrderItemsByProdId> findByProdId(@Param("prodId") String prodId);

	// 13
	@Query("select oi.product from OrderItem oi where oi.order.custId =:custId")
	List<Product> getAllProductsByCust(@Param("custId") int custId);

	// 12
	@Query("select oi.order as order,oi as orderItem from OrderItem oi where oi.orderItemKey.orderId=:orderId")
	List<OrderOrderItemDto> findAllDetailsByOrderId(@Param("orderId") String orderId);

	// 3 b
	void deleteByOrder(Order order);

}
