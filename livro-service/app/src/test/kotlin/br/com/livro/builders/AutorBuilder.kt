package br.com.livro.builders

import br.com.livro.model.Autor
import java.util.*


class AutorBuilder {

    companion object {

        fun createAutor(): Autor {
            return Autor(1L, "Teste", "teste_descricao", "teste1@gmail.com")
        }


        fun vaildAutor(): Autor {
            return Autor(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }


        fun updateAutor(): Autor {
            return Autor(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }

        fun fieldAutor(): Autor {
            return Autor(1L, " ", "teste_descricao", "  ")
        }
    }
}