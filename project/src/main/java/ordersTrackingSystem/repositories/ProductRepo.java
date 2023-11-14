package ordersTrackingSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ordersTrackingSystem.entities.Product;

public interface ProductRepo extends JpaRepository<Product, String> {

	// 2 b
	Product findByProdName(String name);

	// 10
	List<Product> findByProdNameContaining(String search);

}
