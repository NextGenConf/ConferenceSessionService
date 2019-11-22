package conferencesessionservice.services

import java.time.LocalDateTime

interface IGraphDatabaseService {
    fun getSessionByConference(conferenceId: String): List<String>
    fun addSessionToConference(conferenceId: String, sessionId: String, startTime: LocalDateTime)
}