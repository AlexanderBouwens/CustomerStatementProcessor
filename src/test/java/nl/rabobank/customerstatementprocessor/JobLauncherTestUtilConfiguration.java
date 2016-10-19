package nl.rabobank.customerstatementprocessor;

import nl.rabobank.customerstatementprocessor.batch.job.csv.CSVBatchJobConfiguration;
import nl.rabobank.customerstatementprocessor.batch.job.xml.XMLBatchJobConfiguration;

import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobLauncherTestUtilConfiguration {

	@Bean
    public JobLauncherTestUtils csvJobLauncherTestUtils() {
 
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier(CSVBatchJobConfiguration.JOB_NAME) Job job) {
                super.setJob(job);
            }
        };
    }
	
	@Bean
    public JobLauncherTestUtils xmlJobLauncherTestUtils() {
 
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier(XMLBatchJobConfiguration.JOB_NAME) Job job) {
                super.setJob(job);
            }
        };
    }
}
