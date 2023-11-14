package ordersTrackingSystem.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // to make this class as an Entity
@Table(name = "Customers") // to map this entity with customer table in the database
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_id")
	private int custId;

	@NotBlank(message = "customer name cannot be blank")
	@Column(name = "cust_name")
	private String custName;

	@Email
	@Size(min=10,max = 30, message = "email max length is 30")
	@Column(name = "email")
	private String email;

	@Size(min = 10, max = 10, message = "mobile number must contain 10 digits")
	@Column(name = "mobile")
	private String mobile;

	public Customer() {

	}

	public Customer(String custName, String email, String mobile, List<Order> orders) {
		this.custName = custName;
		this.email = email;
		this.mobile = mobile;
		this.orders = orders;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	List<Order> orders = new ArrayList<>();

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", custName=" + custName + ", email=" + email + ", mobile=" + mobile
				+ ", orders=" + orders + "]";
	}

}
