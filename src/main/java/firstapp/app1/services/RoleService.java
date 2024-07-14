package firstapp.app1.services;

import firstapp.app1.models.Role;
import firstapp.app1.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleByName(String role) {
        return roleRepository.findByName(role);
    }

    public Role createRole(Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new RuntimeException("Role with the same name already exists.");
        }
        return roleRepository.save(role);
    }

    public Role updateRole(String name,Role role) {
        Optional<Role> existingRole = roleRepository.findByName(name);
        if (existingRole.isPresent()) {
            Role realRole = existingRole.get();
            realRole.setName(existingRole.get().getName());
            return roleRepository.save(realRole);
        } else {
            throw new RuntimeException("Role not found");
        }

    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
