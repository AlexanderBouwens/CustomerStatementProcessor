package nl.rabobank.customerstatementprocessor.batch.job.xml;

import nl.rabobank.customerstatementprocessor.Transaction;
import nl.rabobank.customerstatementprocessor.batch.job.JobCompletionNotificationListener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Alexander Bouwens
 *
 *	This class configures the batch job for the import of transactions from a xml file.
 *
 */
@Configuration
@EnableBatchProcessing
public class XMLBatchJobConfiguration {

	public static final String JOB_NAME = "importXMLTransactionJob";
	
	private static final String STEP_NAME = "readXMLTransaction";
	
	private static final String ROOT_ELEMENT_NAME = "record";
	
	private static final int BATCH_SIZE = 1;
	
	@Autowired
    private JobBuilderFactory jobBuilder;

    @Autowired
    private StepBuilderFactory stepBuilder;
    
    @Bean
    public StaxEventItemReader<Transaction> xmlReader() {
    	StaxEventItemReader<Transaction> reader = new StaxEventItemReader<Transaction>();
        reader.setResource(new ClassPathResource("records.xml"));
        reader.setFragmentRootElementName(ROOT_ELEMENT_NAME);
        reader.setUnmarshaller(transactionUnmarshaller());
        return reader;
    }
    
    @Bean
    public TransactionUnmarshaller transactionUnmarshaller() {
		return new TransactionUnmarshaller();
    }

    /**
     * Initializes the TransactionItemProcessor class that validates the transactions read from the flat file.
     * 
     * @return XMLTransactionItemProcessor
     */
    @Bean
    public XMLTransactionItemProcessor xmlProcessor() {
        return new XMLTransactionItemProcessor();
    }

    /**
     * Initializes the JobExecutionListener that finalizes the report 
     * 
     * @return JobExecutionListener
     */
    @Bean
    public JobExecutionListener xmlListener() {
        return new JobCompletionNotificationListener();
    }
    
    /**
     * Initializes the TransactionWriter that writes the transactions to the database.
     * 
     * @return JobExecutionListener
     */
    @Bean
    public XMLTransactionWriter xmlTransactionWriter() {
        return new XMLTransactionWriter();
    }


    /**
     * Configures the import transaction job
     * 
     * @param resource The flat file containing the transaction data.
     * @return Job The batch job to execute
     */
    @Bean
    public Job importXMLTransactionJob() {
        return jobBuilder.get(JOB_NAME)
                .listener(xmlListener())
                .flow(importXMLTransactionStep())
                .end()
                .build();
    }

    /**
     * Configures the import step of the batch job.
     * 
     * @return
     */
    @Bean
    public Step importXMLTransactionStep() {
        return stepBuilder.get(STEP_NAME)
                .<Transaction, Transaction> chunk(BATCH_SIZE)
                .reader(xmlReader())
                .writer(xmlTransactionWriter())
                .processor(xmlProcessor())
                .build();
    }
}
