package me.sedlar.scoop_server.controller

import kotlinx.serialization.UnstableDefault
import me.sedlar.scoop_server.dao.PackageDao
import me.sedlar.scoop_server.helpers.Scoop
import me.sedlar.scoop_server.model.Package
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@UnstableDefault
@RestController
@RequestMapping(value = ["api"])
class HomeController {

    @Autowired
    lateinit var packages: PackageDao

    @PostConstruct
    fun loadInitialData() {
        Scoop.parsePackages().forEach { pkg -> packages.save(pkg) }
    }

    @GetMapping(value = ["/packages"], produces = ["application/json"])
    @ResponseBody
    fun fetchPackages(): List<Package> {
        return packages.findAll()
    }

    @GetMapping(value = ["/packages/search/{query}"], produces = ["application/json"])
    @ResponseBody
    fun searchPackages(
        @PathVariable("query") query: String
    ): List<Package> {
        return packages.findByNameOrDescription(query)
    }
}