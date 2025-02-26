package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataModel {
    private List<Spieler> spielerList;
    private List<Mannschaft> mannschaftList;
    private List<Turnier> turnierList;
    private Mannschaft selectedClub;

    public DataModel() {
        spielerList = new ArrayList<>();
        mannschaftList = new ArrayList<>();
        turnierList = new ArrayList<>();
    }

    public List<Spieler> getSpielerList() {
        return spielerList;
    }

    public List<Mannschaft> getMannschaftList() {
        return mannschaftList;
    }

    public List<Turnier> getTurnierList() {
        return turnierList;
    }

    public void addSpieler(Spieler spieler) {
        spielerList.add(spieler);
        selectedClub.addFeldspieler(spieler.getPlayerId());
    }

    public void removeSpieler(Spieler spieler) {
        spielerList.remove(spieler);
    }

    public void addMannschaft(Mannschaft mannschaft) {
        mannschaftList.add(mannschaft);
    }

    public void removeMannschaft(Mannschaft mannschaft) {
        mannschaftList.remove(mannschaft);
    }

    public void addTurnier(Turnier turnier) {
        turnierList.add(turnier);
    }

    public void removeTurnier(Turnier turnier) {
        turnierList.remove(turnier);
    }

    public Mannschaft getSelectedClub() {
        return selectedClub;
    }

    public void setSpielerList(List<Spieler> spielerList) {
        this.spielerList = spielerList;
    }

    public void setSelectedClub(Mannschaft selectedClub) {
        this.selectedClub = selectedClub;
    }

    public Spieler getSpielerById(UUID playerId) {
        for (Spieler spieler : spielerList) {
            if (spieler.getPlayerId() == playerId) {
                return spieler;
            }
        }
        return null;
    }
}
