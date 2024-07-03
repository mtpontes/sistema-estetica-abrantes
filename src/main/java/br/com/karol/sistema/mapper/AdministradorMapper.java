package br.com.karol.sistema.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Administrador;
import br.com.karol.sistema.dto.administrador.CriarAdministradorDTO;
import br.com.karol.sistema.dto.administrador.DadosAdministradorDTO;

@Component
public class AdministradorMapper {

    public DadosAdministradorDTO toDadosAdministrador(Administrador admin) {
        return new DadosAdministradorDTO(admin);
    }
    public List<DadosAdministradorDTO> toListDadosAdministrador(List<Administrador> admins) {
        return admins.stream()
            .map(this::toDadosAdministrador)
            .toList();
    }
    public Administrador toAdministrador(CriarAdministradorDTO dto) {
        return new Administrador(dto.getUsuario(), dto.getSenha());
    }
}