package ordersTrackingSystem.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import ordersTrackingSystem.dtos.OrderItemsByProdId;
import ordersTrackingSystem.dtos.OrderOrderItemDto;
import ordersTrackingSystem.dtos.OrderOrderItemsDtoClass;
import ordersTrackingSystem.dtos.ProductsAndOrderItemsDto;
import ordersTrackingSystem.entities.Customer;
import ordersTrackingSystem.entities.Order;
import ordersTrackingSystem.entities.Product;
import ordersTrackingSystem.repositories.CustomerRepo;
import ordersTrackingSystem.repositories.OrderItemRepo;
import ordersTrackingSystem.repositories.OrderRepo;
import ordersTrackingSystem.repositories.ProductRepo;

@RestController
public class Controller {

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	OrderItemRepo orderItemRepo;

	// 1-a
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Add Customer details", description = "Provide Customer details to add")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status 200!!  Customer added successfully."),
			@ApiResponse(responseCode = "400", description = "Status 400!! Bad Request."),
			@ApiResponse(responseCode = "403", description = "Status 403!! Forbidden."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.") })
	@PostMapping("/customer/add")
	public void addCustomer(@Valid @RequestBody Customer newCustomer) {
		try {
			customerRepo.save(newCustomer);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 1-b
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "update Customer Details", description = "Update Customer email and mobile number for the given customer name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Status OK!! Updated successfully."),
			@ApiResponse(responseCode = "404", description = "Customer Not found."),
			@ApiResponse(responseCode = "400", description = "BadRequest."),
			@ApiResponse(responseCode = "403", description = "Status 403!! Forbidden."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.") })
	@PutMapping("/customer/update/{CustomerName}")
	public void updateCustomerDetailsByName(@PathVariable("CustomerName") String name,
			@RequestParam("email") String customer_email, @RequestParam("Number") String customer_number) {
		Customer customer = customerRepo.findByCustName(name);
		try {
			if (customer == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
			}
			customer.setEmail(customer_email);
			customer.setMobile(customer_number);
			customerRepo.save(customer);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 1-c
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "delete a Customer", description = "Delete customer details for the given name and email")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Status OK!! Updated successfully."),
			@ApiResponse(responseCode = "404", description = "Customer Not found."),
			@ApiResponse(responseCode = "403", description = "Status 403!! Forbidden."),
			@ApiResponse(responseCode = "400", description = "BadRequest."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.") })
	@DeleteMapping("/customer/delete/{name}")
	public void deleteCustomer(@PathVariable("name") String name, @RequestParam("email") String email) {
		Customer customer = customerRepo.findByCustNameAndEmail(name, email);
		try {
			if (customer == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
			}
			customerRepo.delete(customer);

		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 2-a
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Add Product Details", description = "Provide product details to add")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product added successfully."),
			@ApiResponse(responseCode = "302", description = "Product already existed."),
			@ApiResponse(responseCode = "403", description = "Status 403!! Forbidden."),
			@ApiResponse(responseCode = "400", description = "BadRequest."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.") })
	@PostMapping("/Product/add")
	public void addProduct(@Valid @RequestBody Product newProduct) {
		try {
			if (productRepo.findById(newProduct.getProdId()).isPresent()) {
				throw new ResponseStatusException(HttpStatus.FOUND, "already existed");
			}
			productRepo.save(newProduct);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 2-b
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "update Product Details", description = "Update Product description and price by given product name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Status OK!! Updated successfully."),
			@ApiResponse(responseCode = "404", description = "Customer Not found."),
			@ApiResponse(responseCode = "403", description = "Status 403!! Forbidden."),
			@ApiResponse(responseCode = "400", description = "BadRequest."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.") })
	@PutMapping("/product/update/{ProductName}")
	public void updateProductDetailsByName(@PathVariable("ProductName") String ProductName,
			@RequestParam("Description") String description, @RequestParam("Price") Double price) {
		Product product = productRepo.findByProdName(ProductName);
		try {
			if (product == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
			}
			product.setProdDescription(description);
			product.setPrice(price);
			productRepo.save(product);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 2-c
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete a Product", description = "Delete Product details by productId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Status OK!! Updated successfully."),
			@ApiResponse(responseCode = "404", description = "Product Not found."),
			@ApiResponse(responseCode = "403", description = "Status 403!! Forbidden."),
			@ApiResponse(responseCode = "400", description = "Status 400!! Bad Request."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.") })
	@DeleteMapping("/product/delete/{Id}")
	public void deleteProduct(@PathVariable("Id") String prodId) {
		Optional<Product> product = productRepo.findById(prodId);
		try {
			if (product.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
			}
			productRepo.delete(product.get());

		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 3-a
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	@Operation(summary = "Add order and orderItems", description = "Provide order and orderItem details to add order and orderItem details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status 200!!  order and orderItem created successfully."),
			@ApiResponse(responseCode = "302", description = "found"),
			@ApiResponse(responseCode = "403", description = "Status 403!! Forbidden."),
			@ApiResponse(responseCode = "417", description = "new order status must be n."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/order/orderItem/add")
	public void addAnOrder(@Valid @RequestBody OrderOrderItemsDtoClass orderOrderItemsDtoClass) {
		try {
			// check orderid is present or not
			if (orderRepo.findById(orderOrderItemsDtoClass.getOrder().getOrderId()).isEmpty()) {
				// check whether order is new or not
				if (orderOrderItemsDtoClass.getOrder().getStatus() != 'n') {
					throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
							"new order status can not be delivered or cancelled");
				}
				orderRepo.save(orderOrderItemsDtoClass.getOrder());
				orderItemRepo.save(orderOrderItemsDtoClass.getOrderItem());
			} else {
				throw new ResponseStatusException(HttpStatus.FOUND, "orderid already existed");
			}
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 3-b
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	@Operation(summary = "delete order and orderItems", description = "Delete order and orderItem details for the given orderId")
	@ApiResponses(value = { @ApiResponse(responseCode = "404", description = "not found"),
			@ApiResponse(responseCode = "400", description = "Status 400!! Bad Request."),
			@ApiResponse(responseCode = "403", description = "Status 403!! Forbidden."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@DeleteMapping("/order/orderItem/delete/{OrderId}")
	public void deleteAnOrder(@PathVariable("OrderId") String orderId) {
		try {
			Optional<Order> optionalOrder = orderRepo.findById(orderId);
			if (optionalOrder.isPresent()) {
				var order = optionalOrder.get();
				orderItemRepo.deleteByOrder(order);
				orderRepo.deleteById(order.getOrderId());
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
			}
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 4
	@Operation(summary = "Update status of an order", description = "Update status and delivery date by given orderId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status 200!!Order status updated successfully."),
			@ApiResponse(responseCode = "400", description = "Bad request."),
			@ApiResponse(responseCode = "500", description = "Internal server error"),
			@ApiResponse(responseCode = "404", description = "Not found.") })
	@PutMapping("/status/order/update/{OrderId}")
	public void updateStatusOfAnOrder(@PathVariable("OrderId") String orderId, @RequestParam("Status") char newstatus,
			@RequestParam("DeliveryDate") LocalDate deliveryDate) {
		try {
			Optional<Order> optionalOrder = orderRepo.findByorderId(orderId);
			if (optionalOrder.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "orders not found");
			}
			Order order = optionalOrder.get();
			order.setStatus(newstatus);
			order.setDeliveryDate(deliveryDate);
			orderRepo.save(order);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 5
	@CrossOrigin
	@Operation(summary = "Get all Customer details", description = "Retrieve all Customer details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved all Customer details successfully."),
			@ApiResponse(responseCode = "404", description = "Not found."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/getAllCustomers")
	public List<Customer> getAllCustomers() {
		try {
			List<Customer> customers = customerRepo.findAll();
			if (customers.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customers not found");
			}
			return customers;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 6
	@Operation(summary = "Get all Products details", description = "Retrieve all Product details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved all Products details successfully."),
			@ApiResponse(responseCode = "404", description = "Not found."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/getAllProducts")
	public List<Product> getAllProducts() {
		try {
			List<Product> products = productRepo.findAll();
			if (products.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "products not found");
			}
			return products;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 6 using pagination
	@Operation(summary = "Get Products details", description = "Retrieve Product details by given page number")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved Products details successfully."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/getAllProducts/{pageno}")
	public Page<Product> getProductsByPage(@PathVariable("pageno") Integer pageno) {
		try {
			return productRepo.findAll(PageRequest.of(pageno, 2));
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 7
	@Operation(summary = "Orders Ordered by a Customer", description = " Get Orders Ordered By a Customer (custId)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved order details ordered by a customer details successfully."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/orders/Ordered/{CustomerId}")
	public List<Order> ordersByCustId(@PathVariable("CustomerId") int id) {
		return orderRepo.findByCustId(id);
	}

	// 8
	@Operation(summary = "Get Orders by Status", description = "List of Orders with given Status")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved Order details successfully with given status ."),
			@ApiResponse(responseCode = "404", description = "Orders Not found."),
			@ApiResponse(responseCode = "400", description = "bad request."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/orders/by/status/{Status}")
	public List<Order> getOrdersByStatus(@PathVariable("Status") char status) {
		try {
			List<Order> orders = orderRepo.findByStatus(status);
			if (orders.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "orders not found");
			}
			return orders;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 9
	@Operation(summary = "Get orderitems in the order", description = "Retrieves orderitems in the order-productname,quantity and price")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved all Products details successfully."),
			@ApiResponse(responseCode = "404", description = "Orderitems Not found."),
			@ApiResponse(responseCode = "400", description = "Bad Request."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("list/orderitems/for/the/given/{OrderId}")
	public List<ProductsAndOrderItemsDto> orderItemsInOrder(@PathVariable("OrderId") String orderId) {
		try {
			List<ProductsAndOrderItemsDto> orderitems = orderItemRepo.findByorderitemsInTheOrder(orderId);
			if (orderitems.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "orderitems not found");
			}
			return orderitems;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 10
	@Operation(summary = "Get products containing the search string", description = "Retrieve products when product name matches with the given string ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved Products details successfully."),
			@ApiResponse(responseCode = "404", description = "products Not found."),
			@ApiResponse(responseCode = "400", description = "Bad Request."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/products/string/{Search}")
	public List<Product> getProductsContainingString(@PathVariable("Search") String search) {
		try {
			List<Product> products = productRepo.findByProdNameContaining(search);
			if (products.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "products not found");
			}
			return products;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 11
	@Operation(summary = "Get orderitems for the given product ", description = "Retrieve product name,customer name,quantity,price and order date for the given productId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved orderitems successfully."),
			@ApiResponse(responseCode = "404", description = "Orders Not found."),
			@ApiResponse(responseCode = "400", description = "Bad Request."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/orders/for/Product/{ProdId}")
	public List<OrderItemsByProdId> orderItemsByProduct(@PathVariable("ProdId") String prodId) {
		try {
			List<OrderItemsByProdId> orderItems = orderItemRepo.findByProdId(prodId);
			if (orderItems.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Items not Found");
			}
			return orderItems;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 12
	@Operation(summary = "Get all order and orderitem details for the given orderId", description = "Retrieve order and orderitem details for the given orderId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved order and orderitem  details successfully."),
			@ApiResponse(responseCode = "404", description = "Orders And OrderItems Not found."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/order/orderItem/orderId/{OrderId}")
	public List<OrderOrderItemDto> getAllDetailsOfAnOrder(@PathVariable("OrderId") String orderId) {
		try {
			var orderdetails = orderItemRepo.findAllDetailsByOrderId(orderId);
			if (orderdetails.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "details not Found");
			}
			return orderdetails;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 13
	@Operation(summary = "Get all products ordered by a customer", description = "Retrieve all products ordered by a customer (custId)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved all Products details successfully."),
			@ApiResponse(responseCode = "404", description = "Products Not found."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/AllProductsOrderBYCustomer/{CustomerId}")
	public List<Product> AllProductsOrderBYCustomer(@PathVariable("CustomerId") int customerId) {
		try {
			List<Product> orderedproducts = orderItemRepo.getAllProductsByCust(customerId);
			if (orderedproducts.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "products not found");
			}
			return orderedproducts;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 14
	@Operation(summary = "Get Orders ordered between the dates", description = "Retrieves orders between given 2 dates")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status OK!! Retrieved orders successfully."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error."),
			@ApiResponse(responseCode = "400", description = "bad request."),
			@ApiResponse(responseCode = "404", description = "Orders Not found.") })
	@GetMapping("/ordersBetweenDates")
	public List<Order> ordersBetweenDates(@RequestParam("StartDate") LocalDate startDate,
			@RequestParam("EndDate") LocalDate endDate) {
		try {
			List<Order> orders = orderRepo.findByOrderDateBetween(startDate, endDate);
			if (orders.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orders Not found");
			}
			return orders;
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
