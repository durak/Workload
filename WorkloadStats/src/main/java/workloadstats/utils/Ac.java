package workloadstats.utils;

/**
 * ActionCommand enumeration
 *
 * @author Ilkka
 */
public enum Ac {
    TENTATIVE("Harkitset"),
    CONFIRMED("Kyllä"),
    CANCELLED("En"),
    NEWCALENDAR("Uusi kalenteri"),
    IMPORTCALENDAR("Tuo merkintöjä"),
    LOADCALENDAR("Lataa kalenteri"),
    SAVECALENDAR("Tallenna kalenteri"),
    NEWEVENT("Uusi tapahtuma"),
    DELETE_EVENT("Poista tapahtuma"),
    NEWCOURSE("Uusi kurssi"),
    DELETECOURSE("Poista kurssi");

    private String descr;

    private Ac(String description) {
        this.descr = description;
    }

    public String getDescr() {
        return descr;
    }
}
