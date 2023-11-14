package ordersTrackingSystem.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@NotBlank(message = "product id must be blank")
	@Column(name = "prod_id", unique = true)
	private String prodId;

	@NotBlank(message = "product name must be blank")
	@Column(name = "prod_name")
	private String prodName;

	@NotBlank(message = "product description must be blank")
	@Column(name = "description")
	private String prodDescription;

	@Positive(message = "price must be positive")
	@Column(name = "Price")
	private Double Price;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderItem> orderItems = new ArrayList<>();

	public Product() {

	}

	public Product(String prodId, String prodName, String prodDescription, Double price, List<OrderItem> orderItems) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.prodDescription = prodDescription;
		Price = price;
		this.orderItems = orderItems;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDescription() {
		return prodDescription;
	}

	public void setProdDescription(String prodDescription) {
		this.prodDescription = prodDescription;
	}

	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double price) {
		Price = price;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Price, orderItems, prodDescription, prodId, prodName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(Price, other.Price) && Objects.equals(orderItems, other.orderItems)
				&& Objects.equals(prodDescription, other.prodDescription) && Objects.equals(prodId, other.prodId)
				&& Objects.equals(prodName, other.prodName);
	}

	@Override
	public String toString() {
		return "Product [prodId=" + prodId + ", prodName=" + prodName + ", prodDescription=" + prodDescription
				+ ", Price=" + Price + ", orderItems=" + orderItems + "]";
	}

}
