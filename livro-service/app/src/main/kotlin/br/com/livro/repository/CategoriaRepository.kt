package br.com.livro.repository

import br.com.livro.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoriaRepository:JpaRepository<Categoria,Long>{

    fun findByNome(nome: String): List<Categoria>
    override fun findById(id: Long): Optional<Categoria>
}