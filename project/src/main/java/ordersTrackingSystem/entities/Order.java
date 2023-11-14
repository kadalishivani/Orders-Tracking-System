package ordersTrackingSystem.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@NotBlank(message = "orderId must not be blank")
	@Column(name = "order_id")
	private String orderId;

	@Column(name = "order_date")
	private LocalDate orderDate;

	@Column(name = "cust_id")
	private int custId;

	@Column(name = "status")
	private char status;

	@FutureOrPresent(message = "delivery date must not be past")
	@Column(name = "delivery_date")
	private LocalDate deliveryDate;

	public Order() {

	}

	public Order(String orderId, LocalDate orderDate, int custId, char status, LocalDate deliveryDate) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.custId = custId;
		this.status = status;
		this.deliveryDate = deliveryDate;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cust_id", insertable = false, updatable = false)
	private Customer customer;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	List<OrderItem> orderItems = new ArrayList<>();

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

//	public List<OrderItem> getOrderitems() {
//		return orderItems;
//	}
//
//	public void setOrderitems(List<OrderItem> orderitems) {
//		this.orderItems = orderitems;
//	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", custId=" + custId + ", status=" + status
				+ ", deliveryDate=" + deliveryDate + "]";
	}

}
