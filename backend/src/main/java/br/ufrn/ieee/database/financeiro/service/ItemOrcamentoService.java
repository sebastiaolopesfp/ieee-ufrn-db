package br.ufrn.ieee.database.financeiro.service;

import br.ufrn.ieee.database.evento.model.Evento;
import br.ufrn.ieee.database.evento.repository.EventoRepository;
import br.ufrn.ieee.database.financeiro.dto.ItemOrcamentoRequestDTO;
import br.ufrn.ieee.database.financeiro.dto.ItemOrcamentoResponseDTO;
import br.ufrn.ieee.database.financeiro.model.Fornecedor;
import br.ufrn.ieee.database.financeiro.model.ItemOrcamento;
import br.ufrn.ieee.database.financeiro.repository.FornecedorRepository;
import br.ufrn.ieee.database.financeiro.repository.ItemOrcamentoRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ItemOrcamentoService {

    private final ItemOrcamentoRepository itemRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EventoRepository eventoRepository;

    public ItemOrcamentoService(ItemOrcamentoRepository itemRepository,
            FornecedorRepository fornecedorRepository,
            EventoRepository eventoRepository) {
        this.itemRepository = itemRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.eventoRepository = eventoRepository;
    }

    @Transactional(readOnly = true)
    public Page<ItemOrcamentoResponseDTO> listarTodos(Pageable pageable) {
        return itemRepository.findAll(pageable).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ItemOrcamentoResponseDTO buscarPorId(Long id) {
        ItemOrcamento item = buscarEntidadeOuFalhar(id);
        return toResponseDTO(item);
    }

    @Transactional
    public ItemOrcamentoResponseDTO criar(ItemOrcamentoRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Fornecedor não encontrado."));

        Evento evento = eventoRepository.findById(dto.getEventoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado."));

        ItemOrcamento item = new ItemOrcamento();
        item.setFornecedor(fornecedor);
        item.setEvento(evento);
        item.setDescricaoProduto(dto.getDescricaoProduto());
        item.setCategoriaFinanceira(dto.getCategoriaFinanceira());
        item.setQuantidade(dto.getQuantidade());
        item.setCustoUnitario(dto.getCustoUnitario());
        item.setNotaFiscalPath(dto.getNotaFiscalPath());
        item.setLinkItem(dto.getLinkItem());

        return toResponseDTO(itemRepository.save(item));
    }

    @Transactional
    public ItemOrcamentoResponseDTO atualizar(Long id, ItemOrcamentoRequestDTO dto) {
        ItemOrcamento item = buscarEntidadeOuFalhar(id);

        if (dto.getDescricaoProduto() != null)
            item.setDescricaoProduto(dto.getDescricaoProduto());
        if (dto.getCategoriaFinanceira() != null)
            item.setCategoriaFinanceira(dto.getCategoriaFinanceira());
        if (dto.getQuantidade() != null)
            item.setQuantidade(dto.getQuantidade());
        if (dto.getCustoUnitario() != null)
            item.setCustoUnitario(dto.getCustoUnitario());
        if (dto.getNotaFiscalPath() != null)
            item.setNotaFiscalPath(dto.getNotaFiscalPath());
        if (dto.getLinkItem() != null)
            item.setLinkItem(dto.getLinkItem());

        if (dto.getFornecedorId() != null && !dto.getFornecedorId().equals(item.getFornecedor().getId())) {
            Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Novo Fornecedor não encontrado."));
            item.setFornecedor(fornecedor);
        }

        if (dto.getEventoId() != null && !dto.getEventoId().equals(item.getEvento().getId())) {
            Evento evento = eventoRepository.findById(dto.getEventoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Novo Evento não encontrado."));
            item.setEvento(evento);
        }

        return toResponseDTO(itemRepository.save(item));
    }

    @Transactional
    public void deletar(Long id) {
        ItemOrcamento item = buscarEntidadeOuFalhar(id);
        itemRepository.delete(item);
    }

    private ItemOrcamento buscarEntidadeOuFalhar(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(
                        () -> new EntidadeNaoEncontradaException("Item de Orçamento não encontrado com ID: " + id));
    }

    private ItemOrcamentoResponseDTO toResponseDTO(ItemOrcamento item) {
        ItemOrcamentoResponseDTO dto = new ItemOrcamentoResponseDTO();
        dto.setId(item.getId());
        dto.setFornecedorId(item.getFornecedor().getId());
        dto.setNomeFornecedor(item.getFornecedor().getNome());
        dto.setEventoId(item.getEvento().getId());
        dto.setDescricaoProduto(item.getDescricaoProduto());
        dto.setCategoriaFinanceira(item.getCategoriaFinanceira());
        dto.setQuantidade(item.getQuantidade());
        dto.setCustoUnitario(item.getCustoUnitario());

        if (item.getQuantidade() != null && item.getCustoUnitario() != null) {
            dto.setCustoTotal(item.getCustoUnitario().multiply(new BigDecimal(item.getQuantidade())));
        }

        dto.setNotaFiscalPath(item.getNotaFiscalPath());
        dto.setLinkItem(item.getLinkItem());
        return dto;
    }
}