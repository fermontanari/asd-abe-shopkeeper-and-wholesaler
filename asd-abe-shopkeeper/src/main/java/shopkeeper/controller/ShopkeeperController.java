package shopkeeper.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.google.gson.Gson;

import shopkeeper.product.Product;
import shopkeeper.product.ProductRepository;
import shopkeeper.proposal.Proposal;
import shopkeeper.proposal.ProposalRepository;

@RestController("/solicitation")
public class ShopkeeperController {

    
	@Autowired
    ProductRepository productRepository;
	
	@Autowired
    ProposalRepository proposalRepository;


    @RequestMapping( value ="/solicitation/product/{id}", method = RequestMethod.GET)
    public String product(@PathVariable Long id, Model model){
        return new Gson().toJson(productRepository.findOne(id));
    }

    @RequestMapping(value = "/solicitation/products",method = RequestMethod.GET)
    public String productsList(Model model){
        return new Gson().toJson(productRepository.findAll());
    }

    @RequestMapping(value = "/solicitation/product", method = RequestMethod.POST)
    @ResponseBody
    public String saveProduct(@RequestBody List<Product> products) {
    	List<Product> savedProducts = new ArrayList<Product>();
    	for (Product product: products){
    		savedProducts.add(productRepository.save(product));
    	}
        //TODO: Send notification
        return new Gson().toJson(savedProducts);
    }
    
    @RequestMapping( value = "/solicitation/proposal/{id}",method = RequestMethod.GET)
    public String proposal(@PathVariable Long id, Model model){
        model.addAttribute("proposal", proposalRepository.findOne(id));
        return new Gson().toJson(proposalRepository.findOne(id));
    }
    
    @RequestMapping(value = "/solicitation/proposal/{id}/accept", method = RequestMethod.POST)
    @ResponseBody
    public String acceptProposal(Long id, Model model) {
    	model.addAttribute("proposal", proposalRepository.findOne(id));
        //TODO: Send notification to wholesaler
        return new Gson().toJson(proposalRepository.findOne(id));
    }
    
    @RequestMapping(value = "/solicitation/proposal/{id}/reject", method = RequestMethod.POST)
    @ResponseBody
    public String rejectProposal(Long id, Model model) {
        //TODO: Send notification to wholesaler
        return new Gson().toJson(proposalRepository.findOne(id));
    }
    
    @RequestMapping(value = "/solicitation/proposal", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Proposal> saveProposal(@RequestBody Proposal proposal) {
    	if (proposal.getStatus()== null){
    	proposalRepository.save(proposal);
        return ResponseEntity.ok(proposal);
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(proposal);
    }
    
}