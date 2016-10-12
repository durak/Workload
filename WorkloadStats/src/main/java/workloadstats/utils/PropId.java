package workloadstats.utils;

/**
 * Enumeration of event property identifiers needed in event creation,
 * doubled with their descriptions as values
 * 
 * @author Ilkka
 */
public enum PropId {
    COURSENAME("Kurssin nimi"),
    EVENTNAME("Tapahtuman nimi"),
    DATE("Pvm"),
    STARTTIME("Aloitusaika"),
    ENDTIME("Lopetusaika"),
    EVENTTYPE("Tyyppi"),
    STATUS("Tila");
    
    private String descr;

    private PropId(String description) {
        this.descr = description;
    }
    
    /**
     * Get identifier description
     * @return 
     */
    public String getDescr() {
        return descr;
    }
    
        
}
