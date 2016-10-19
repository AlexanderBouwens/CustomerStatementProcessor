package nl.rabobank.customerstatementprocessor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alexander Bouwens
 *
 * The Transaction controller handles all requests regarding Transaction information.
 *
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	
	/**
	 * @return
	 */
	@GetMapping
    public Collection<Transaction> getTransactions() {
		return this.transactionService.getTransactions();
    }
	
	/**
	 * @param id
	 * @return
	 */
	@GetMapping(params = "id")
    public Transaction getTransaction(@RequestParam("id") Long id) {
		return this.transactionService.getTransaction(id);
    }
	
	/**
	 * @param transaction
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postTransaction(@RequestBody Transaction transaction) {
		this.transactionService.saveTransaction(transaction);
    }
	
	/**
	 * @param transaction
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putTransaction(@RequestBody Transaction transaction) {
		this.transactionService.saveTransaction(transaction);
    }
	
	/**
	 * @param transaction
	 */
	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void patchTransaction(@RequestBody Transaction transaction) {
		this.transactionService.patchTransaction(transaction);
	}
	
	/**
	 * @param transaction
	 * @return
	 */
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTransaction(@RequestBody Transaction transaction) {
		this.transactionService.deleteTransaction(transaction);
    }
    
}