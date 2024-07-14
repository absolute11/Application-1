package firstapp.app1.repositories;

import firstapp.app1.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByGithubId(String githubId);
    Optional<Customer> findByEmail(String email);
    void deleteCustomerByEmail(String email);
}
