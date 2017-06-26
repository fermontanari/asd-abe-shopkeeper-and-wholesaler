package shopkeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import shopkeeper.order.ProductsOrder;
import shopkeeper.order.ProductsOrderRepository;
import shopkeeper.product.ProductRepository;
import shopkeeper.proposal.Proposal;
import shopkeeper.proposal.ProposalRepository;

@RestController("/orchestration")
public class ShopkeeperController {

	private static final String ACCEPT_TASK = "/accept";
	private static final String REJECT_TASK = "/reject";
	private static final String ORDER_PATH = "order/";
	private static final String PROPOSAL_PATH = "proposal";
	private static final String BASE_PATH = "http://localhost:13080/wholesaler/v1/";

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProposalRepository proposalRepository;

	@Autowired
	ProductsOrderRepository orderRepository;

	@RequestMapping(value = "/orchestration/orders", method = RequestMethod.GET)
	public ResponseEntity<Iterable<ProductsOrder>> productsList(){
		return ResponseEntity.ok(orderRepository.findAll());
	}

	@RequestMapping(value = "/orchestration/order/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductsOrder> order(@PathVariable Long id, Model model){
		model.addAttribute("product", productRepository.findOne(id));
		return ResponseEntity.ok(orderRepository.findOne(id));
	}

	@RequestMapping(value = "/orchestration/order", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProductsOrder> saveOrder(@RequestBody ProductsOrder order) {
		if (order.getStatus() != null && order.getDeliveryDate() != null && order.getPrice() != null){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(order);
		}
		//TODO: Send notification
		productRepository.save(order.getProducts());
		order = orderRepository.save(order);

		final String uri = BASE_PATH + ORDER_PATH;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ProductsOrder> wholesalerResponse = restTemplate.getForEntity(uri, ProductsOrder.class, order);
		if (wholesalerResponse.getStatusCode().equals(HttpStatus.OK)){
			return ResponseEntity.ok(order);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(order);
	}

	@RequestMapping(value = "/orchestration/order/{id}/manufactoring", method = RequestMethod.POST)
	public ResponseEntity<ProductsOrder> transitionOrdeManufactoring(@PathVariable Long id){
		ProductsOrder order = orderRepository.findOne(id);
		if (order.getStatus().equals(ProductsOrder.Status.Ordered)){
			order.setStatus(ProductsOrder.Status.Manufactoring);
			orderRepository.save(order);

			return ResponseEntity.ok(orderRepository.findOne(id));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(order);
	}

	@RequestMapping(value = "/orchestration/order/{id}/dispatch", method = RequestMethod.POST)
	public ResponseEntity<ProductsOrder> transitionOrdeDispatched(@PathVariable Long id){
		ProductsOrder order = orderRepository.findOne(id);
		if (order.getStatus().equals(ProductsOrder.Status.Manufactoring)){
			order.setStatus(ProductsOrder.Status.Dispatched);
			orderRepository.save(order);
			return ResponseEntity.ok(orderRepository.findOne(id));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(order);
	}

	@RequestMapping(value = "/orchestration/order/{id}/close", method = RequestMethod.POST)
	public ResponseEntity<ProductsOrder> transitionOrdeClosed(@PathVariable Long id){
		ProductsOrder order = orderRepository.findOne(id);
		if (order.getStatus().equals(ProductsOrder.Status.Dispatched)){
			order.setStatus(ProductsOrder.Status.Closed);
			orderRepository.save(order);

			return ResponseEntity.ok(orderRepository.findOne(id));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(order);
	}

	@RequestMapping(value = "/orchestration/proposal/{id}",method = RequestMethod.GET)
	public ResponseEntity<Proposal> proposal(@PathVariable Long id){
		return ResponseEntity.ok(proposalRepository.findOne(id));
	}

	@RequestMapping(value = "/orchestration/proposal", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Proposal> saveProposal(@RequestBody Proposal proposal) {
		String orderId = proposal.getOrderRef() != null ? proposal.getOrderRef().toString() : null;
		if (proposal.getStatus() == null && orderId != null && orderRepository.findOne(Long.valueOf(orderId.substring(orderId.lastIndexOf("/")))) != null){
			proposal.setStatus(Proposal.Status.Open);
			proposalRepository.save(proposal);
			return ResponseEntity.ok(proposal);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(proposal);
	}

	@RequestMapping(value = "/orchestration/proposal/{id}/accept", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Proposal> acceptProposal(@PathVariable Long id) {
		Proposal proposal = proposalRepository.findOne(id);

		String orderId = proposal.getOrderRef() != null ? proposal.getOrderRef().toString() : "";
		if (!orderId.isEmpty()){
			ProductsOrder order = orderRepository.findOne(Long.valueOf(orderId.substring(orderId.lastIndexOf("/"))));

			if (order != null){
				order.setPrice(proposal.getPrice());
				order.setDeliveryDate(proposal.getDeliveryDate());
				order.setStatus(ProductsOrder.Status.Ordered);
				orderRepository.save(order);

				proposal.setStatus(Proposal.Status.Accepted);
				proposalRepository.save(proposal);

				final String uri = BASE_PATH + PROPOSAL_PATH + id + ACCEPT_TASK;
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<Proposal> wholesalerResponse = restTemplate.getForEntity(uri, Proposal.class);
				if (wholesalerResponse.getStatusCode().equals(HttpStatus.OK)){
					return ResponseEntity.ok(proposal);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(proposal);
	}

	@RequestMapping(value = "/orchestration/proposal/{id}/reject", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Proposal> rejectProposal(@PathVariable Long id) {
		Proposal proposal = proposalRepository.findOne(id);
		proposal.setStatus(Proposal.Status.Rejected);
		proposalRepository.save(proposal);

		final String uri = BASE_PATH + PROPOSAL_PATH + id + REJECT_TASK;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Proposal> wholesalerResponse = restTemplate.getForEntity(uri, Proposal.class);
		if (wholesalerResponse.getStatusCode().equals(HttpStatus.OK)){
			return ResponseEntity.ok(proposal);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(proposal);
	}

}