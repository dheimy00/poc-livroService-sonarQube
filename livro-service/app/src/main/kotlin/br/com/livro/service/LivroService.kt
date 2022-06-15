package br.com.livro.service

import br.com.livro.dto.LivroDto
import java.util.*

interface LivroService {

    fun findAll(): List<LivroDto>

    fun save(livroDto: LivroDto): LivroDto

    fun update(id: Long, livroDto: LivroDto): LivroDto

    fun findById(id: Long): LivroDto

    fun deleteById(id: Long)

}