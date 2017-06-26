package shopkeeper.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import shopkeeper.order.Order;
import shopkeeper.order.OrderRepository;
import shopkeeper.product.Product;
import shopkeeper.product.ProductRepository;
import shopkeeper.proposal.Proposal;
import shopkeeper.proposal.ProposalRepository;

@RestController("/orchestration")
public class ShopkeeperController {


	@Autowired
    ProductRepository productRepository;

	@Autowired
    ProposalRepository proposalRepository;

	@Autowired
    OrderRepository orderRepository;


    @RequestMapping( value ="/orchestration/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> product(@PathVariable Long id){
        return ResponseEntity.ok(productRepository.findOne(id));
    }

    @RequestMapping( value = "/orchestration/orders", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Order>> productsList(){
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @RequestMapping( value = "/orchestration/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> order(@PathVariable Long id){
        return ResponseEntity.ok(orderRepository.findOne(id));
    }

    @RequestMapping( value = "/orchestration/order/{id}/manufactoring", method = RequestMethod.POST)
    public ResponseEntity<Order> transitionOrdeManufactoring(@PathVariable Long id){
    	Order order = orderRepository.findOne(id);
    	if (order.getStatus().equals(Order.Status.Ordered)){
    		order.setStatus(Order.Status.Manufactoring);
    		orderRepository.save(order);
            return ResponseEntity.ok(orderRepository.findOne(id));
    	}
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(order);
    }

    @RequestMapping( value = "/orchestration/order/{id}/dispatched", method = RequestMethod.POST)
    public ResponseEntity<Order> transitionOrdeDispatched(@PathVariable Long id){
    	Order order = orderRepository.findOne(id);
    	if (order.getStatus().equals(Order.Status.Manufactoring)){
    		order.setStatus(Order.Status.Dispatched);
    		orderRepository.save(order);
            return ResponseEntity.ok(orderRepository.findOne(id));
    	}
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(order);
    }

    @RequestMapping( value = "/orchestration/order/{id}/closed", method = RequestMethod.POST)
    public ResponseEntity<Order> transitionOrdeClosed(@PathVariable Long id){
    	Order order = orderRepository.findOne(id);
    	if (order.getStatus().equals(Order.Status.Dispatched)){
    		order.setStatus(Order.Status.Closed);
    		orderRepository.save(order);
            return ResponseEntity.ok(orderRepository.findOne(id));
    	}
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(order);
    }


    @RequestMapping(value = "/orchestration/order", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
    	/*List<Product> savedProducts = new ArrayList<Product>();
    	for (Product product: products){
    		savedProducts.add(productRepository.save(product));
    	}*/
    	if (order.getStatus() != null && order.getDeliveryDate() != null && order.getPrice() != null){
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(order);
    	}
        //TODO: Send notification
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @RequestMapping( value = "/orchestration/proposal/{id}",method = RequestMethod.GET)
    public ResponseEntity<Proposal> proposal(@PathVariable Long id){
        return ResponseEntity.ok(proposalRepository.findOne(id));
    }

    @RequestMapping(value = "/orchestration/proposal/{id}/accept", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Proposal> acceptProposal(Long id) {
        //TODO: Send notification to wholesaler
    	Proposal proposal = proposalRepository.findOne(id);

    	String orderId = proposal.getOrderId().toString();
    	Order order = orderRepository.findOne(Long.valueOf(orderId.substring(orderId.lastIndexOf("/"))));

    	if (order == null){
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(proposal);
    	}

    	order.setPrice(proposal.getPrice());
    	order.setDeliveryDate(proposal.getDeliveryDate());
    	order.setStatus(Order.Status.Ordered);
    	orderRepository.save(order);

    	proposal.setStatus(Proposal.Status.Accepted);
    	proposalRepository.save(proposal);

        return ResponseEntity.ok(proposal);
    }

    @RequestMapping(value = "/orchestration/proposal/{id}/reject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Proposal> rejectProposal(Long id) {
        //TODO: Send notification to wholesaler
    	Proposal proposal = proposalRepository.findOne(id);
    	proposal.setStatus(Proposal.Status.Rejected);
    	proposalRepository.save(proposal);
        return ResponseEntity.ok(proposal);
    }

    @RequestMapping(value = "/orchestration/proposal", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Proposal> saveProposal(@RequestBody Proposal proposal) {
    	String orderId = Optional.ofNullable(proposal.getOrderId()).toString();
    	if (proposal.getStatus() == null && !orderId.isEmpty() && orderRepository.findOne(Long.valueOf(orderId.substring(orderId.lastIndexOf("/")))) != null){
    		proposal.setStatus(Proposal.Status.Open);
    		proposalRepository.save(proposal);
    		return ResponseEntity.ok(proposal);
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(proposal);
    }

}