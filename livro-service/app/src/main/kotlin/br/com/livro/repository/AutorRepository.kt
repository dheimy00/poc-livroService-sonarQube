package br.com.livro.repository


import br.com.livro.model.Autor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AutorRepository: JpaRepository<Autor, Long> {

    fun findByEmail(email:String) : Optional<Autor>
    fun findByNome( nome:String) : List<Autor>
    override fun findById(id: Long): Optional<Autor>
}