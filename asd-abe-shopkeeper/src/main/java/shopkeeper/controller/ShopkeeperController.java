package shopkeeper.controller;

import java.net.URI;

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

@RestController()
public class ShopkeeperController {

	private static final String ORDER_PATH = "/order";
	private static final String PROPOSAL_PATH = "/proposal";
	private static final String BASE_PATH = "http://localhost:13080/wholesaler/v1";

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
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
		productRepository.save(order.getProducts());
		order = orderRepository.save(order);

		String uri = BASE_PATH + ORDER_PATH;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ProductsOrder> wholesalerResponse = restTemplate.postForEntity(uri, order, ProductsOrder.class);
		if (wholesalerResponse.getStatusCode().equals(HttpStatus.CREATED)){
			return ResponseEntity.status(HttpStatus.CREATED).body(order);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@RequestMapping(value = "/orchestration/proposal/{id}",method = RequestMethod.GET)
	public ResponseEntity<Proposal> proposal(@PathVariable Long id){
		return ResponseEntity.ok(proposalRepository.findOne(id));
	}

	@RequestMapping(value = "/orchestration/proposal/{id}/accept", method = RequestMethod.POST)
	public ResponseEntity<Proposal> acceptProposal(@PathVariable Long id) {
		Proposal proposal = proposalRepository.findOne(id);

		if (proposal.getOrderRef() != null){
			String orderId = proposal.getOrderRef().toString();
			ProductsOrder order = orderRepository.findOne(Long.valueOf(orderId.substring(orderId.lastIndexOf("/")+1)));

			if (order != null){
				order.setPrice(proposal.getPrice());
				order.setDeliveryDate(proposal.getDeliveryDate());
				order.setStatus(ProductsOrder.Status.Ordered);
				orderRepository.save(order);

				proposal.setStatus(Proposal.Status.Accepted);
				proposalRepository.save(proposal);

				String uri = BASE_PATH + PROPOSAL_PATH + "/" + id;
				RestTemplate restTemplate = new RestTemplate();
				try{
					restTemplate.put(new URI(uri), proposal);
					uri = BASE_PATH + ORDER_PATH + "/" + order.getId();
					restTemplate = new RestTemplate();
					ProductsOrder orderToUpdade = new ProductsOrder();
					orderToUpdade.setDeliveryDate(order.getDeliveryDate());
					orderToUpdade.setPrice(order.getPrice());
					orderToUpdade.setStatus(order.getStatus());
					restTemplate.put(new URI(uri), orderToUpdade);
					return ResponseEntity.status(HttpStatus.CREATED).body(proposal);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@RequestMapping(value = "/orchestration/proposal/{id}/reject", method = RequestMethod.POST)
	public ResponseEntity<Proposal> rejectProposal(@PathVariable Long id) {
		Proposal proposal = proposalRepository.findOne(id);

		String orderId = proposal.getOrderRef().toString();
		ProductsOrder order = orderRepository.findOne(Long.valueOf(orderId.substring(orderId.lastIndexOf("/")+1)));

		if (order != null){
			order.setStatus(ProductsOrder.Status.Closed);
			orderRepository.save(order);
			proposal.setStatus(Proposal.Status.Rejected);
			proposalRepository.save(proposal);

			String uri = BASE_PATH + PROPOSAL_PATH + "/" + id;
			RestTemplate restTemplate = new RestTemplate();
			try{
				restTemplate.put(new URI(uri), proposal);
				uri = BASE_PATH + ORDER_PATH + "/" + order.getId();
				restTemplate = new RestTemplate();
				ProductsOrder orderToUpdade = new ProductsOrder();
				orderToUpdade.setStatus(order.getStatus());
				restTemplate.put(new URI(uri), orderToUpdade);
				return ResponseEntity.status(HttpStatus.CREATED).body(proposal);
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

}