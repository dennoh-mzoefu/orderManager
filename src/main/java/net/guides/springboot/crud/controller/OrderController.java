package net.guides.springboot.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.guides.springboot.crud.exception.ResourceNotFoundException;
import net.guides.springboot.crud.model.Order;
import net.guides.springboot.crud.repository.OrderRepository;
import net.guides.springboot.crud.service.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/orders")
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Long orderId)
			throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
		return ResponseEntity.ok().body(order);
	}

	@PostMapping("/orders")
	public Order createOrder(@Valid @RequestBody Order order) {
		order.setId(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
		if(order.getType().trim().equals("highschool")) {
			order.setPrice((float) (order.getPages()*11.5*30/order.getDays()));
		}else if(order.getType().trim() == "undergraduate") {
			order.setPrice((float) (order.getPages()*12.5*30/order.getDays()));
		}else if(order.getType().trim() == "masters") {
			order.setPrice((float) (order.getPages()*13.5*30/order.getDays()));
		}else if(order.getType().trim() == "doctorate") {
			order.setPrice((float) (order.getPages()*14.5*30/order.getDays()));
		}else {
			order.setPrice(order.getDays());
		}
		
		return orderRepository.save(order);
	}

	@PutMapping("/orders/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Long orderId,
			@Valid @RequestBody Order orderDetails) throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));

		order.setType(orderDetails.getType());
		order.setLevel(orderDetails.getLevel());
		order.setPages(orderDetails.getPages());
		order.setDays(orderDetails.getDays());
		
		if(order.getType() == "highschool") {
			order.setPrice((float) (order.getPages()*11.5*30/order.getDays()));
		}else if(order.getType() == "undergraduate") {
			order.setPrice((float) (order.getPages()*12.5*30/order.getDays()));
		}else if(order.getType() == "masters") {
			order.setPrice((float) (order.getPages()*13.5*30/order.getDays()));
		}else if(order.getType() == "doctorate") {
			order.setPrice((float) (order.getPages()*14.5*30/order.getDays()));
		}else {
			order.setPrice((float)0);
		}
		
		final Order updatedOrder = orderRepository.save(order);
		return ResponseEntity.ok(updatedOrder);
	}

	@DeleteMapping("/orders/{id}")
	public Map<String, Boolean> deleteOrder(@PathVariable(value = "id") Long orderId)
			throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));

		orderRepository.delete(order);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
