package br.com.livro.integration

import br.com.livro.controllers.CategoriaController
import br.com.livro.dto.CategoriaDto
import br.com.livro.service.CategoriaService
import br.com.livro.builders.requests.CategoriaDtoBuilder
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
@WebMvcTest(CategoriaController::class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Categoria Controller Test API")
class CategoriaControllerTest {


    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var categoriaService: CategoriaService


    @Test
    fun `Dados categoria quando categoria autor com sucesso entao retorno 201 `() {

        BDDMockito.given(categoriaService.save(CategoriaDtoBuilder.categoriaRequest()))
            .willReturn(CategoriaDtoBuilder.categoriaResponse())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/api/categorias")
                .content(objectMapper.writeValueAsString(CategoriaDtoBuilder.categoriaRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("computacao"))
            .andDo(MockMvcResultHandlers.print())

        BDDMockito.verify(categoriaService, Mockito.times(1)).save(CategoriaDtoBuilder.validCategoria())
    }

    @Test
    fun `Dados categoria quando buscar id categoria com sucesso entao retorno 200 `() {

        BDDMockito.given(categoriaService.findById(CategoriaDtoBuilder.categoriaRequest().id))
            .willReturn(CategoriaDtoBuilder.categoriaResponse())

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/api/categorias/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("computacao"))
            .andDo(MockMvcResultHandlers.print())

        BDDMockito.verify(categoriaService, Mockito.times(1)).findById(CategoriaDtoBuilder.validCategoria().id)
    }

    @Test
    fun `Dados categoria quando excluir categoria com sucesso entao retorno 200 `() {

        BDDMockito.given(categoriaService.findById(CategoriaDtoBuilder.categoriaRequest().id))
            .willReturn(CategoriaDtoBuilder.categoriaResponse())

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/v1/api/categorias/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Deletando categoria com sucesso"))


    }

    @Test
    fun `Dados categoria quando atualizada categoria com sucesso entao retorno 200`() {

        BDDMockito.given(categoriaService.save(CategoriaDtoBuilder.categoriaRequest()))
            .willReturn(CategoriaDtoBuilder.categoriaResponse())

        mockMvc.perform(
            MockMvcRequestBuilders.put("/v1/api/categorias/{id}", "1")
                .content(objectMapper.writeValueAsString(CategoriaDtoBuilder.categoriaRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Atualizo categoria com sucesso"))

    }

    @Test
    fun `Listas dos categorias quando lista categoria com sucesso entao retorno 200`() {

        val listaCategoria: List<CategoriaDto> = listOf(CategoriaDtoBuilder.categoriaResponse())
        BDDMockito.doReturn(listaCategoria).`when`(categoriaService).findAll()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("computacao"))
    }


}