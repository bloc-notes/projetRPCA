package doyonbenoit.projetRPC.enumeration;

public enum ActionDeplacement {
    SPECTATEUR("majSpectateur"),
    ATTENTECOMBAT("majAttenteCombat"),
    COMBATANTROUGE("majCombatantRouge"),
    COMBATANTBLANC("majCombatantBlanc"),
    LISTEARBITRE("majListeArbitre"),
    AILLEUR("majAilleur"),
    ARBITE("majArbite"),
    COMBAT("");

    private final String strCheminRetourMaj;

    ActionDeplacement(String strCheminRetourMaj) {
        this.strCheminRetourMaj = strCheminRetourMaj;
    }

    public String getStrCheminRetourMaj() {
        return strCheminRetourMaj;
    }
}
