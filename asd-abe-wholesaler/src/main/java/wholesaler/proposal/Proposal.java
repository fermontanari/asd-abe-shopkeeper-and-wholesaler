package wholesaler.proposal;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Proposal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Double price;
	private String deliveryDate;
	private Status status;
	private URL orderRef;

	public enum Status {
		Accepted, Rejected, Open;
	}

	public long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public URL getOrderRef() {
		return orderRef;
	}

	public void setOrderRef(URL orderRef) {
		this.orderRef = orderRef;
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