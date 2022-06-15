package br.com.livro.integration


import br.com.livro.builders.requests.LivroDtoBuilder
import br.com.livro.controllers.LivroController
import br.com.livro.dto.LivroDto
import br.com.livro.service.LivroService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*


@ActiveProfiles("Test")
@WebMvcTest(LivroController::class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Livro Controller Test API")
class LivroControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var livroService: LivroService

    @Test
    fun `Dados livro quando salvar livro com sucesso entao retorno 201 `() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/api/livros")
                .content(objectMapper.writeValueAsString(LivroDtoBuilder.newlivroRequest()))
                .contentType(MediaType.APPLICATION_JSON)

        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Dados livro quando buscar id livro com sucesso entao retorno 200 `() {

        BDDMockito.given(livroService.findById(LivroDtoBuilder.livroRequest().id))
            .willReturn(LivroDtoBuilder.livroResponse())

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/api/livros/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Teste"))
            .andDo(MockMvcResultHandlers.print())

        BDDMockito.verify(livroService, Mockito.times(1)).findById(LivroDtoBuilder.livroRequest().id)
    }

    @Test
    fun `Dados livro quando excluir livro com sucesso entao retorno 200 `() {

        BDDMockito.given(livroService.findById(LivroDtoBuilder.livroRequest().id))
            .willReturn(LivroDtoBuilder.livroResponse())

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/v1/api/livros/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Deletando livro com sucesso"))


    }

    @Test
    fun `Dados livro quando atualizado livro com sucesso entao retorno 200`() {

        BDDMockito.given(livroService.save(LivroDtoBuilder.livroRequest()))
            .willReturn(LivroDtoBuilder.livroResponse())

        mockMvc.perform(
            MockMvcRequestBuilders.put("/v1/api/livros/{id}", "1")
                .content(objectMapper.writeValueAsString(LivroDtoBuilder.livroRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Atualizo livro com sucesso"))

    }


    @Test
    fun `Listas dos categorias quando lista categoria com sucesso entao retorno 200`() {

        val lista: List<LivroDto> = listOf(LivroDtoBuilder.livroResponse())
        BDDMockito.doReturn(lista).`when`(livroService).findAll()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/api/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Teste"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id_categoria").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id_autor").value("1"))
    }

}


