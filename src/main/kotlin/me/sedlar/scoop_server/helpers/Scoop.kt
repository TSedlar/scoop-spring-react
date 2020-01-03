package me.sedlar.scoop_server.helpers

import java.net.URL
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import me.sedlar.scoop_server.model.Package

object Scoop {

    @UnstableDefault
    fun parsePackages(): List<Package> {
        val url = "https://raw.githubusercontent.com/TSedlar/scoop-frontend/master/pkglist.json"
        val raw = URL(url)
            .readText()
            .replace("\"description\": null,", "\"description\": \"\",")

        val multiBucketModel = Json.parse(MultiBucketModel.serializer(), raw)

        val packages = ArrayList<Package>()
        val buckets = arrayOf(multiBucketModel.main, multiBucketModel.extras, multiBucketModel.versions)

        buckets.forEach { bucket ->
            bucket.packages.forEach { pkg ->
                packages.add(Package(pkg.pkg, pkg.version, bucket.name, pkg.description))
            }
        }

        return packages
    }
}

@Serializable
private data class MultiBucketModel(
    val main: BucketModel,
    val extras: BucketModel,
    val versions: BucketModel
)

@Serializable
private data class BucketModel(
    val packages: Array<PackageModel>,
    val usage: String,
    val path: String,
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BucketModel

        if (!packages.contentEquals(other.packages)) return false
        if (usage != other.usage) return false
        if (path != other.path) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = packages.contentHashCode()
        result = 31 * result + usage.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

@Serializable
private data class PackageModel(val version: String, val pkg: String, val description: String, val website: String)