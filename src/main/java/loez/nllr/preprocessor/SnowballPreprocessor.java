package loez.nllr.preprocessor;

import loez.nllr.preprocessor.exception.StemmerCreationException;
import loez.nllr.datastructure.HashSet;
import loez.nllr.preprocessor.util.Numeral;
import loez.nllr.preprocessor.util.Punctuation;
import org.tartarus.snowball.SnowballProgram;

/**
 * A preprocessor that utilizes the Porter2 (Snowball) stemmer.
 * @author ljleppan
 */
public class SnowballPreprocessor implements PreProcessor{
    private final static String DEFAULT_LANGUAGE = "English";
    private final Punctuation punctuation = new Punctuation();   
    private final Numeral numeral = new Numeral();
    private SnowballProgram stemmer;
    private HashSet<String> languages;
    private String language;
    
    /**
     * Creates a new SnowballPreprocessor initialized with a certain language
     * @param language  The language
     * @throws StemmerCreationException
     */
    public SnowballPreprocessor(String language) throws StemmerCreationException{
        languages = new HashSet<>();
        languages.add("English");
        languages.add("Finnish");
        
        tryToSetLanguage(language);
    }
    
    /**
     * Creates a new SnowballPreprocessor with the default language.
     * @throws StemmerCreationException
     */
    public SnowballPreprocessor() throws StemmerCreationException{
        this("default");
    }
    
    /**
     * Sets the language of the Stemmer
     * @param language  The new language
     * @throws StemmerCreationException
     */
    @Override
    public void setLanguage(String language) throws StemmerCreationException{
        tryToSetLanguage(language);
    }
    
    /**
     * @return The language this preprocessor is set to use.
     */
    @Override
    public String getLanguage(){
        return this.language;
    }
    
    @Override
    public String process(String input) {
        String output = input;
        output = punctuation.remove(output);
        output = numeral.replace(output, "NUMERAL");
        output = stemMultiple(output);
        output = output.trim().toUpperCase();
        return output;
    }
    
    private void tryToSetLanguage(String language) throws StemmerCreationException{
        if (languages.contains(language)){
            buildSnowballProgram(language);
            this.language = language;
        }
        else{
            buildSnowballProgram(DEFAULT_LANGUAGE);
            this.language = DEFAULT_LANGUAGE;
        }
    }
    
    private void buildSnowballProgram(String language) throws StemmerCreationException{
        try {
            Class stemClass = Class.forName("org.tartarus.snowball.ext." + language + "Stemmer");
            stemmer = (SnowballProgram) stemClass.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            throw new StemmerCreationException(e.getMessage());
        }
    }
    
    private String stemMultiple(String sentence){
        String[] words = sentence.split(" ");
        
        StringBuilder output = new StringBuilder();
        for (String word : words){
            String token = stem(word);
            output.append( token );
            output.append(" ");
        }
        return output.toString();
    }
    
    private String stem(String word){
        stemmer.setCurrent(word);
        stemmer.stem();
        return stemmer.getCurrent(); 
    }
}