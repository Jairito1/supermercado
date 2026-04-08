package com.pollos.supermercado.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pollos.supermercado.dto.request.LoginRequestDTO;
import com.pollos.supermercado.dto.request.RegistroRequestDTO;
import com.pollos.supermercado.dto.response.TokenResponseDTO;
import com.pollos.supermercado.entity.Usuario;
import com.pollos.supermercado.exception.DuplicateResourceException;
import com.pollos.supermercado.repository.UsuarioRepository;
import com.pollos.supermercado.security.JwtService;

@Service
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService,
                       @Lazy PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponseDTO registro(RegistroRequestDTO request) {
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("El nombre de usuario ya está en uso");
        }

        Usuario usuario = Usuario.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .rol("ROLE_USER")
                .build();

        usuarioRepository.save(usuario);
        String token = jwtService.generarToken(usuario);
        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtService.generarToken(usuario);
        return new TokenResponseDTO(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
