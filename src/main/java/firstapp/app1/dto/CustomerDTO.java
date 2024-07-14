package firstapp.app1.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Data
public class CustomerDTO {
    private String username;
    private String email;
    private String githubId;
    private Set<String> roles;
}
