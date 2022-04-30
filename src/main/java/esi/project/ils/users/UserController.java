package esi.project.ils.users;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping("/auth")
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

    @GetMapping("/me")
    @ResponseBody
    public Map<String, Object> meAPI(@AuthenticationPrincipal User user){
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("email", user.getEmail());
        result.put("name", user.getName());
    
        return result;
    }

}
