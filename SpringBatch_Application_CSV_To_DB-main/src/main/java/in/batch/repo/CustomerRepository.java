package in.batch.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.batch.entity.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Serializable>{

}
