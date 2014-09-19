package loez.nllr.domain;

import java.util.Calendar;

/**
 * A timespan.
 * @author loezi
 */
public class TimeSpan {

    /**
     * The length of the timespan.
     */
    public enum Length {

        /**
         * A single day.
         */
        DAILY,

        /**
         * A single week.
         */
        WEEKLY,

        /**
         * Two weeks.
         */
        BIWEEKLY,

        /**
         * A single month.
         */
        MONTHLY,

        /**
         * A single year.
         */
        YEARLY
    };

    private Calendar startDate;
    private Calendar endDate;
    private final Length length;

    /**
     * Creates a new timespan.
     * The actual start date is not necessarily the given start date, but the start date of the time period the given date belongs to.
     * @param startDate A date that is used to determine the start date of the
     * @param length    Length of the time span
     */
    public TimeSpan(final Calendar startDate, final Length length) {

        this.length = length;

        calculateRealStartDate(startDate);
        calculateEndDate();
    }

    /**
     * @return The start date of the time span
     */
    public Calendar getStart() {

        return (Calendar) this.startDate.clone();
    }

    /**
     * @return The end date of the time span
     */
    public Calendar getEnd() {

        return (Calendar) this.endDate.clone();
    }

    /**
     * Advanced the time span to the next equivalent time span.
     * For Length.DAILY, this is the next day. For Length.BIWEEKLY this is the next two-week period.
     */
    public void advance() {

        if (length == Length.DAILY) {
            startDate.add(Calendar.DATE, 1);
        } else if (length == Length.WEEKLY) {
            startDate.add(Calendar.WEEK_OF_YEAR, 1);
        } else if (length == Length.BIWEEKLY) {
            startDate.add(Calendar.DATE, 14);
        } else if (length == Length.MONTHLY) {
            startDate.add(Calendar.MONTH, 1);
        } else if (length == Length.YEARLY) {
            startDate.add(Calendar.YEAR, 1);
        }

        calculateEndDate();

    }

    private void calculateRealStartDate(final Calendar rawStartDate) {

        clearDate(rawStartDate);
        rawStartDate.setFirstDayOfWeek(Calendar.MONDAY);

        startDate = rawStartDate;
        //Default is Length.DAILY

        if (length == Length.WEEKLY) {
            startDate.add(Calendar.DAY_OF_WEEK, startDate.getFirstDayOfWeek() - startDate.get(Calendar.DAY_OF_WEEK));
        } else if (length == Length.BIWEEKLY) {
            startDate.add(Calendar.DAY_OF_WEEK, startDate.getFirstDayOfWeek() - startDate.get(Calendar.DAY_OF_WEEK));
            startDate.add(Calendar.WEEK_OF_YEAR, Calendar.WEEK_OF_YEAR % 2 - 1);
        } else if (length == Length.MONTHLY) {
            startDate.set(Calendar.DAY_OF_MONTH, startDate.getActualMinimum(Calendar.DAY_OF_MONTH));
        } else if (length == Length.YEARLY) {
            startDate.set(Calendar.DAY_OF_YEAR, startDate.getActualMinimum(Calendar.DAY_OF_YEAR));
        }
    }

    private void calculateEndDate() {

        endDate = (Calendar) startDate.clone();
        //Default is Length.DAILY

        if (length == Length.WEEKLY) {
            endDate.add(Calendar.DATE, 6);
        } else if (length == Length.BIWEEKLY) {
            endDate.add(Calendar.DAY_OF_YEAR, 13);
        } else if (length == Length.MONTHLY) {
            endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else if (length == Length.YEARLY) {
            endDate.set(Calendar.DAY_OF_YEAR, endDate.getActualMaximum(Calendar.DAY_OF_YEAR));
        }
    }

    private void clearDate(final Calendar date) {

        date.clear(Calendar.HOUR);
        date.clear(Calendar.HOUR_OF_DAY);
        date.clear(Calendar.MINUTE);
        date.clear(Calendar.SECOND);
        date.clear(Calendar.MILLISECOND);
    }

}
