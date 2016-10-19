package nl.rabobank.customerstatementprocessor.batch.job.csv;

import nl.rabobank.customerstatementprocessor.Transaction;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * @author Alexander Bouwens
 *
 * The TransactionFieldSetMapper class maps the fields that have been read from a flat file to 
 * the attributes of the Transaction object.
 *
 */
public class TransactionFieldSetMapper implements FieldSetMapper<Transaction> {

	private static final String REFERENCE = "Reference";
	private static final String ACCOUNTNUMBER = "Account number"; 
	private static final String DESCRIPTION = "Description";
	private static final String STARTBALANCE = "Start Balance"; 
	private static final String MUTATION = "Mutation";
	private static final String ENDBALANCE = "End Balance"; 
	
	@Override
	public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {
		Transaction transaction = new Transaction();
		transaction.setReference(fieldSet.readLong(REFERENCE));
		transaction.setAccountNumber(fieldSet.readString(ACCOUNTNUMBER));
		transaction.setMutation(determineMutation(fieldSet.readString(MUTATION)));
		transaction.setStartBalance(fieldSet.readDouble(STARTBALANCE));
		transaction.setDescription(fieldSet.readString(DESCRIPTION));
		transaction.setEndBalance(fieldSet.readDouble(ENDBALANCE));
		return transaction;
	}

	private Double determineMutation(String mutation) {
		if (mutation.startsWith("+")) {
			mutation = mutation.substring(1);
		}
		return Double.valueOf(mutation);
	}
}
