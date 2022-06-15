package br.com.livro.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CategoriaDto(

        @JsonProperty("id")
        var id: Long,

        @JsonProperty("nome")
        @field:NotBlank(message = "O campo nome é obrigatório")
        @field:Size(min = 4)
        var nome: String
) {}