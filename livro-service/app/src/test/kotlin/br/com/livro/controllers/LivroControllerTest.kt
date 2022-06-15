package br.com.livro.controllers

import br.com.livro.dto.LivroDto
import br.com.livro.service.LivroService
import br.com.livro.utils.GeneralMessage
import br.com.livro.builders.requests.LivroDtoBuilder
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
@DisplayName("Livro Controller Test")
class LivroControllerTest {


    @InjectMocks
    lateinit var livroController: LivroController

    @Mock
    lateinit var livroService: LivroService

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
    @Order(1)
    fun `Criar livro quando sucesso`() {

        // Given
        //
        //Save
        BDDMockito.`when`(livroService.save(LivroDtoBuilder.livroRequest()))
            .thenReturn(LivroDtoBuilder.livroResponse())

        // When
        //
        val responseEntity: ResponseEntity<LivroDto> = livroController.save(
            LivroDtoBuilder.livroRequest(),
        )

        // Then
        //
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    @Test
    @Order(2)
    fun `Atualizar livro quando sucesso`() {

        // Given
        //
        //Update
        BDDMockito.`when`(livroService.update(LivroDtoBuilder.livroRequest().id, LivroDtoBuilder.updateLivro()))
            .thenReturn(LivroDtoBuilder.updateLivro())

        // When
        //
        val responseEntity: ResponseEntity<GeneralMessage> =
            livroController.update(LivroDtoBuilder.livroRequest().id, LivroDtoBuilder.updateLivro())

        // Then
        //
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }


    @Test
    @Order(3)
    fun `Obter id livro quando sucesso`() {

        // Given
        //
        //Delete
        BDDMockito.doNothing().`when`(livroService).deleteById(LivroDtoBuilder.livroRequest().id)

        // When
        //
        val responseEntity: ResponseEntity<LivroDto> =
            livroController.findById(LivroDtoBuilder.livroRequest().id)

        // Then
        //
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }


    @Test
    @Order(4)
    fun `Excluir id livro quando sucesso`() {

        // Given
        //
        //FindById
        BDDMockito.doNothing().`when`(livroService).deleteById(LivroDtoBuilder.livroRequest().id)

        // When
        //
        val responseEntity: ResponseEntity<GeneralMessage> =
            livroController.deleteById(LivroDtoBuilder.livroRequest().id)

        // Then
        //
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }


    @Test
    @Order(5)
    fun `Consultar livro quando sucesso`() {

        // Given
        //
        //FindById
        val listLivros: List<LivroDto> =
            listOf(LivroDtoBuilder.livroResponse(), LivroDtoBuilder.livroResponse())
        BDDMockito.`when`(livroService.findAll()).thenReturn(listLivros)

        // When
        //
        val livros = livroController.findAll().body

        // Then
        //
        Assertions.assertThat(livros).isNotNull.isNotEmpty.hasSize(2)
        Assertions.assertThat(livros?.get(0)?.titulo).isEqualTo(LivroDtoBuilder.livroRequest().titulo)
    }


}