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
public class SecController {
    
    @GetMapping("/auth")
    public String authenticatedAPI(){
        return "Hi, you are authenticated";
    }

    @GetMapping("/authenticate") public List<String> authenticate() {
        System.out.print("hi");
        UserDetails userDetails = (LibUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.print(userDetails.getAuthorities());
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

}
