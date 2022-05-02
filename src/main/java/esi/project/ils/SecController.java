package esi.project.ils;

import java.util.LinkedHashMap;
import java.util.Map;

import esi.project.ils.users.LibUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SecController {

  @GetMapping("/auth")
  @ResponseBody
    public Map<String, Object> authenticate(@AuthenticationPrincipal LibUserDetails user) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("id", user.getId());
        result.put("email", user.getEmail());
        result.put("name", user.getName());
        result.put("role", user.getAuthorities().get(0).getAuthority());

        return result;
    }

}
