package conferencesessionservice.controllers
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConferenceSessionController {

    @GetMapping("/api/conferences/{id}/sessions/")
    fun getSessionsByConference(conferenceId: String): List<String> {
        return ArrayList();
    }
}