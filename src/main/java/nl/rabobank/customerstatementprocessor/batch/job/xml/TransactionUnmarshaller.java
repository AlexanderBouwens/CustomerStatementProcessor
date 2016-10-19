package nl.rabobank.customerstatementprocessor.batch.job.xml;

import nl.rabobank.customerstatementprocessor.Transaction;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * @author Alexander Bouwens
 *
 * This class unmarshalls a xml resource into a Transaction.
 *
 */
public class TransactionUnmarshaller extends Jaxb2Marshaller {{
	setClassesToBeBound(Transaction.class);
}}
