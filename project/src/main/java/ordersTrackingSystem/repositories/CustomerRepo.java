package ordersTrackingSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ordersTrackingSystem.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	// 1 b
	Customer findByCustName(String name);

	// 1 c
	Customer findByCustNameAndEmail(String name, String email);

}
