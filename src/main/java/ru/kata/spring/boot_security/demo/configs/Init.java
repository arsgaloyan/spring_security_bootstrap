package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final RoleService roleService;

    private final UserService userService;

    public Init(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @PostConstruct
    public void saveUsers() {
        roleService.save(new Role("ROLE_USER"));
        roleService.save(new Role("ROLE_ADMIN"));

        Set<Role> userRole = new HashSet<>();
        userRole.add(roleService.findById(1L));
        userService.save(new User("User"
                , "User"
                , (byte) 20
                , "user@mail.ru"
                , "100"
                , userRole));

        Set<Role> adminRole = new HashSet<>();
        adminRole.add(roleService.findById(2L));
        userService.save(new User("Admin"
                , "Admin"
                , (byte) 25
                , "admin@mail.ru"
                , "100"
                , adminRole));
    }
}
