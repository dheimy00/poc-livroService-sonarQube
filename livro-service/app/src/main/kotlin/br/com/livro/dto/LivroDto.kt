package br.com.livro.dto


import com.fasterxml.jackson.annotation.*
import java.util.*
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.*

data class LivroDto(

        @JsonProperty("id")
        var id: Long,

        @JsonProperty("titulo")
        @field:NotBlank(message = "O campo título é obrigatório")
        @field:Size(min = 4)
        var titulo: String,

        @JsonProperty("resumo")
        @field:NotBlank(message = "O campo título é obrigatório")
        @field:Size(min = 4, max = 500, message = "O máximo é 500 caracteres")
        var resumo: String,

        @JsonProperty("isbn")
        @field:NotBlank(message = "O campo isbn é obrigatório")
        @field:Size(min = 4)
        var isbn: String,

        @JsonProperty("sumario")
        @field:NotBlank(message = "O campo sumário é obrigatório")
        var sumario: String,

        @JsonProperty("pagina")
        @field:NotNull(message = "O campo página é obrigatório")
        @field:Min(value = 100, message = "O mínimo é de 100")
        var pagina: Int,

        @JsonProperty("data_publicacao")
        @Temporal(TemporalType.DATE)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "America/Sao_Paulo")
        var dataPublicacao: Date,

        @JsonProperty("id_autor")
        var id_autor: Long,

        @JsonProperty("id_categoria")
        var id_categoria: Long,
) {}