package com.smartcity.ai.agentiaservice.controller;

import com.smartcity.ai.agentiaservice.tools.CityTools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/agent")
@Tag(name = "Agent IA", description = "API d'Agent Intelligent pour interroger les services de la Smart City")
public class AgentController {

    private final ChatClient chatClient;

    public AgentController(ChatClient.Builder builder, CityTools cityTools) {
        this.chatClient = builder
                // 1. Définition du rôle (System Prompt)
                .defaultSystem("""
                        Tu es l'Assistant Intelligent de la Smart City.
                        Tu aides les citoyens en vérifiant le trafic et la consommation d'énergie dans différentes zones.
                        
                        RÈGLES STRICTES :
                        - Utilise UNIQUEMENT tes outils pour répondre (getTrafficStatus, getEnergyConsumption, listAllTraffic, listAllEnergy)
                        - Ne réponds QU'aux questions sur le trafic et l'énergie de la ville
                        - Si la question ne concerne pas ces sujets, réponds exactement : "Je ne peux répondre qu'aux questions sur le trafic et l'énergie de la Smart City"
                        - NE DONNE JAMAIS d'informations inventées ou supposées
                        - Si un outil retourne une erreur ou aucune donnée, dis explicitement : "Aucune donnée disponible pour cette zone"
                        - Si un service est indisponible, dis : "Le service [nom] est temporairement indisponible"
                        
                        FORMAT DES RÉPONSES :
                        - Pour les incidents : Liste TOUS les incidents avec le nom de zone, l'état (BLOQUE/DENSE/FLUIDE) et si incident=true
                        - Pour les résumés : Utilise des puces (*) pour chaque zone avec détails complets
                        - Sois précis avec les chiffres (ex: 850,3 kW/h, pas "environ 850")
                        - Structure tes réponses en sections claires avec titres
                        - Mets en évidence les zones problématiques
                        
                        EXEMPLE DE BONNE RÉPONSE pour "Y a-t-il des incidents ?" :
                        "Il y a actuellement des incidents sur les routes de la ville. Voici les zones affectées :
                        
                        * L'Autoroute-Nord est bloquée en raison d'un incident.
                        * La Zone-Sud est également bloquée en raison d'un incident.
                        
                        Les autres zones (Centre-Ville, Zone-Industrielle, Nord, Est, Ouest) sont fluides ou denses mais sans incident."
                        
                        Outils disponibles :
                        - getTrafficStatus(zone) : état du trafic dans une zone spécifique
                        - getEnergyConsumption(zone) : consommation énergétique d'une zone
                        - listAllTraffic() : liste toutes les zones de trafic
                        - listAllEnergy() : liste toutes les zones d'énergie
                        
                        Réponds de manière naturelle, détaillée et UNIQUEMENT basé sur les résultats réels des outils.
                        """)
                // 2. Injection des outils locaux
                .defaultTools(cityTools)
                // 3. Logger pour debug
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    /**
     * Endpoint de chat simple
     * Exemple: GET /agent/chat?question=Quelle est la situation du trafic au Centre-Ville ?
     */
    @GetMapping("/chat")
    @Operation(
            summary = "Chat avec l'Agent IA",
            description = "Posez une question à l'agent intelligent qui interrogera automatiquement les services Traffic et Energy selon le contexte",
            security = {}  // Endpoint public, pas besoin d'authentification
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Réponse de l'agent"),
            @ApiResponse(responseCode = "400", description = "Question invalide")
    })
    public String chat(
            @Parameter(description = "Question à poser à l'agent (ex: Quel est l'état du trafic au Centre-Ville ?)", required = true)
            @RequestParam String question) {
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
    @Operation(
            summary = "Chat en streaming",
            description = "Réponse de l'agent en mode streaming (effet machine à écrire)",
            security = {}  // Endpoint public, pas besoin d'authentification
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flux de réponse de l'agent")
    })
    public Flux<String> chatStream(
            @Parameter(description = "Question à poser à l'agent", required = true)
            @RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .stream()
                .content();
    }
}
