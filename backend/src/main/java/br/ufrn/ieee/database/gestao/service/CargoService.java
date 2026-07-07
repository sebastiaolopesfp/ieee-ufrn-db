package br.ufrn.ieee.database.gestao.service;

import br.ufrn.ieee.database.gestao.dto.CargoRequestDTO;
import br.ufrn.ieee.database.gestao.dto.CargoResponseDTO;
import br.ufrn.ieee.database.gestao.model.Cargo;
import br.ufrn.ieee.database.gestao.repository.CargoRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<CargoResponseDTO> listarTodos() {
        return cargoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public CargoResponseDTO buscarPorId(Long id) {
        Cargo cargo = cargoRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Cargo não encontrado."));
        return toResponseDTO(cargo);
    }

    public CargoResponseDTO criar(CargoRequestDTO dto) {
        Cargo cargo = new Cargo();
        cargo.setNome(dto.getNome());
        return toResponseDTO(cargoRepository.save(cargo));
    }

    public CargoResponseDTO atualizar(Long id, CargoRequestDTO dto) {
        Cargo cargo = cargoRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Cargo não encontrado."));
        if (dto.getNome() != null) cargo.setNome(dto.getNome());
        return toResponseDTO(cargoRepository.save(cargo));
    }

    public void deletar(Long id) {
        Cargo cargo = cargoRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Cargo não encontrado."));
        cargoRepository.delete(cargo);
    }

    private CargoResponseDTO toResponseDTO(Cargo cargo) {
        CargoResponseDTO dto = new CargoResponseDTO();
        dto.setId(cargo.getId());
        dto.setNome(cargo.getNome());
        return dto;
    }
}