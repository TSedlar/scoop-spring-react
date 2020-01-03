package me.sedlar.scoop_server.dao

import me.sedlar.scoop_server.model.Package

interface PackageDao {

    fun save(pkg: Package)
    fun findAll(): List<Package>
    fun findByNameOrDescription(queryString: String): List<Package>
}