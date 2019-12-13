package me.sedlar.spring_test.model

import javax.persistence.*

@Entity
@Table(name = "package")
class Package(
    @Column(name = "name")
    val name: String = "",

    @Column(name = "version")
    val version: String = "",

    @Column(name = "bucket")
    val bucket: String = "",

    @Column(name = "description", length = 550)
    val description: String = ""
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long = 0

    override fun toString(): String {
        return "Package[id=$id, name=$name, version=$version, bucket=$bucket, description=$description]"
    }
}