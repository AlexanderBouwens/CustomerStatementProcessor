package nl.rabobank.customerstatementprocessor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Bouwens
 *
 * The TransactionService class handles all modification on the Transaction data.
 *
 */
@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	/**
	 * @param id
	 * @return
	 */
	public Transaction getTransaction(Long id) {
		return transactionRepository.findOne(id);
	}
	
	/**
	 * @return
	 */
	public List<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		Iterable<Transaction> foundTransactions = transactionRepository.findAll();
		for (Transaction transaction : foundTransactions) {
			transactions.add(transaction);
		}
		return transactions;
	}
	
	/**
	 * @param Transaction
	 */
	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	 
	/**
	 * @param Transaction
	 */
	public void patchTransaction(Transaction transaction) {
		if (transactionRepository.exists(transaction.getReference())) {
			transactionRepository.save(transaction);
		}
	}
	
	public boolean transactionExists(Transaction transaction) {
		return (transactionRepository.exists(transaction.getReference()));
	}
	
	/**
	 * @param Transaction
	 * @return
	 */
	public void deleteTransaction(Transaction transaction) {
		transactionRepository.delete(transaction);
	}
	
	/**
	 * @param Transaction
	 * @return
	 */
	public void deleteAllTransactions() {
		transactionRepository.deleteAll();
	}
}
