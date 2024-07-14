package firstapp.app1.services;

import firstapp.app1.dto.CustomerDTO;
import firstapp.app1.mappers.CustomerMapper;
import firstapp.app1.models.Customer;
import firstapp.app1.models.Role;
import firstapp.app1.repositories.CustomerRepository;
import firstapp.app1.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.findByGithubId(customerDTO.getGithubId()).isPresent() ||
                customerRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Customer with the same GitHub ID or email already exists.");
        }

        Customer customer = CustomerMapper.toEntity(customerDTO);
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        customer.setRoles(Set.of(userRole));

        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.toDTO(savedCustomer);
    }
    public List<CustomerDTO> getAllCustomers(){
        return customerRepository.findAll().stream().map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }


    public Optional<CustomerDTO> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(CustomerMapper::toDTO);
    }

    public CustomerDTO updateCustomer(String email, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.setUsername(customerDTO.getUsername());
        existingCustomer.setEmail(customerDTO.getEmail());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toDTO(updatedCustomer);
    }

    public void deleteCustomer(String email) {
        customerRepository.deleteCustomerByEmail(email);
    }

}
