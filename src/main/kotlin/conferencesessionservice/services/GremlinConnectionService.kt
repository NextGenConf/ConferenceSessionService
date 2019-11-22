package conferencesessionservice.services

import org.apache.tinkerpop.gremlin.driver.Client
import org.apache.tinkerpop.gremlin.driver.Cluster
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException

@Service
class GremlinConnectionService {

    private lateinit var cluster: Cluster
    private lateinit var client: Client

    init {
        try {
            cluster = Cluster.build(File("src/remote.yaml")).create()
            client = cluster.connect()
        } catch (e: FileNotFoundException) { // Handle file errors.
            println("Couldn't find the configuration file.")
            e.printStackTrace()
        }
    }
}