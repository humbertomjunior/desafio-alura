package br.com.alura.AluraFake.security;

import br.com.alura.AluraFake.user.Role;

public record RegisterDTO(String name, String login, String password, Role role) {
}