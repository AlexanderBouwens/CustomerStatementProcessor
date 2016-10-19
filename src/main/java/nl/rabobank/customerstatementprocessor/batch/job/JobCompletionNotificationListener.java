package nl.rabobank.customerstatementprocessor.batch.job;

import java.util.List;

import nl.rabobank.customerstatementprocessor.Transaction;
import nl.rabobank.customerstatementprocessor.TransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Alexander Bouwens
 * 
 * This class runs when it notices that the associated batch job has been completed.
 *
 */
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Autowired
	TransactionService transactionService; 

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Transaction import job completed.");
			log.info("The following transactions have been imported: ");
			List<Transaction> importedTransactions = transactionService.getTransactions();
			for (Transaction transaction : importedTransactions) {
				log.info("Transaction " + transaction + " has been imported.");
			}
		}
	}
}
