package nl.rabobank.customerstatementprocessor.batch.job.csv;

import java.util.List;

import nl.rabobank.customerstatementprocessor.Transaction;
import nl.rabobank.customerstatementprocessor.TransactionService;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Alexander Bouwens
 *
 *	The transactionWriter saves validated transactions to the database.
 *
 */
public class CSVTransactionWriter implements ItemWriter<Transaction>{

	@Autowired
	private TransactionService transactionService;

	@Override
	public void write(List<? extends Transaction> transactions) throws Exception {
		for(Transaction transaction : transactions) {
			if (transaction != null) {
				transactionService.saveTransaction(transaction);
			}
		}
	}
}

