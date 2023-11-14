package ordersTrackingSystem.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "orderitems")
public class OrderItem {

	@NotNull(message = "primary key must not be null")
	@EmbeddedId
	private OrderItemCompositePk orderItemKey;

	@Positive(message = "quantity must be greater than zero")
	@NotNull(message = "quantity must not be null")
	@Column(name = "qty")
	private int qty;

	@Positive(message = "price must be greater than zero")
	@Column(name = "price")
	private Double price;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	private Order order;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_id", insertable = false, updatable = false)
	private Product product;

	public OrderItem() {

	}

	public OrderItem(OrderItemCompositePk orderItemKey, int qty, Double price, Order order, Product product) {
		this.orderItemKey = orderItemKey;
		this.qty = qty;
		this.price = price;
		this.order = order;
		this.product = product;
	}

	public OrderItemCompositePk getOrderItemKey() {
		return orderItemKey;
	}

	public void setOrderItemKey(OrderItemCompositePk orderItemKey) {
		this.orderItemKey = orderItemKey;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(order, orderItemKey, price, product, qty);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		return Objects.equals(order, other.order) && Objects.equals(orderItemKey, other.orderItemKey)
				&& Objects.equals(price, other.price) && Objects.equals(product, other.product) && qty == other.qty;
	}

	@Override
	public String toString() {
		return "OrderItem [orderItemKey=" + orderItemKey + ", qty=" + qty + ", price=" + price + ", order=" + order
				+ ", product=" + product + "]";
	}

}