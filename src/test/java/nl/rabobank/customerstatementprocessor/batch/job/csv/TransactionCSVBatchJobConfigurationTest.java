package nl.rabobank.customerstatementprocessor.batch.job.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import nl.rabobank.customerstatementprocessor.TransactionService;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransactionCSVBatchJobConfigurationTest {

	private File reportFile = new File("./log/test/csv_import_report.log");
	
	@Autowired
	JobLauncherTestUtils csvJobLauncherTestUtils;

	@Autowired
	TransactionService transactionService;
	
	@Before
	public void setUp() throws Exception {
		clearDatabase();
	}
	
	@After
	public void breakDown() throws Exception {
		clearDatabase();
	}
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void launchCSVJob() throws Exception {
		JobExecution jobExecution = csvJobLauncherTestUtils.launchJob();
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		shouldReportDoubleReferences();
	}
	private void shouldReportDoubleReferences() throws IOException {
		String report = FileUtils.readFileToString(reportFile);
		assertTrue(report.contains("Not unique reference error detected for transaction: 112806, for account: NL90ABNA0585647886, with description: Clothes from Peter de Vries."));
		assertTrue(report.contains("Not unique reference error detected for transaction: 112806, for account: NL91RABO0315273637, with description: Tickets for Erik Dekker. "));
	}
	
	private void clearDatabase() {
		transactionService.deleteAllTransactions();
	}
}


