package br.com.livro.builders.requests

import br.com.livro.dto.AutorDto
import java.util.*


class AutorDtoBuilder {

    companion object {
        fun autorRequest(): AutorDto {
            return AutorDto(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }

        fun validAutor(): AutorDto {
            return AutorDto(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }

        fun updateAutor(): AutorDto {
            return AutorDto(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }

        fun autorResponse(): AutorDto {
            return AutorDto(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }
    }

}