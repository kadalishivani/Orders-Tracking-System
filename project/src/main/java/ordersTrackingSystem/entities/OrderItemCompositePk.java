package ordersTrackingSystem.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class OrderItemCompositePk implements Serializable {

	@NotBlank(message = "orderId must not be blank")
	@Column(name = "order_id")
	private String orderId;

	@NotBlank(message = "prodId must not be blank")
	@Column(name = "prod_id")
	private String prodId;

	@Override
	public int hashCode() {
		return Objects.hash(orderId, prodId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemCompositePk other = (OrderItemCompositePk) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(prodId, other.prodId);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
}
