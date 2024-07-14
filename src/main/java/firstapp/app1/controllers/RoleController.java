package firstapp.app1.controllers;

import firstapp.app1.models.Role;
import firstapp.app1.repositories.RoleRepository;
import firstapp.app1.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
    @GetMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<Role> getRoleByName(@PathVariable String name){
        return roleService.getRoleByName(name);
    }
    @PutMapping("/{name}")
    public Role updateRole(@PathVariable String name, @RequestBody Role role){
        return roleService.updateRole(name,role);
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }


}
