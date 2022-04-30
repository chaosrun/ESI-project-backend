package esi.project.ils.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LibUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
       Optional<User> user = userRepository.findByEmail(email);
       user.orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
       return user.map(LibUserDetails::new).get();
    }
    
}
