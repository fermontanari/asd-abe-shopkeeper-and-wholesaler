package shopkeeper.order;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import shopkeeper.product.Product;

@Entity
public class Order {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Status status;
	private Double price;
	private String deliveryDate;

	@OneToMany
	private List<Product> products;

	public enum Status {
        Closed, Manufactoring, Ordered, Dispatched;
    }

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

}
