package br.com.livro.dto


import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AutorDto(

        @JsonProperty("id")
        var id: Long,

        @JsonProperty("nome")
        @field:NotBlank(message = "O campo nome é obrigatório")
        @field:Size(min = 4)
        var nome: String,

        @JsonProperty("descricao")
        @field:NotBlank(message = "O campo descricao é obrigatório")
        @field:Size(min = 4)
        var descricao: String,

        @JsonProperty("email")
        @field:NotBlank(message = "O campo email é obrigatório")
        @field:Email
        var email: String
) {

}