package br.com.livro.service.implementations

import br.com.livro.dto.LivroDto
import br.com.livro.exception.ResourceAlreadyExistException
import br.com.livro.mapper.LivroMapper
import br.com.livro.exception.ResourceNotFoundException
import br.com.livro.repository.AutorRepository
import br.com.livro.repository.LivroRepository
import br.com.livro.service.LivroService
import br.com.livro.repository.CategoriaRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class LivroServiceImpl(
        private val livroRepository: LivroRepository,
        private val categoriaRepository: CategoriaRepository,
        private val autorRepository: AutorRepository,
        private val mapper: LivroMapper,
) : LivroService {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findAll(): List<LivroDto> {
        return livroRepository.findAll().map { mapper.convertEntityToDto(it) }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun save(livroDto: LivroDto): LivroDto {

        var titulo = livroRepository.findByTitulo(livroDto.titulo)
        if (titulo.isPresent) {
            throw ResourceAlreadyExistException("Livro já existe título ${titulo.get().titulo}")
        }

        var isbn = livroRepository.findByIsbn(livroDto.isbn)
        if (isbn.isPresent) {
            throw ResourceAlreadyExistException("Livro já existe isbn ${isbn.get().isbn}")
        }

        var categoria = categoriaRepository.findById(livroDto.id_categoria).orElseThrow {
            throw ResourceNotFoundException("Categoria não encontro id ${livroDto.id_categoria}")
        }

        var autor = autorRepository.findById(livroDto.id_autor).orElseThrow {
            throw ResourceNotFoundException("Autor não encontro id ${livroDto.id_autor}")
        }

        var livro = mapper.convertDtoToEntity(livroDto)
        livro.categoria = categoria
        livro.autor = autor
        livro = livroRepository.save(livro)

        return mapper.convertEntityToDto(livro)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun update(id: Long, livroDto: LivroDto): LivroDto {

        var livro = livroRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Livro não encontro id $id")
        }
        livro.titulo = livroDto.titulo
        livro.resumo = livroDto.resumo
        livro.isbn = livroDto.isbn
        livro.sumario = livroDto.sumario
        livro.pagina = livroDto.pagina
        return mapper.convertEntityToDto(livroRepository.save(livro))
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findById(id: Long): LivroDto {
        var livro = livroRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Livro não encontro id $id")
        }
        return mapper.convertEntityToDto(livro)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun deleteById(id: Long) {
        var livro = livroRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Livro não encontro id $id")
        }
            livroRepository.delete(livro)
    }

}