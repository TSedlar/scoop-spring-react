package me.sedlar.spring_test.dao

import me.sedlar.spring_test.model.Package

interface PackageDao {

    fun save(pkg: Package)
    fun findAll(): List<Package>
    fun findByNameOrDescription(queryString: String): List<Package>
}