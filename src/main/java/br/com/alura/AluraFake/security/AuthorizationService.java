package br.com.alura.AluraFake.security;

import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserDetailsByEmail(email).orElseThrow(() -> new NotFoundException("Usuário não encontrado no banco de dados"));
    }

}
