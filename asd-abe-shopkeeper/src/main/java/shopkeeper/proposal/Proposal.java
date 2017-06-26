package shopkeeper.proposal;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Proposal {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private Double price;
	private String deliveryDate;
	private Status status;

	public enum Status {
        Accepted, Rejected, Open;
    }

	private URL orderId;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public URL getOrderId() {
		return orderId;
	}

	public void setOrderId(URL orderId) {
		this.orderId = orderId;
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

	public void setDeliveryDate(String date) {
		this.deliveryDate = date;
	}

}
