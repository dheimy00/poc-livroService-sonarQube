package br.com.livro.service.implementations

import br.com.livro.dto.CategoriaDto
import br.com.livro.exception.ResourceAlreadyExistException
import br.com.livro.mapper.CategoriaMapper
import br.com.livro.exception.ResourceNotFoundException
import br.com.livro.repository.CategoriaRepository
import br.com.livro.service.CategoriaService
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class CategoriaServiceImpl(private val categoriaRepository: CategoriaRepository,
                           private val mapper: CategoriaMapper,) : CategoriaService {


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findAll(): List<CategoriaDto> {
        return categoriaRepository.findAll().map { mapper.convertEntityToDto(it) }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun save(categoriaDto: CategoriaDto): CategoriaDto {

        var categoriaNome = categoriaRepository.findByNome(categoriaDto.nome).stream()
            .map{ categoria -> mapper.convertEntityToDto(categoria) }.collect(Collectors.toList())
        if ( categoriaNome.isNotEmpty()){
            throw ResourceAlreadyExistException("Categoria já existe nome ${categoriaDto.nome}")
        }

        var categoria = mapper.convertDtoToEntity(categoriaDto)
        categoria = categoriaRepository.save(categoria)
        return mapper.convertEntityToDto(categoria)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun update(id: Long, categoriaDto: CategoriaDto): CategoriaDto {

        var categoria = categoriaRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Categoria não encontro id $id")
        }
            categoria.nome = categoriaDto.nome
            return mapper.convertEntityToDto(categoriaRepository.save(categoria))

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findById(id: Long): CategoriaDto {

        var categoria = categoriaRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Categoria não encontro id $id")
        }

            return mapper.convertEntityToDto(categoria)

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findByNome(nome: String): List<CategoriaDto> {
        var categoriaNome = categoriaRepository.findByNome(nome).stream()
            .map{ categoria -> mapper.convertEntityToDto(categoria) }.collect(Collectors.toList())
        if ( categoriaNome.isEmpty()){
            throw ResourceNotFoundException("Categoria não encontro nome $nome")
        }
        return  categoriaNome;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun deleteById(id: Long) {
        var categoria = categoriaRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Categoria não encontro id $id")
        }
            categoriaRepository.delete(categoria)
            return
    }

}