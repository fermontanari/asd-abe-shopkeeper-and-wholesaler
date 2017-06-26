package shopkeeper.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import shopkeeper.order.Order;

@Entity
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private long productCode;
	private int quantity;
	private String observation;

	@ManyToOne
	private Order order;

	public long getProductCode() {
		return productCode;
	}

	public void setProductCode(long productCode) {
		this.productCode = productCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

}
