package br.com.karol.sistema.service;

import br.com.karol.sistema.domain.Administrador;
import br.com.karol.sistema.repository.AdministradorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdministradorService {

    private final AdministradorRepository repository;

    @Autowired
    public AdministradorService(AdministradorRepository repository) {
        this.repository = repository;

    }

    public ResponseEntity<Administrador> salvar(Administrador administrador) {
        Administrador admin = repository.save(administrador);
        System.out.println("Administrador salvo com sucesso");
        return ResponseEntity.ok(admin);
    }

    public ResponseEntity<Administrador> excluir(Administrador administrador) {
        repository.delete(administrador);
        System.out.println("Administrador excluido com sucesso!");
        return ResponseEntity.ok(administrador);

    }


    public ResponseEntity<Administrador> buscarAdministradorPorId(Integer id) {
        Optional<Administrador> admin = repository.findById(id);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<List<Administrador>> listar() {
        Iterable<Administrador> adms = repository.findAll();


        return null;
    }

    public ResponseEntity<Administrador> editar(Administrador administrador) {
        Optional<Administrador> admin = repository.findById(administrador.getId());
        if (admin.isPresent()) {
            repository.save(administrador);
            System.out.println("Administrador: " + administrador);
            return ResponseEntity.ok(administrador);
        } else {
            System.out.println("Administrador não localizado: " + administrador);
            return ResponseEntity.notFound().build();
        }

    }


}
