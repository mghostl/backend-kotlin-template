package com.mghostl.templates.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime

@Table(name = "templates")
@Entity
data class Template(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,

    var description: String? = null,

    var date: ZonedDateTime? = null,

    var createdAt: ZonedDateTime = ZonedDateTime.now(),

    @UpdateTimestamp
    var updatedAt: ZonedDateTime = ZonedDateTime.now()
)
