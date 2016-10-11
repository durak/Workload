package workloadstats.utils;

/**
 * Event selection statistics enum
 * @author Ilkka
 */
public enum Stat {

    TIMESPAN("Alkupäivä - Loppupäivä"),
    TOTALDURATION("Kokonaiskesto (h)"),
    LECTURES("Luentoja (h)"),
    EXERCISES("Harkkoja (h)"),
    PERSONAL("Omaa työtä (h)"),
    TEAMWORK("Ryhmätyötä (h)"),
    CONFIRMED("Osallistuit"),
    TENTATIVE("Harkitset"),
    CANCELLED("Et osallistunut");

    private String descr;

    private Stat(String description) {
        this.descr = description;
    }

    /**
     * Get identifier description
     *
     * @return
     */
    public String getDescr() {
        return descr;
    }

}
