package firstapp.app1.controllers;

import firstapp.app1.dto.CustomerDTO;
import firstapp.app1.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class UserController {
    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Optional<CustomerDTO> getCustomerByEmail(@PathVariable String email){
        return customerService.getCustomerByEmail(email);
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.createCustomer(customerDTO);
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable String email,@RequestBody CustomerDTO customerDTO){
        return customerService.updateCustomer(email, customerDTO);
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello !";
    }

}
