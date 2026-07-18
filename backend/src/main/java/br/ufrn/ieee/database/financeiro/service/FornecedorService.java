package br.ufrn.ieee.database.financeiro.service;

import br.ufrn.ieee.database.financeiro.dto.FornecedorRequestDTO;
import br.ufrn.ieee.database.financeiro.dto.FornecedorResponseDTO;
import br.ufrn.ieee.database.financeiro.model.Fornecedor;
import br.ufrn.ieee.database.financeiro.repository.FornecedorRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional(readOnly = true)
    public Page<FornecedorResponseDTO> listarTodos(Pageable pageable) {
        return fornecedorRepository.findAll(pageable).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public FornecedorResponseDTO buscarPorId(Long id) {
        Fornecedor fornecedor = buscarEntidadeOuFalhar(id);
        return toResponseDTO(fornecedor);
    }

    @Transactional
    public FornecedorResponseDTO criar(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.getNome());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setLinkWebsite(dto.getLinkWebsite());

        return toResponseDTO(fornecedorRepository.save(fornecedor));
    }

    @Transactional
    public FornecedorResponseDTO atualizar(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = buscarEntidadeOuFalhar(id);

        if (dto.getNome() != null)
            fornecedor.setNome(dto.getNome());
        if (dto.getTelefone() != null)
            fornecedor.setTelefone(dto.getTelefone());
        if (dto.getLinkWebsite() != null)
            fornecedor.setLinkWebsite(dto.getLinkWebsite());

        return toResponseDTO(fornecedorRepository.save(fornecedor));
    }

    @Transactional
    public void deletar(Long id) {
        Fornecedor fornecedor = buscarEntidadeOuFalhar(id);
        fornecedorRepository.delete(fornecedor);
    }

    private Fornecedor buscarEntidadeOuFalhar(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Fornecedor não encontrado com ID: " + id));
    }

    private FornecedorResponseDTO toResponseDTO(Fornecedor fornecedor) {
        FornecedorResponseDTO dto = new FornecedorResponseDTO();
        dto.setId(fornecedor.getId());
        dto.setNome(fornecedor.getNome());
        dto.setTelefone(fornecedor.getTelefone());
        dto.setLinkWebsite(fornecedor.getLinkWebsite());
        return dto;
    }
}