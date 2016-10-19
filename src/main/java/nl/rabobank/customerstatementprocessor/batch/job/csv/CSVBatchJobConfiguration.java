package nl.rabobank.customerstatementprocessor.batch.job.csv;

import nl.rabobank.customerstatementprocessor.Transaction;
import nl.rabobank.customerstatementprocessor.batch.job.JobCompletionNotificationListener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Alexander Bouwens
 *
 *	This class configures the batch job for the import of transactions from a flat file (*.csv).
 *
 */
@Configuration
@EnableBatchProcessing
public class CSVBatchJobConfiguration {

	public static final String JOB_NAME = "importCSVTransactionJob";
	
	private static final String STEP_NAME = "readCSVTransaction";
	
	private static final int BATCH_SIZE = 1;
	
	@Autowired
    private JobBuilderFactory jobBuilder;

    @Autowired
    private StepBuilderFactory stepBuilder;
    
    @Bean
    public FlatFileItemReader<Transaction> csvReader() {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<Transaction>();
        reader.setResource(new ClassPathResource("records.csv"));
        // Skip the title.
        reader.setLinesToSkip(1);
        reader.setLineMapper(createLineMapper());
        return reader;
    }

    @Bean
	public DefaultLineMapper<Transaction> createLineMapper() {
		return new DefaultLineMapper<Transaction>() {{
            setLineTokenizer(createDelimitedLineTokenizer());
            setFieldSetMapper(new TransactionFieldSetMapper());
        }

		private DelimitedLineTokenizer createDelimitedLineTokenizer() {
			return new DelimitedLineTokenizer() {{
				setDelimiter(DELIMITER_COMMA);
				setNames(getAllColumnNames());
			}};
		}
		
		private String[] getAllColumnNames() {
			String[] allNames = new String[ColumnName.values().length];
			int i = 0;
			for (ColumnName columnName : ColumnName.values()) {
				allNames[i] = columnName.getColumnName();
				i++;
			}
			return allNames;
		}};
	}
    
    /**
     * Initializes the TransactionFieldSetMapper class that maps the fields read from the flat file to the Transaction object.
     * 
     * @return TransactionFieldSetMapper
     */
    @Bean
	public TransactionFieldSetMapper transactionFieldSetMapper() {
		return new TransactionFieldSetMapper();
	}

    /**
     * Initializes the TransactionItemProcessor class that validates the transactions read from the flat file.
     * 
     * @return CSVTransactionItemProcessor
     */
    @Bean
    public CSVTransactionItemProcessor csvProcessor() {
        return new CSVTransactionItemProcessor();
    }

    /**
     * Initializes the JobExecutionListener that finalizes the report 
     * 
     * @return JobExecutionListener
     */
    @Bean
    public JobExecutionListener csvlistener() {
        return new JobCompletionNotificationListener();
    }
    
    /**
     * Initializes the TransactionWriter that writes the transactions to the database.
     * 
     * @return JobExecutionListener
     */
    @Bean
    public CSVTransactionWriter csvTransactionWriter() {
        return new CSVTransactionWriter();
    }


    /**
     * Configures the import transaction job
     * 
     * @param resource The flat file containing the transaction data.
     * @return Job The batch job to execute
     */
    @Bean
    public Job importCSVTransactionJob() {
        return jobBuilder.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .listener(csvlistener())
                .flow(importCSVTransactionStep())
                .end()
                .build();
    }

    /**
     * Configures the import step of the batch job.
     * 
     * @return
     */
    @Bean
    public Step importCSVTransactionStep() {
        return stepBuilder.get(STEP_NAME)
                .<Transaction, Transaction> chunk(BATCH_SIZE)
                .reader(csvReader())
                .processor(csvProcessor())
                .writer(csvTransactionWriter())
                .build();
    }
    
    
    
    public enum ColumnName {
    	REFERENCE("Reference"), 
    	ACCOUNTNUMBER("Account number"), 
    	DESCRIPTION("Description"), 
    	STARTBALANCE("Start Balance"), 
    	MUTATION("Mutation"), 
    	ENDBALANCE("End Balance"); 
    	
    	private String columnName;
    	
    	private ColumnName(String columnName) {
			this.columnName = columnName;
		}
    	
    	public String getColumnName() {
			return columnName;
		}
    }
}
