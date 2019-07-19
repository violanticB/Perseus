package me.borawski.perseus.instance.tournament;

import me.borawski.perseus.instance.ServerType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Tournament {

    // Current status of tournament.
    private String currentState;

    // Tournament properties
    private int startingPlayers;
    private ServerType serverType;

    // First round data
    private int firstRoundAdvance = 3;
    private List<UUID> firstRoundContinue;

    // Second round data
    private int secondRoundAdvance = 2;
    private List<UUID> secondRoundContinue;

    // Third round data
    private int thirdRoundAdvance = 2;
    private List<UUID> thirdRoundContinue;

    private Map<UUID, Integer> tournamentScore;

    public Tournament(int startingPlayers, ServerType type) {
        this.startingPlayers = startingPlayers;
        this.serverType = type;

        this.currentState = "lobby";
        this.firstRoundContinue = new LinkedList<>();
        this.secondRoundContinue = new LinkedList<>();
        this.thirdRoundContinue = new LinkedList<>();
    }

    public String getState() {
        return currentState;
    }

    public int getStartingPlayers() {
        return startingPlayers;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public List<UUID> getFirstRoundContinue() {
        return firstRoundContinue;
    }

    public List<UUID> getSecondRoundContinue() {
        return secondRoundContinue;
    }

    public List<UUID> getThirdRoundContinue() {
        return thirdRoundContinue;
    }

}
