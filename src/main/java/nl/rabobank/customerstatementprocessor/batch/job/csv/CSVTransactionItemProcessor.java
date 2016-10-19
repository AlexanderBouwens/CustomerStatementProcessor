package nl.rabobank.customerstatementprocessor.batch.job.csv;

import nl.rabobank.customerstatementprocessor.Transaction;
import nl.rabobank.customerstatementprocessor.TransactionService;

import org.apache.commons.math.util.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Alexander Bouwens
 *
 *	The CSVTransactionItemProcessor verifies whether the transactions read from a flat file abide the business rules.
 *	a. All transactions should have a unique reference.
 *  b. The end balance must correspond to the calculated predicted end balance.
 *
 */
public class CSVTransactionItemProcessor implements ItemProcessor<Transaction, Transaction> {
	
    private static final Logger log = LoggerFactory.getLogger("csv");

    @Autowired
	private TransactionService transactionService;
    
    @Override
    public Transaction process(final Transaction transaction) throws Exception {

		if (transactionService.transactionExists(transaction)) {
			log.error(createEndBalanceErrorMessage(transaction));
			return null;
		}
    	
    	double predictedEndBalance = MathUtils.round(transaction.getStartBalance() + transaction.getMutation(), 2);
		if (predictedEndBalance != transaction.getEndBalance()) {
			log.error(createEndBalanceErrorMessage(transaction, predictedEndBalance));
			return null;
    	} 

		return transaction;
    }

	private String createEndBalanceErrorMessage(final Transaction transaction, double predictedEndBalance) {
		StringBuilder message = new StringBuilder();
		message.append("End balance error detected in transaction: ");
		message.append(transaction.getReference());
		message.append(", for account: ");
		message.append(transaction.getAccountNumber());
		message.append(", with description: ");
		message.append(transaction.getDescription());
		message.append( ". \n");
		message.append("The calculated end balance: ");
		message.append(predictedEndBalance);
		message.append(" does not correspond to the given end balance: ");
		message.append(transaction.getEndBalance());
		return message.toString();
	}
	
	private String createEndBalanceErrorMessage(final Transaction transaction) {
		StringBuilder message = new StringBuilder();
		message.append("Not unique reference error detected for transaction: ");
		message.append(transaction.getReference());
		message.append(", for account: ");
		message.append(transaction.getAccountNumber());
		message.append(", with description: ");
		message.append(transaction.getDescription());
		message.append( ".");
		return message.toString();
	}
}
