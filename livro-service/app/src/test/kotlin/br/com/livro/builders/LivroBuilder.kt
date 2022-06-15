package br.com.livro.builders

import br.com.livro.model.Livro
import java.util.*

class LivroBuilder {

    companion object {
        fun createLivro(): Livro {
            return Livro(
                1L,
                    "Teste",
                    "resumo",
                    "teste_isbn",
                    "teste_sumario",
                    100,
                    Date(),
                    CategoriaBuilder.createCategoria(),
                    AutorBuilder.createAutor()
            )
        }

        fun validLivro(): Livro {
            return Livro(
                1L,
                    "Teste",
                    "resumo",
                    "teste_isbn",
                    "teste_sumario",
                    100,
                    Date(),
                    CategoriaBuilder.createCategoria(),
                    AutorBuilder.createAutor()
            )
        }
    }

}