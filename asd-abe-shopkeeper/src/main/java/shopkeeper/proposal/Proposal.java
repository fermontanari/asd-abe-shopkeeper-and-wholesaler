package shopkeeper.proposal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Proposal {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private long solicitationCode;
	private double price;
	private String date;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getSolicitationCode() {
		return solicitationCode;
	}
	
	public void setSolicitationCode(long solicitationCode) {
		this.solicitationCode = solicitationCode;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

}
