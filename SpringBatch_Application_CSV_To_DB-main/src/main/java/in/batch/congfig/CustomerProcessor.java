package in.batch.congfig;

import org.springframework.batch.item.ItemProcessor;

import in.batch.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		return item;
	}

}
