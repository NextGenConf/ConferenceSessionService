package conferencesessionservice.services

import org.apache.tinkerpop.gremlin.process.traversal.dsl.GremlinDsl
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Property
import org.apache.tinkerpop.gremlin.structure.VertexProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@GremlinDsl
class GremlinApiService @Autowired constructor(private var graph: GraphTraversalSource) : IGraphDatabaseService {

    private val conferenceLabel = "conference"
    private val sessionLabel = "session"
    private val edgeLabel = "hasSession"
    private val idPropertyKey = "id"
    private val timePropertyKey = "time"

    fun getSessionByConference(conferenceId: String): MutableList<out Property<String>>? {
        val test = graph.V()
                .has(conferenceLabel, idPropertyKey, conferenceId)
                .out(edgeLabel)
                .hasLabel(sessionLabel)
                .properties<VertexProperty<String>>(idPropertyKey)
                .toList()

        var res = ArrayList<String>();
        test.forEach {
            res.add(it.value().value())
        }
    }

    fun addSessionToConference(conferenceId: String, sessionId: String, startTime: LocalDateTime) {
        val conferenceTraversal = graph.V().has(conferenceLabel, idPropertyKey, conferenceId)
        val conference = if(conferenceTraversal.hasNext()) conferenceTraversal.next() else graph.addV(conferenceLabel).property(idPropertyKey, conferenceId).next()

        val sessionTraversal = graph.V().has(sessionLabel, idPropertyKey, sessionId)
        val session = if(sessionTraversal.hasNext()) sessionTraversal.next() else graph.addV(sessionLabel).property(idPropertyKey, sessionId).next()

        graph.V(conference).addE(edgeLabel).to(session).property(timePropertyKey, startTime).iterate()
    }


}