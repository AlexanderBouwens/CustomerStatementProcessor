package nl.rabobank.customerstatementprocessor.batch.job.xml;

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
public class TransactionXMLBatchJobConfigurationTest {

	private File reportFile = new File("./log/test/xml_import_report.log");
	
	@Autowired
	JobLauncherTestUtils xmlJobLauncherTestUtils;

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
	public void launchXMLJob() throws Exception {
		JobExecution jobExecution = xmlJobLauncherTestUtils.launchJob();
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		shouldReportFaultyEndBalances();
	}
	
	private void shouldReportFaultyEndBalances() throws IOException {
		String report = FileUtils.readFileToString(reportFile);
		assertTrue(report.contains("End balance error detected in transaction: 154270, for account: NL56RABO0149876948, with description: Candy for Peter de Vries."));
		assertTrue(report.contains("End balance error detected in transaction: 140269, for account: NL43AEGO0773393871, with description: Tickets for Vincent Dekker. "));
	}

	private void clearDatabase() {
		transactionService.deleteAllTransactions();
	}
}


