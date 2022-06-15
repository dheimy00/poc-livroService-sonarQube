package br.com.livro.service.implementations

import br.com.livro.dto.AutorDto
import br.com.livro.exception.ResourceAlreadyExistException
import br.com.livro.mapper.AutorMapper
import br.com.livro.exception.ResourceNotFoundException
import br.com.livro.repository.AutorRepository
import br.com.livro.service.AutorService
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.stream.Collectors

@Service
class AutorServiceImpl(
        private val autorRepository: AutorRepository,
        private val mapper: AutorMapper
) : AutorService {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findAll(): List<AutorDto> {
        return autorRepository.findAll().map { mapper.convertEntityToDto(it) }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun save(autorDto: AutorDto): AutorDto {

        var autorNome = autorRepository.findByNome(autorDto.nome).stream()
                .map { autor -> mapper.convertEntityToDto(autor) }.collect(Collectors.toList())
        if (autorNome.isNotEmpty()) {
            throw ResourceAlreadyExistException("Autor já existe nome ${autorDto.nome}")
        }

        val email = autorRepository.findByEmail(autorDto.email)
        if (email.isPresent) {
            throw ResourceAlreadyExistException("Autor já existe email ${email.get().email}")
        }
        var autor = mapper.convertDtoToEntity(autorDto)
        autor = autorRepository.save(autor)
        return mapper.convertEntityToDto(autor)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun update(id: Long, autorDto: AutorDto): AutorDto {

        val autor = autorRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Autor não encontro id $id")
        }

        autor.nome = autorDto.nome
        autor.descricao = autorDto.descricao
        autor.email = autorDto.email
        return mapper.convertEntityToDto(autorRepository.save(autor))

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findById(id: Long): AutorDto {
        val autor = autorRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Autor não encontro id $id")
        }
        return mapper.convertEntityToDto(autor)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findByNome(nome: String): List<AutorDto> {
        var autor = autorRepository.findByNome(nome).stream()
                .map { autorNome -> mapper.convertEntityToDto(autorNome) }.collect(Collectors.toList())
        if (autor.isEmpty()) {
            throw ResourceNotFoundException("Autor não encontro nome $nome")
        }
        return autor;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun deleteById(id: Long) {
        val autor = autorRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Autor não encontro id $id")
        }
        autorRepository.delete(autor)
        return
    }


}