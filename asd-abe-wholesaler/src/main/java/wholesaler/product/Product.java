package wholesaler.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import wholesaler.order.ProductsOrder;

@Entity
public class Product {

	@Id
	private long id;
	private long productCode;
	private int quantity;
	private String observation;

	@ManyToOne
	private ProductsOrder order;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
