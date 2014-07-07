package loez.nllr.reader;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import loez.nllr.domain.Document;
import loez.nllr.preprocessor.PreProcessor;

/**
 * Converts csv-string to documents.
 * @author loezi
 */
public class DocumentConverter {

    /**
     * Parse a csv-string to a document
     * @param rawDocumentString The raw string
     * @param dateParser        A string-to-date parser
     * @param preprocessor      A words-to-tokens processor
     * @return                  A document
     */
    public static Document rawStringToDocument(String rawDocumentString, DateFormat dateParser, PreProcessor preprocessor) {
        String[] parts = rawDocumentString.split(";");
        
        if (parts.length < 3){
            return null;
        }
        
        Calendar date = null;
        try {
            Date dDate = dateParser.parse(parts[1]);
            date = new GregorianCalendar();
            date.setTime(dDate);
        } catch (ParseException ex) {
            return null;
        }
        
        StringBuilder body = new StringBuilder();
        for (int i = 2; i < parts.length; i++) {
            body.append(parts[i]);
        }
        
        String bodyProcessed = preprocessor.process(body.toString());
        
        return new Document(date, bodyProcessed);
    }
}