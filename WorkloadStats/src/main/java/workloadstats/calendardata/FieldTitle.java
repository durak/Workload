package workloadstats.calendardata;

/**
 * Enumeration for identifiers of data needed in event creation
 *
 * @author Ilkka
 */
public enum FieldTitle {
    COURSENAME("Kurssin nimi"),
    EVENTNAME("Tapahtuman nimi"),
    DATE("Alkupvm (VVVVKKPP"),
    STARTTIME("Aloitusaika (HHMMSS)"),
    ENDTIME("Lopetusaika (HHMMSS");
    
    private String title;

    private FieldTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get enum description
     * @return 
     */
    public String getDescr() {
        return title;
    }
}
