package workloadstats.utils;

/**
 * Enum of all calendar event types available in the program
 * @author Ilkka
 */
public enum EventType {
    COURSE("Kurssi"),
    LECTURE("Luento"),
    EXERCISE("Harkka"),
    EXAM("Koe"),    
    PERSONAL("Personal"),
    TEAMWORK("Teamwork"),
    TRASH("Roskaa");
    
    private String descr;
    
    private EventType(String description) {
        this.descr = description;
    }
    
    public String getDescr() {
        return descr;
    }


}
