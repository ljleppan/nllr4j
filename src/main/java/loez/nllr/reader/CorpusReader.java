package loez.nllr.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;
import loez.nllr.preprocessor.PreProcessor;

/**
 * A multi purpose corpus reader.
 * @author loezi
 */
public class CorpusReader {

    /**
     * Reads a corpus from a file.
     * @param path          Path to file
     * @param dateFormat    DateFormat for parsing datestrings to dates
     * @param preprocessor  Preprocessor for processing words to tokens
     * @return              A corpus parsed from the file
     * @throws java.io.FileNotFoundException
     */
    public Corpus readCorpus(String path, DateFormat dateFormat, PreProcessor preprocessor) throws FileNotFoundException{
        try(BufferedReader in = new BufferedReader(new FileReader(path))) {
            String rawDocumentString;
            Corpus corpus = new Corpus();
            while ((rawDocumentString = in.readLine()) != null){
                Document document = DocumentConverter.rawStringToDocument(rawDocumentString, dateFormat, preprocessor);
                if (document != null){
                    corpus.add(document);
                }
            }
            corpus.refreshStats();
            return corpus;
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
