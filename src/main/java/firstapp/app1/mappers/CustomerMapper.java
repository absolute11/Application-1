package firstapp.app1.mappers;

import firstapp.app1.dto.CustomerDTO;
import firstapp.app1.models.Customer;
import firstapp.app1.models.Role;

import java.util.stream.Collectors;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setGithubId(customer.getGithubId());
        customerDTO.setRoles(customer.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return customerDTO;
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setUsername(customerDTO.getUsername());
        customer.setEmail(customerDTO.getEmail());
        customer.setGithubId(customerDTO.getGithubId());
        return customer;
    }
}
