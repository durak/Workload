package workloadstats.ui;

/**
 * Enumeration of event property identifiers needed in event creation,
 * doubled with their descriptions as values
 * 
 * @author Ilkka
 */
public enum EvPropId {
    COURSENAME("Kurssin nimi"),
    EVENTNAME("Tapahtuman nimi"),
    DATE("Alkupvm (VVVVKKPP"),
    STARTTIME("Aloitusaika (HHMM)"),
    ENDTIME("Lopetusaika (HHMM"),
    EVENTTYPE("Tyyppi"),
    STATUS("Tila");
    
    private String descr;

    private EvPropId(String title) {
        this.descr = title;
    }
    
    /**
     * Get identifier value
     * @return 
     */
    public String getDescr() {
        return descr;
    }
}
