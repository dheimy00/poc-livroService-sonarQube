package br.com.livro.repository

import br.com.livro.model.Livro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LivroRepository: JpaRepository<Livro,Long> {

    fun findByTitulo(titulo: String?): Optional<Livro>
    fun findByIsbn(isbn: String?): Optional<Livro>
    override fun findById(id: Long): Optional<Livro>
}