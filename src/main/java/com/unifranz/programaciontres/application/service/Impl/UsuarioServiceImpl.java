package com.unifranz.programaciontres.application.service.Impl;

import com.unifranz.programaciontres.application.dto.UsuarioDto;
import com.unifranz.programaciontres.application.service.UsuarioService;
import com.unifranz.programaciontres.domain.Usuario;
import com.unifranz.programaciontres.domain.UsuarioAdmin;
import com.unifranz.programaciontres.infrastructure.persistence.UsuarioRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto guardar (UsuarioDto usuarioDto){
        Usuario usuario;
        if (usuarioDto.getRol() != null && usuarioDto.getRol().equalsIgnoreCase("admin")) {
            usuario = new UsuarioAdmin();
        } else {
            usuario = new Usuario();
        }
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        Usuario guardar =   usuarioRepository.save(usuario);
        return new UsuarioDto(guardar);
    }

    @Override
    public List<UsuarioDto> listar(){
        return usuarioRepository.findAll()
                .stream()
                .map (UsuarioDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> listarActivos(){
        return usuarioRepository.listarActivos();
    }

    @Override
    public void eliminarLogico(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuario.setEliminado(true);
        usuarioRepository.save(usuario);
    }
}
