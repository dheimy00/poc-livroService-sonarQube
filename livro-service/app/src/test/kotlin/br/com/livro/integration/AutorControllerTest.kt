package br.com.livro.integration

import br.com.livro.controllers.AutorController
import br.com.livro.dto.AutorDto
import br.com.livro.service.AutorService
import br.com.livro.builders.requests.AutorDtoBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito.times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import java.util.*

@ActiveProfiles("Test")
@WebMvcTest(AutorController::class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Autor Controller Test API")
class AutorControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var autorService: AutorService

    @Test
    fun `Dados autores quando salvar autor com sucesso entao retorno 201 `() {

        BDDMockito.given(autorService.save(AutorDtoBuilder.autorRequest()))
            .willReturn(AutorDtoBuilder.autorResponse())

        mockMvc.perform(
            post("/v1/api/autores")
                .content(objectMapper.writeValueAsString(AutorDtoBuilder.autorRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.nome").value("Teste"))
            .andExpect(jsonPath("$.descricao").value("teste_descricao"))
            .andExpect(jsonPath("$.email").value("teste@gmail.com"))
            .andDo(print())

        BDDMockito.verify(autorService, times(1)).save(AutorDtoBuilder.validAutor())
    }

    @Test
    fun `Dados autores quando buscar id autor com sucesso entao retorno 200 `() {

        BDDMockito.given(autorService.findById(AutorDtoBuilder.autorRequest().id))
            .willReturn(AutorDtoBuilder.autorResponse())

        mockMvc.perform(
            get("/v1/api/autores/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nome").exists())
            .andExpect(jsonPath("$.nome").value("Teste"))
            .andExpect(jsonPath("$.descricao").value("teste_descricao"))
            .andExpect(jsonPath("$.email").value("teste@gmail.com"))
            .andDo(print())

        BDDMockito.verify(autorService, times(1)).findById(AutorDtoBuilder.validAutor().id)
    }

    @Test
    fun `Dados autores quando excluir autor com sucesso entao retorno 200 `() {

        BDDMockito.given(autorService.findById(AutorDtoBuilder.autorRequest().id))
            .willReturn(AutorDtoBuilder.autorResponse())

        mockMvc.perform(
            delete("/v1/api/autores/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(status().isOk)
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nome").doesNotExist())
            .andExpect(jsonPath("$.message").value("Deletando autor com sucesso"))


    }

    @Test
    fun `Dados autor quando atualizada autores com sucesso entao retorno 200`() {

        BDDMockito.given(autorService.save(AutorDtoBuilder.autorRequest()))
            .willReturn(AutorDtoBuilder.autorResponse())

        mockMvc.perform(
            put("/v1/api/autores/{id}", "1")
                .content(objectMapper.writeValueAsString(AutorDtoBuilder.autorRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(status().isOk)
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Atualizo autor com sucesso"))

    }


    @Test
    fun `Listas dos autores quando lista autores com sucesso entao retorno 200`() {

        val listaAtor: List<AutorDto> = listOf(AutorDtoBuilder.autorResponse())
        BDDMockito.doReturn(listaAtor).`when`(autorService).findAll()

        mockMvc.perform(
            get("/v1/api/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].nome").exists())
            .andExpect(jsonPath("$[0].nome").value("Teste"))
    }


}