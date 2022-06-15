package br.com.livro.service

import br.com.livro.dto.CategoriaDto
import java.util.*

interface CategoriaService {

    fun findAll(): List<CategoriaDto>

    fun save(categoriaDto: CategoriaDto): CategoriaDto

    fun update(id: Long, categoriaDto: CategoriaDto): CategoriaDto

    fun findById(id: Long):CategoriaDto

    fun findByNome(nome: String) : List<CategoriaDto>

    fun deleteById(id: Long)
}