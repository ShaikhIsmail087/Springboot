package in.batch.congfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import in.batch.entity.Customer;
import in.batch.repo.CustomerRepository;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Bean
	FlatFileItemReader<Customer> customerReader() {

		FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
		itemReader.setName("csvfile-reader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(LineMapper());
		return itemReader;
	}

	private LineMapper<Customer> LineMapper() {

		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokennizer = new DelimitedLineTokenizer();
		lineTokennizer.setDelimiter(",");
		lineTokennizer.setStrict(false);
		lineTokennizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

		BeanWrapperFieldSetMapper<Customer> filedSetmapper = new BeanWrapperFieldSetMapper<>();
		filedSetmapper.setTargetType(Customer.class);
		lineMapper.setLineTokenizer(lineTokennizer);
		lineMapper.setFieldSetMapper(filedSetmapper);
		return lineMapper;
	}

	@Bean
	CustomerProcessor customerProcessor() {
		return new CustomerProcessor();
	}

	@Bean
	RepositoryItemWriter<Customer> customerWriter() {

		RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
		writer.setRepository(customerRepository);
		writer.setMethodName("save");
		return writer;

	}

    @Bean
    Step step() {
		return stepBuilderFactory.get("step-1").<Customer,Customer> chunk(10)
				                 .reader(customerReader())
				                 .processor(customerProcessor())
				                 .writer(customerWriter())
				                 .taskExecutor(TaskExecutor())
				                 .build();
		
	}
    @Bean
    Job job() {
    	return jobBuilderFactory.get(("customer-import"))
    			                .flow(step())
    			                .end()
    			                .build();
    }
    
    
    
    
    
    @Bean
	 TaskExecutor TaskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor= new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(10);
		return taskExecutor;
	}
	
	
	
	
	
	

}
