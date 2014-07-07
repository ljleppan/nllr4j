package loez.nllr.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author loezi
 */
public class TimeSpanTest {
    DateFormat df;
    
    
    @Before
    public void setUp() {
        df = new SimpleDateFormat("d-MM-yyyy");
    }

    
    @Test
    public void datesAreCleared(){
        TimeSpan ts = new TimeSpan(Calendar.getInstance(), TimeSpan.Length.DAILY);
        assertEquals("The hour field should be cleared when calculating the real start date",
                0, ts.getStart().get(Calendar.HOUR));
        assertEquals("The minutes field should be cleared when calculating the real start date",
                0, ts.getStart().get(Calendar.MINUTE));
        assertEquals("The seconds field should be cleared when calculating the real start date",
                0, ts.getStart().get(Calendar.SECOND));
        assertEquals("The milliseconds field should be cleared when calculating the real start date",
                0, ts.getStart().get(Calendar.MILLISECOND));
    }

    @Test
    public void testDaily() {
        Calendar now = newCalendar(1,1,2000);
        TimeSpan ts = new TimeSpan(now, TimeSpan.Length.DAILY);
        assertEquals("A daily timespan's start date should be the setup date",
                format(now), format(ts.getStart()));
        assertEquals("A daily timespan's end date should be the setup date",
                format(now), format(ts.getEnd()));
        
        ts.advance();
        Calendar tomorrow = newCalendar(2,1,2000);
        assertEquals("Advancing should change start date appropriately",
                format(tomorrow), format(ts.getStart()));
        assertEquals("Advancing should change end date appropriately",
                format(tomorrow), format(ts.getEnd()));
    }
    
    @Test
    public void testWeekly(){
        Calendar now = newCalendar(19,2,2014);
        TimeSpan ts = new TimeSpan(now, TimeSpan.Length.WEEKLY);
        assertEquals("Start date should be set properly",
                format(newCalendar(17,2,2014)), format(ts.getStart()));
        assertEquals("End date should be set properly",
                format(newCalendar(23,2,2014)), format(ts.getEnd()));
        
        ts.advance();
        assertEquals("Advancing should change start date appropriately",
                format(newCalendar(24,2,2014)), format(ts.getStart()));
        assertEquals("Advancing should change end date appropriately",
                format(newCalendar(2,3,2014)), format(ts.getEnd()));
    }
    
    @Test
    public void testBiWeekly(){
        Calendar now = newCalendar(1,1,2014);
        TimeSpan ts = new TimeSpan(now, TimeSpan.Length.BIWEEKLY);
        assertEquals("Start date should be set properly",
                format(newCalendar(30,12,2013)), format(ts.getStart()));
        assertEquals("End date should be set properly",
                format(newCalendar(12,1,2014)), format(ts.getEnd()));
        
        ts.advance();
        assertEquals("Advancing should change start date appropriately",
                format(newCalendar(13,1,2014)), format(ts.getStart()));
        assertEquals("Advancing should change end date appropriately",
                format(newCalendar(26,1,2014)), format(ts.getEnd()));
    }
    
    @Test
    public void testMonthly(){
        Calendar now = newCalendar(19,2,2014);
        TimeSpan ts = new TimeSpan(now, TimeSpan.Length.MONTHLY);
        assertEquals("Start date should be set properly",
                format(newCalendar(1,2,2014)), format(ts.getStart()));
        assertEquals("End date should be set properly",
                format(newCalendar(28,2,2014)), format(ts.getEnd()));
        
        ts.advance();
        assertEquals("Advancing should change start date appropriately",
                format(newCalendar(1,3,2014)), format(ts.getStart()));
        assertEquals("Advancing should change end date appropriately",
                format(newCalendar(31,3,2014)), format(ts.getEnd()));
    }
    
    @Test
    public void testYearly(){
        Calendar now = newCalendar(19,2,2014);
        TimeSpan ts = new TimeSpan(now, TimeSpan.Length.YEARLY);
        assertEquals("Start date should be set properly",
                format(newCalendar(1,1,2014)), format(ts.getStart()));
        assertEquals("End date should be set properly",
                format(newCalendar(31,12,2014)), format(ts.getEnd()));
        
        ts.advance();
        assertEquals("Advancing should change start date appropriately",
                format(newCalendar(1,1,2015)), format(ts.getStart()));
        assertEquals("Advancing should change end date appropriately",
                format(newCalendar(31,12,2015)), format(ts.getEnd()));
    }
    
    private String format(Calendar calendar){
        return df.format(calendar.getTime());
    }
    
    private Calendar newCalendar(int day, int month, int year){
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DATE, day);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.YEAR, year);
        c.clear(Calendar.HOUR);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        return c;
    }
}
