package nl.rabobank.customerstatementprocessor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Alexander Bouwens
 * 
 * The Transaction class contains all information regarding a transaction. 
 *
 */
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@XmlRootElement(name = "record")
@Table(name = "TRANSACTION")
public class Transaction {

	@Id
	private long reference;
	
	// IBAN
	@Column(nullable=false, length=512)
	private String accountNumber;
	
	@Column(nullable=false, scale=2)
	private double startBalance;
	
	@Column(nullable=false, scale=2)
	private double mutation;
	
	@Column(length=512)
	private String description;
	
	@Column(nullable=false, scale=2)
	private double endBalance;

	@XmlAttribute(name = "reference")
	public long getReference() {
		return reference;
	}

	@XmlElement(name = "accountNumber")
	public String getAccountNumber() {
		return accountNumber;
	}
	
	@XmlElement(name = "startBalance")
	public double getStartBalance() {
		return startBalance;
	}

	@XmlElement(name = "mutation")
	public double getMutation() {
		return mutation;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	@XmlElement(name = "endBalance")
	public double getEndBalance() {
		return endBalance;
	}
	
}
