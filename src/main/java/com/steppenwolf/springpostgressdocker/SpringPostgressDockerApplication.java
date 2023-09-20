package com.steppenwolf.springpostgressdocker;

import com.steppenwolf.springpostgressdocker.customer.Customer;
import com.steppenwolf.springpostgressdocker.customer.CustomerRepository;
import com.steppenwolf.springpostgressdocker.customer.CustomerRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customer")
public class SpringPostgressDockerApplication {

    private final CustomerRepository customerRepository;

    public SpringPostgressDockerApplication(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringPostgressDockerApplication.class, args);
	}

	@GetMapping
    public List<Customer> getCustomers(){
	    return customerRepository.findAll();
    }

    @PostMapping
    public void addCustomer(@RequestBody CustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setAge(request.getAge());

        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){

        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody CustomerRequest request){

       Optional<Customer> customerOptional = customerRepository.findById(id);
       if (customerOptional.isPresent()) {
           Customer customer = customerOptional.get();

           customer.setAge(request.getAge());
           customer.setName(request.getName());
           customer.setEmail(request.getEmail());

           customerRepository.save(customer);
       }

    }

}
