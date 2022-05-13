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
import net.guides.springboot.crud.model.GeneralOrder;
import net.guides.springboot.crud.repository.generalOrderRepository;
import net.guides.springboot.crud.service.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class GeneralOrdersController {
	@Autowired
	private generalOrderRepository generalOrderRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/generalOrders")
	public List<GeneralOrder> getAllOrders() {
		return generalOrderRepository.findAll();
	}

	@GetMapping("/generalOrders/{id}")
	public ResponseEntity<GeneralOrder> getOrderById(@PathVariable(value = "id") Long orderId)
			throws ResourceNotFoundException {
		GeneralOrder order = generalOrderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("GeneralOrder not found for this id :: " + orderId));
		return ResponseEntity.ok().body(order);
	}

	@PostMapping("/generalOrders")
	public GeneralOrder createOrder(@Valid @RequestBody GeneralOrder order) {
		order.setId(sequenceGeneratorService.generateSequence(GeneralOrder.SEQUENCE_NAME));		
		return generalOrderRepository.save(order);
	}

	@PutMapping("/generalOrders/{id}")
	public ResponseEntity<GeneralOrder> updateOrder(@PathVariable(value = "id") Long orderId,
			@Valid @RequestBody GeneralOrder orderDetails) throws ResourceNotFoundException {
		GeneralOrder order = generalOrderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("GeneralOrder not found for this id :: " + orderId));

		order.setCategory(orderDetails.getCategory());
		order.setMaxBudget(orderDetails.getMaxBudget());
		order.setDuration(orderDetails.getDuration());
		order.setMinBudget(orderDetails.getMinBudget());
		
		
		final GeneralOrder updatedOrder = generalOrderRepository.save(order);
		return ResponseEntity.ok(updatedOrder);
	}

	@DeleteMapping("/generalOrders/{id}")
	public Map<String, Boolean> deleteOrder(@PathVariable(value = "id") Long orderId)
			throws ResourceNotFoundException {
		GeneralOrder order = generalOrderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("GeneralOrder not found for this id :: " + orderId));

		generalOrderRepository.delete(order);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}

