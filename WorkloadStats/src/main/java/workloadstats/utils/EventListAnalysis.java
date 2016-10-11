package workloadstats.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.EventType;
import workloadstats.domain.model.Exam;
import workloadstats.domain.model.Exercise;
import workloadstats.domain.model.Lecture;
import workloadstats.domain.model.Personal;
import workloadstats.domain.model.Teamwork;

/**
 * Analysis class that tells us things about a list of calendar events Private
 * analysis methods are listed in their chronological order, getters after that.
 *
 * @author Ilkka
 */
public class EventListAnalysis {

    private EventType[] eventTypes = {EventType.LECTURE, EventType.EXERCISE, EventType.PERSONAL, EventType.TEAMWORK, EventType.EXAM};
    private StatusType[] statusTypes = {StatusType.CONFIRMED, StatusType.CANCELLED, StatusType.TENTATIVE};

    private List<Event> events;
    private Event earliest;
    private Event latest;

    private Map<EventType, Map<StatusType, Long>> durations;
    private Long totalDuration;

    public EventListAnalysis(List<Event> el) {
        this.events = el;
        this.earliest = el.get(0);
        this.latest = el.get(0);
        this.totalDuration = new Long(0);
        durationMap();
        analyseEvents();
    }

    private void durationMap() {
        Map<EventType, Map<StatusType, Long>> duramap = new HashMap<>();

        for (EventType eventType : eventTypes) {
            duramap.put(eventType, new HashMap<>());

            for (StatusType statusType : statusTypes) {
                duramap.get(eventType).put(statusType, new Long(0));

            }
        }
        this.durations = duramap;
    }

    private void analyseEvents() {
        for (Event event : events) {
            findTimeSpan(event);
            Long duration = findDuration(event);
            EventType etype = findEventType(event);
            StatusType stype = findStatusType(event);

            Long old = durations.get(etype).get(stype);
            Long add = old + duration;
            durations.get(etype).put(stype, add);
            totalDuration += duration;
        }
    }

    private void findTimeSpan(Event ev) {
        if (ev.getStartDate().getDate().before(earliest.getStartDate().getDate())) {
            earliest = ev;
        }
        if (ev.getEndDate().getDate().after(latest.getEndDate().getDate())) {
            latest = ev;
        }
    }

    private Long findDuration(Event event) {
        Instant start = event.getStartDate().getDate().toInstant();
        Instant end = event.getEndDate().getDate().toInstant();
        long gap = ChronoUnit.MINUTES.between(start, end);

        return gap;
    }

    private EventType findEventType(Event ev) {
        if (ev.getClass().equals(Lecture.class)) {
            return EventType.LECTURE;
        }
        if (ev.getClass().equals(Exercise.class)) {
            return EventType.EXERCISE;
        }
        if (ev.getClass().equals(Personal.class)) {
            return EventType.PERSONAL;
        }
        if (ev.getClass().equals(Teamwork.class)) {
            return EventType.TEAMWORK;
        }
        if (ev.getClass().equals(Exam.class)) {
            return EventType.EXAM;
        }

        return null;
    }

    private StatusType findStatusType(Event ev) {
        for (StatusType statusType : statusTypes) {
            if (ev.getEventStatus().equals(statusType.name())) {
                return statusType;
            }
        }
        return null;
    }

    private String minutesToHoursAndMinutes(Long l) {
        int h = (int) (l / 60);
        int m = (int) (l % 60);
        String hours = String.format("%02d", h);
        String minutes = String.format("%02d", m);

        return hours + ":" + minutes;
    }

    public String getTotalDuration() {
        return minutesToHoursAndMinutes(totalDuration);
    }

    public String getEventTypeAndStatusDuration(EventType et, StatusType st) {
        Long dur = durations.get(et).get(st);
        return minutesToHoursAndMinutes(dur);
    }

    public String getEventTypeTotalDuration(EventType et) {
        Long dur = new Long(0);
        Map<StatusType, Long> m = durations.get(et);
        for (StatusType statusType : m.keySet()) {
            dur += m.get(statusType);
        }

        return minutesToHoursAndMinutes(dur);
    }

    public String getStatusTotalDuration(StatusType st) {
        Long dur = new Long(0);
        for (EventType et : durations.keySet()) {
            dur += durations.get(et).get(st);
        }

        return minutesToHoursAndMinutes(dur);
    }

    public String getAttendancePercentage(EventType et) {
        Long attended = durations.get(et).get(StatusType.CONFIRMED);
        Long total = new Long(0);

        Map<StatusType, Long> m = durations.get(et);
        for (StatusType statusType : m.keySet()) {
            total += m.get(statusType);
        }

        if (total == 0) {
            return "-";
        }
        double percentage = 100 * (double) attended / total;

        return String.format("%.2f", percentage) + "%";
    }

    public String getTotalAttendancePercentage() {
        Long attended = new Long(0);
        for (EventType et : eventTypes) {
            attended += durations.get(et).get(StatusType.CONFIRMED);
        }
        if (totalDuration == 0) {
            return "-";
        }
        double percentage = 100 * (double) attended / totalDuration;

        return String.format("%.2f", percentage) + "%";
    }

    public String getTimespan() {
        return earliest.getStartDateString() + " - " + latest.getEndDateString();
    }

}
