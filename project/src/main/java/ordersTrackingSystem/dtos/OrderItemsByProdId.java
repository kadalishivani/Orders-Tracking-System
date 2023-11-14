package ordersTrackingSystem.dtos;

import java.time.LocalDate;

public interface OrderItemsByProdId {

	String getProdName();

	String getCustName();

	Integer getQty();

	Double getPrice();

	LocalDate getOrderDate();
}
