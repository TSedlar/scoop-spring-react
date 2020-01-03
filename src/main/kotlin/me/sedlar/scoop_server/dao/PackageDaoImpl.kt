package me.sedlar.scoop_server.dao

import me.sedlar.spring.hibernate.query
import me.sedlar.scoop_server.model.Package
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class PackageDaoImpl : PackageDao {

    @Autowired
    lateinit var sessionFactory: SessionFactory

    override fun save(pkg: Package) {
        val session = sessionFactory.openSession()
        session.beginTransaction()
        session.saveOrUpdate(pkg)
        session.transaction.commit()
        session.close()
    }

    override fun findAll(): List<Package> {
        return sessionFactory.query(Package::class.java) { namespace ->
            with(namespace) {
                select()
                    .orderAsc(getString("name").toLowerCase())
            }
        }
    }

    override fun findByNameOrDescription(queryString: String): List<Package> {
        return sessionFactory.query(Package::class.java) { namespace ->
            with(namespace) {
                select()
                    .where(
                        or(
                            containsIgnoreCase(getString("name"), queryString),
                            containsIgnoreCase(getString("description"), queryString)
                        )
                    )
                    .orderAsc(getString("name").toLowerCase())
            }
        }
    }
}