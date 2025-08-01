package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.AuthorityDao;
import kg.attractor.job_search_java25.dao.UserDao;
import kg.attractor.job_search_java25.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;
    private final AuthorityDao authorityDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с email '" + email + "' не найден.");
        }

        User user = userOptional.get();

        List<String> authorityNames = authorityDao.getAuthoritiesByUserId(user.getId());

        Collection<GrantedAuthority> authorities = authorityNames.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
