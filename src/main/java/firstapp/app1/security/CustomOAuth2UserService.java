package firstapp.app1.security;

import firstapp.app1.models.Customer;
import firstapp.app1.models.Role;
import firstapp.app1.repositories.CustomerRepository;
import firstapp.app1.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final RestTemplate restTemplate;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        logger.info("OAuth2User attributes: {}", oAuth2User.getAttributes());

        String githubId = oAuth2User.getAttribute("id").toString();
        String username = oAuth2User.getAttribute("login");
        String email = fetchEmail(userRequest.getAccessToken().getTokenValue());

        Optional<Customer> existingCustomer = customerRepository.findByGithubId(githubId);
        Customer customer;

        if (existingCustomer.isPresent()) {
            customer = existingCustomer.get();
        } else {
            customer = new Customer();
            customer.setGithubId(githubId);
            customer.setUsername(username);
            customer.setEmail(email);

            Role userRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            customer.setRoles(Set.of(userRole));

            customerRepository.save(customer);
        }

        Set<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new CustomOAuth2User(oAuth2User, authorities);
    }

    private String fetchEmail(String accessToken) {
        String email = null;
        try {
            String url = "https://api.github.com/user/emails";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity<>("", headers);

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});

            List<Map<String, Object>> emails = response.getBody();
            for (Map<String, Object> emailInfo : emails) {
                if ((boolean) emailInfo.get("primary")) {
                    email = (String) emailInfo.get("email");
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Failed to fetch email from GitHub API", e);
        }
        return email != null ? email : "default@example.com";
    }
}