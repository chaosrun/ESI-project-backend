package esi.project.ils.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserController {

  @GetMapping("/authenticate")
    public List<String> authenticate() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = new ArrayList<>();

        if (principal instanceof LibUserDetails) {
            UserDetails details = (LibUserDetails) principal;
            for (GrantedAuthority authority: details.getAuthorities())
                roles.add(authority.getAuthority());
        }

        return roles;
    }
 
    @GetMapping("/auth")
    public String authenticatedAPI(){
        return "Hi, you are authenticated";
    }

}
