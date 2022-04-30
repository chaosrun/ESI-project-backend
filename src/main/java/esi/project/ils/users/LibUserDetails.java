package esi.project.ils.users;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class LibUserDetails extends User implements UserDetails {
    
        private User user;
    
        public LibUserDetails(final User user) {
            super(user);
        }
    
        @Override
        public List<? extends GrantedAuthority> getAuthorities() {
            return Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
        }
    
        @Override
        public String getPassword() {
            return super.getPassword();
        }
    
        @Override
        public String getUsername() {
            return super.getEmail();
        }
    
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }
    
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }
    
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
    
        @Override
        public boolean isEnabled() {
            return true;
        }

}
