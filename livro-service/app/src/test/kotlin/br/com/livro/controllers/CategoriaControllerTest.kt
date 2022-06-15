package br.com.livro.controllers


import br.com.livro.dto.CategoriaDto
import br.com.livro.service.CategoriaService
import br.com.livro.utils.GeneralMessage
import br.com.livro.builders.requests.CategoriaDtoBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@ActiveProfiles("Test")
@ExtendWith(SpringExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Categoria Controller Test")
class CategoriaControllerTest {

    @InjectMocks
    lateinit var categoriaController: CategoriaController

    @Mock
    lateinit var categoriaService: CategoriaService

    @AfterEach
    fun tearDown() {
        RequestContextHolder.resetRequestAttributes()
    }

    @BeforeEach
    fun setUp() {

        val httpServletRequest: HttpServletRequest
        httpServletRequest = MockHttpServletRequest()
        val servletRequestAttributes = ServletRequestAttributes(httpServletRequest)
        RequestContextHolder.setRequestAttributes(servletRequestAttributes)
    }

    @Test
    fun `Criar categoria quando sucesso`() {

        // Given
        //
        val createCategoriaRequestrBuilder = CategoriaDtoBuilder.categoriaRequest()
        //Save
        BDDMockito.`when`(categoriaService.save(createCategoriaRequestrBuilder))
            .thenReturn(CategoriaDtoBuilder.categoriaResponse())

        // When
        //
        val categoria = categoriaController.save(createCategoriaRequestrBuilder).body

        // Then
        //
        Assertions.assertThat(categoria).isNotNull
        Assertions.assertThat(categoria?.nome).isEqualTo(CategoriaDtoBuilder.validCategoria().nome)
    }


    @Test
    @Order(2)
    fun `Atualizar categoria quando sucesso`() {

        // Given
        //
        //Update
        BDDMockito.`when`(
            categoriaService.update(
                CategoriaDtoBuilder.updateCategoria().id,
                CategoriaDtoBuilder.updateCategoria()
            )
        )
            .thenReturn(CategoriaDtoBuilder.updateCategoria())
        // When
        //
        val idCategoria = categoriaController.update(
            CategoriaDtoBuilder.updateCategoria().id,
            CategoriaDtoBuilder.updateCategoria()
        ).body

        // Then
        //
        Assertions.assertThat(idCategoria).isNotNull
    }

    @Test
    @Order(3)
    fun `Obter id categoria quando sucesso`() {

        // Given
        //
        //FindById
        BDDMockito.`when`(categoriaService.findById(CategoriaDtoBuilder.categoriaRequest().id))
            .thenReturn(CategoriaDtoBuilder.categoriaResponse())

        // When
        //
        val idCategoria = categoriaController.findById(CategoriaDtoBuilder.categoriaRequest().id).body

        // Then
        //
        Assertions.assertThat(idCategoria).isNotNull
        Assertions.assertThat(idCategoria?.nome).isEqualTo(CategoriaDtoBuilder.validCategoria().nome)
    }

    @Test
    @Order(3)
    fun `Excluir id categoria quando sucesso`() {

        // Given
        //
        //Delete
        BDDMockito.doNothing().`when`(categoriaService).deleteById(CategoriaDtoBuilder.categoriaRequest().id)

        // When
        //
        val response: ResponseEntity<GeneralMessage> =
            categoriaController.deleteById(CategoriaDtoBuilder.categoriaRequest().id)

        // Then
        //
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

    }


    @Test
    @Order(3)
    fun `Consultar categoria quando sucesso`() {

        // Given
        //
        //FindAll
        val listCategoria: List<CategoriaDto> =
            listOf(CategoriaDtoBuilder.categoriaResponse(), CategoriaDtoBuilder.categoriaResponse())
        BDDMockito.`when`(categoriaService.findAll()).thenReturn(listCategoria)

        // When
        //
        val categorias = categoriaController.findAll().body

        // Then
        //
        Assertions.assertThat(categorias).isNotNull
        Assertions.assertThat(categorias?.get(0)?.nome).isEqualTo(CategoriaDtoBuilder.validCategoria().nome)
    }

}