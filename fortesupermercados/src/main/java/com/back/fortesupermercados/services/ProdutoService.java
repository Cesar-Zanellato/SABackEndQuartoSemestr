package com.back.fortesupermercados.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.fortesupermercados.dtos.produtos.ProdutoEntrada;
import com.back.fortesupermercados.dtos.produtos.ProdutoSaida;
import com.back.fortesupermercados.entities.Produto;
import com.back.fortesupermercados.repositories.ProdutoRepository;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository repository;

    @Transactional
    public ProdutoSaida create(ProdutoEntrada entrada){
        Produto produto = convertEntradaParaProduto(entrada);
        produto = repository.save(produto);

        return convertProdutoParaSaida(produto);
    }

    public List<ProdutoSaida> list(Pageable page, Produto produtoExemplo){

        ExampleMatcher matcher = ExampleMatcher
                                        .matching()
                                        .withIgnoreCase()
                                        .withStringMatcher(StringMatcher.CONTAINING);

        Example<Produto> exemplo = Example.of(produtoExemplo, matcher);

        return repository
        .findAll(exemplo, page)
        .stream()
        .map(produto -> convertProdutoParaSaida(produto))
        .toList();
    }

    public ProdutoSaida read(Long id){
        Produto produto = repository.findById(id).orElse(null);
        return convertProdutoParaSaida(produto);
    }

    @Transactional
    public ProdutoSaida update(Long id, ProdutoEntrada entrada){
        if(repository.existsById(id)){
            Produto produto = convertEntradaParaProduto(entrada);
            produto.setCodigoInterno(id);
            produto = repository.save(produto);
            return convertProdutoParaSaida(produto);
        }else{
            return null;
        }
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    private ProdutoSaida convertProdutoParaSaida(Produto produto){
        if(produto == null){
            return null;
        }
        ProdutoSaida saida = new ProdutoSaida(
            produto.getCodigoProduto(), 
            produto.getNome(),
            produto.getValorVenda(), 
            produto.getPromocao(), 
            produto.getEstoque(), 
            produto.getImagem(), 
            produto.getGramagem()
        );

        return saida;
    }

    private Produto convertEntradaParaProduto(ProdutoEntrada entrada){
        Produto produto = new Produto();
        produto.setCodigoInterno(entrada.codigoInterno());
        produto.setCodigoProduto(entrada.codigoProduto());
        produto.setNome(entrada.nome());
        produto.setValorVenda(entrada.valorVenda());
        produto.setValorCompra(entrada.valorCompra());
        produto.setPercentualLucro(entrada.percentualLucro());
        produto.setEstoque(entrada.estoque());
        produto.setImagem(entrada.imagem());
        produto.setGramagem(entrada.gramagem());

        return produto;
    }
}
