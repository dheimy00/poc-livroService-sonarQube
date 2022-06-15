package br.com.livro.controllers

import br.com.livro.dto.AutorDto
import br.com.livro.service.AutorService
import br.com.livro.builders.requests.AutorDtoBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@ActiveProfiles("Test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Autor Controller Test")
class AutorControllerTest {


    @InjectMocks
    lateinit var autorController: AutorController

    @Mock
    lateinit var autorService: AutorService

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
    fun `Criar autor quando sucesso`() {

        // Given
        //
        val createAutorRequestrBuilder = AutorDtoBuilder.autorRequest()
        //Save
        BDDMockito.`when`(autorService.save(AutorDtoBuilder.autorRequest()))
            .thenReturn(AutorDtoBuilder.autorResponse())

        // When
        //
        val autor = autorController.save(createAutorRequestrBuilder).body

        // Then
        //
        Assertions.assertThat(autor).isNotNull
        Assertions.assertThat(autor?.nome).isEqualTo(AutorDtoBuilder.validAutor().nome)
    }


    @Test
    @Order(2)
    fun `Atualizar autor quando sucesso`() {

        // Given
        //
        //Update
        BDDMockito.`when`(
            autorService.update(
                AutorDtoBuilder.updateAutor().id,
                AutorDtoBuilder.updateAutor()
            )
        )
            .thenReturn(AutorDtoBuilder.updateAutor())

        val updateAutorRequest = AutorDtoBuilder.updateAutor()

        // When
        //
        val updateAutor = autorController.update(AutorDtoBuilder.autorRequest().id, updateAutorRequest).body

        // Then
        //
        Assertions.assertThat(updateAutor).isNotNull
    }

    @Test
    @Order(3)
    fun `Obter id autor quando sucesso`() {

        // Given
        //
        //FindById
        BDDMockito.`when`(autorService.findById(AutorDtoBuilder.autorRequest().id))
            .thenReturn(AutorDtoBuilder.autorResponse())

        // When
        //
        val idAutor = autorController.findById(AutorDtoBuilder.autorRequest().id).body

        // Then
        //
        Assertions.assertThat(idAutor).isNotNull
        Assertions.assertThat(idAutor?.id).isEqualTo(AutorDtoBuilder.validAutor().id)
        Assertions.assertThat(idAutor?.nome).isEqualTo(AutorDtoBuilder.validAutor().nome)
    }

    @Test
    @Order(4)
    fun `Excluir id autor quando sucesso`() {

        // Given
        //
        //Delete
        BDDMockito.doNothing().`when`(autorService).deleteById(AutorDtoBuilder.autorRequest().id)

        // When
        //
        val idAutor = autorController.deleteById(AutorDtoBuilder.autorRequest().id).body

        // Then
        //
        Assertions.assertThat(idAutor).isNotNull
    }


    @Test
    @Order(5)
    fun `Consultar autor quando sucesso`() {

        // Given
        //
        //FindAll
        val list: List<AutorDto> =
            listOf(AutorDtoBuilder.autorResponse(), AutorDtoBuilder.autorResponse())
        BDDMockito.`when`(autorService.findAll()).thenReturn(list)

        // When
        //
        val listAutores: List<AutorDto>? = autorController.findAll().body

        // Then
        //
        Assertions.assertThat(listAutores).isNotNull

        Assertions.assertThat(listAutores?.get(0)?.nome).isEqualTo(AutorDtoBuilder.validAutor().nome)

    }


}