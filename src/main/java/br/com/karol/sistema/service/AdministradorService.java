package br.com.karol.sistema.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.karol.sistema.domain.Administrador;
import br.com.karol.sistema.dto.administrador.CriarAdministradorDTO;
import br.com.karol.sistema.dto.administrador.DadosAdministradorDTO;
import br.com.karol.sistema.dto.administrador.AtualizarAdministradorDTO;
import br.com.karol.sistema.mapper.AdministradorMapper;
import br.com.karol.sistema.repository.AdministradorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdministradorService {

    private AdministradorRepository repository;
    private AdministradorMapper mapper;

    public AdministradorService(AdministradorRepository repository, AdministradorMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }


    public DadosAdministradorDTO salvar(CriarAdministradorDTO dto) {
        Administrador admin = mapper.toAdministrador(dto);
        admin = repository.save(admin);
        System.out.println("Administrador salvo com sucesso");

        return mapper.toDadosAdministrador(admin);
    }

    public void excluir(Long administradorID) {
        repository.deleteById(administradorID);
        System.out.println("Administrador excluido com sucesso!");
    }

    public DadosAdministradorDTO buscarAdministradorPorId(Long id) {
        Administrador admin = this.getAdminById(id);
        
        return mapper.toDadosAdministrador(admin);
    }

    public List<DadosAdministradorDTO> listar() {
        return mapper.toListDadosAdministrador(repository.findAll());
    }

    
    public DadosAdministradorDTO editarAdministrador(Long adminId, AtualizarAdministradorDTO dadosParaUpdate) {
        Administrador alvo = this.getAdminById(adminId);
        alvo.update(dadosParaUpdate.getUsuario());
        System.out.println("Administrador: " + alvo);

        return mapper.toDadosAdministrador(alvo);
    }

    private Administrador getAdminById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Administrador não encontrado"));
    }
}