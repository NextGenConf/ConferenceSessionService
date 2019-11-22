package conferencesessionservice.services

import org.apache.tinkerpop.gremlin.process.traversal.dsl.GremlinDsl
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
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

    override fun getSessionByConference(conferenceId: String): List<String> {
        return graph.V()
                .has(conferenceLabel, idPropertyKey, conferenceId)
                .out(edgeLabel)
                .hasLabel(sessionLabel)
                .properties<String>()
                .values<String>(idPropertyKey)
                .toList()
    }

    override fun addSessionToConference(conferenceId: String, sessionId: String, startTime: LocalDateTime) {
        val conferenceTraversal = graph.V().has(conferenceLabel, idPropertyKey, conferenceId)
        val conference = if(conferenceTraversal.hasNext()) conferenceTraversal.next() else graph.addV(conferenceLabel).property(idPropertyKey, conferenceId).next()

        val sessionTraversal = graph.V().has(sessionLabel, idPropertyKey, sessionId)
        val session = if(sessionTraversal.hasNext()) sessionTraversal.next() else graph.addV(sessionLabel).property(idPropertyKey, sessionId).next()

        graph.V(conference).addE(edgeLabel).to(session).property(timePropertyKey, startTime).iterate()
    }


}