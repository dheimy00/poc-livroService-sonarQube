package br.com.livro.service


import br.com.livro.dto.AutorDto
import java.util.*

interface AutorService {

    fun findAll(): List<AutorDto>

    fun save(autorDto: AutorDto): AutorDto

    fun update(id: Long,autorDto: AutorDto): AutorDto

    fun findById(id: Long): AutorDto

    fun findByNome(nome: String) : List<AutorDto>

    fun deleteById(id: Long)
}