package br.com.livro.model

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "Livro")
data class Livro(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long,

        @Column(name = "titulo", unique = true, nullable = false)
        var titulo: String,

        @Column(name = "resumo", nullable = false)
        var resumo: String,

        @Column(name = "isbn", unique = true, nullable = false)
        var isbn: String,

        @Column(name = "sumario", nullable = false)
        var sumario: String,

        @Column(name = "pagina", nullable = false)
        var pagina: Int,

        @Column(name = "dataPublicacao")
        @DateTimeFormat(pattern = "dd-MM-yyyy")
        var dataPublicacao: Date,

        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
        @JoinColumn(name = "categoria_id")
        var categoria: Categoria,

        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
        @JoinColumn(name = "autor_id")
        var autor: Autor


)