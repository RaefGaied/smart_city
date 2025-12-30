package com.smartcity.ai.agentiaservice.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final ChatClient chatClient;

    public AgentController(ChatClient.Builder builder) {
        this.chatClient = builder
                // 1. Définition du rôle (System Prompt)
                .defaultSystem("""
                        Tu es l'Assistant Intelligent de la Smart City.
                        Tu aides les citoyens en vérifiant le trafic et la consommation d'énergie dans différentes zones.
                        
                        Utilise tes outils disponibles pour répondre aux questions factuelles :
                        - getTrafficStatus(zone) : pour obtenir l'état du trafic dans une zone
                        - getEnergyConsumption(zone) : pour obtenir la consommation énergétique d'une zone
                        - listAllTraffic() : pour lister toutes les zones de trafic
                        - listAllEnergy() : pour lister toutes les zones d'énergie
                        
                        Réponds de manière naturelle, claire et concise.
                        Si tu ne sais pas ou si les données ne sont pas disponibles, dis-le clairement.
                        Adapte ton ton selon le contexte : urgent pour incidents, rassurant pour situation normale.
                        """)
                // 2. Logger pour debug
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    /**
     * Endpoint de chat simple
     * Exemple: GET /agent/chat?question=Quelle est la situation du trafic au Centre-Ville ?
     */
    @GetMapping("/chat")
    public String chat(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    /**
     * Endpoint Streaming (pour effet machine à écrire)
     * Exemple: GET /agent/stream?question=Donne-moi un résumé de l'état de la ville
     */
    @GetMapping("/stream")
    public Flux<String> chatStream(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .stream()
                .content();
    }
}
