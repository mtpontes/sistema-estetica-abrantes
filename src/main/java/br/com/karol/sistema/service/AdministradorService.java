package br.com.karol.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.karol.sistema.domain.Administrador;
import br.com.karol.sistema.dto.administrador.DadosAdministradorDTO;
import br.com.karol.sistema.repository.AdministradorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository repository;


    public DadosAdministradorDTO salvar(Administrador administrador) {
        Administrador admin = repository.save(administrador);
        System.out.println("Administrador salvo com sucesso");

        return new DadosAdministradorDTO(admin);
    }

    // não precisa retornar
    public void excluir(Integer administradorID) {
        repository.deleteById(administradorID);
        System.out.println("Administrador excluido com sucesso!");
    }

    public DadosAdministradorDTO buscarAdministradorPorId(Integer id) {
        Administrador admin = repository.findById(id)
            .orElseThrow(EntityNotFoundException::new);

        return new DadosAdministradorDTO(admin);
    }

    public List<DadosAdministradorDTO> listar() {
        return repository.findAll().stream()
            .map(DadosAdministradorDTO::new)
            .toList();
    }

    // implementar lógica de update na entidade Administrador
    public DadosAdministradorDTO editar(Administrador dadosParaUpdate) {
        Administrador alvo = repository.findById(dadosParaUpdate.getId())
            .orElseThrow((() -> new EntityNotFoundException("Administrador não encontrado")));
        
        alvo.update(dadosParaUpdate.getUsuario());
        System.out.println("Administrador: " + alvo);

        return new DadosAdministradorDTO(alvo);
    }
}