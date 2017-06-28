package shopkeeper.proposal;

import java.net.URI;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Proposal {

	@Id
	private long id;
	@NotNull
	private Double price;
	@NotNull
	private String deliveryDate;
	private Status status;
	@NotNull
	private URI orderRef;

	public enum Status {
		Accepted, Rejected, Open;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public URI getOrderRef() {
		return orderRef;
	}

	public void setOrderRef(URI orderRef) {
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
