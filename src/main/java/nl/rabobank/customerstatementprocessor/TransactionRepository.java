package nl.rabobank.customerstatementprocessor;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Alexander Bouwens
 * 
 * The TransactionRepository provides basic Crud functionality for the Transaction
 *
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
