package loez.nllr.userinterface;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import loez.nllr.algorithm.Argmax;
import loez.nllr.algorithm.Argmax.Result;
import loez.nllr.algorithm.Nllr;
import loez.nllr.datastructure.ArrayList;
import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;
import loez.nllr.domain.TimeSpan;
import loez.nllr.preprocessor.PreProcessor;
import loez.nllr.preprocessor.SimplePreprocessor;
import loez.nllr.preprocessor.exception.StemmerCreationException;
import loez.nllr.reader.CorpusReader;

/**
 * A command line interface for the NLLR-application
 * @author ljleppan
 */
public class CommandLineInterface implements UserInterface{
    private final Scanner in = new Scanner(System.in);
    private DateFormat dateFormat;
    private ArrayList<String> preProcessorNames;
    private ArrayList<PreProcessor> preProcessors;
    private PreProcessor preProcessor;
    private TimeSpan timeSpan;
    private CorpusReader corpusReader;
    private Corpus referenceCorpus;
    private Corpus testCorpus;
    private ArrayList<Corpus> timePartitions;
    private Nllr nllr;
    
    private int correct = 0;
    private int wrong = 0;
    
    private static final String DEFAULT_DATE_STRING = "d-MMM-yyyy";
    
    /**
     * Sets up possible preprocessors and their names.
     * A preprocessor and its name should have same index in both lists.
     * @param preProcessors     ArrayList of preprocessors
     * @param preProcessorNames ArrayList of names of preprocessor
     */
    @Override
    public void setupPreprocessors(ArrayList<PreProcessor> preProcessors, ArrayList<String> preProcessorNames){
        this.preProcessors = preProcessors;
        this.preProcessorNames = preProcessorNames;
    }
    
    /**
     * Starts up the user interface
     */
    @Override
    public void run() {
        getDateFormat(); 
        getPreprocessor();
        getLanguage();
        getCorpusReader();
        getReferenceCorpus();
        getTimePartitionSize();
        processTimePartitions();
        setupNllr();
        
        printCommands();
        while(true){
            printCommandPrompt();
            String input = in.nextLine();
            
            if (input.equals("quit")){
                return;
            }
            if (input.equals("help")){
                printCommands();
            }
            if (input.equals("random")){
                processRandom();
            }
            if (input.equals("single")){
                processSingle();
            }
            if (input.equals("corpus")){
                processMultiple();
            }
        }
    }

    private void getDateFormat() {
        String dateFormatString = queryFor("Set date format:");
        if (dateFormatString.isEmpty()){
            dateFormatString = DEFAULT_DATE_STRING;
        }
        dateFormat = new SimpleDateFormat(dateFormatString, Locale.US);
    }
    
    private void getPreprocessor(){       
        printPreProcessorPrompt();
        printCommandPrompt();
        String preprocessorString = in.nextLine();
        
        while (!preProcessorNames.contains(preprocessorString.toLowerCase().trim())){
            System.out.println("\tNo such preprocessor. ");
            printPreProcessorPrompt();
            preprocessorString = in.nextLine();
        }
        
        int index = preProcessorNames.indexOf(preprocessorString);
        preProcessor = preProcessors.get(index);
    }

    private void printPreProcessorPrompt() {
        System.out.print("Set preprocessor ["+preProcessorNames.get(1));
        for (int i = 2; i < preProcessorNames.size(); i++) {
            System.out.print(", "+preProcessorNames.get(i));
        }
        System.out.println("]: ");
    }
    
    private void getLanguage() {
        if (!(preProcessor instanceof SimplePreprocessor)){
            String language = queryFor("Set language:");
            try {
                preProcessor.setLanguage(language);
            } catch (StemmerCreationException e){
                System.out.println("\tSomething went wrong, attempting to use default language ...");
            }
            System.out.println("\tLanguage set to "+preProcessor.getLanguage());
        }
    }
    
    private void getCorpusReader() {
        corpusReader = new CorpusReader();
    }
    
    private void getReferenceCorpus(){
        String referenceCorpusPath = queryFor("Set path to reference corpus:");
        while (!processReferenceCorpus(referenceCorpusPath)) {
            System.out.println("File not found.");
            referenceCorpusPath = queryFor("Set path to reference corpus:");
        }
    }

    private boolean processReferenceCorpus(String referenceCorpusPath) {
        System.out.println("\tProcessing reference corpus (this might take long) ... ");
        try{
            referenceCorpus = corpusReader.readCorpus(referenceCorpusPath, dateFormat, preProcessor);
        } catch (FileNotFoundException e){
            return false;
        }
        System.out.println("\tDone processing reference corpus. \n");
        return true;
    }
    
    private void getTimePartitionSize(){
        while (true){ 
            printTimePartitionSizePrompt();
            printCommandPrompt();
            String input = in.nextLine().trim().toLowerCase();
            if (input.equals("") || input.equals("daily")){
                timeSpan = new TimeSpan(referenceCorpus.getStartDate(), TimeSpan.Length.DAILY);
                break;
            }
            if(input.equals("weekly")){
                timeSpan = new TimeSpan(referenceCorpus.getStartDate(), TimeSpan.Length.WEEKLY);
                break;
            }
            if(input.equals("biweekly")){
                timeSpan = new TimeSpan(referenceCorpus.getStartDate(), TimeSpan.Length.BIWEEKLY);
                break;
            }
            if(input.equals("monthly")){
                timeSpan = new TimeSpan(referenceCorpus.getStartDate(), TimeSpan.Length.MONTHLY);
                break;
            }
            if(input.equals("yearly")){
                timeSpan = new TimeSpan(referenceCorpus.getStartDate(), TimeSpan.Length.YEARLY);
                break;
            }
            
            System.out.println("\tInvalid time partition size.");
        }        
    }
    
    private void printTimePartitionSizePrompt() {
        System.out.println("Set time partition size [daily, weekly, biweekly, monthly, yearly]: ");
    }

    private void processTimePartitions(){
        timePartitions = new ArrayList<>();
        
        Calendar startDate = (Calendar) referenceCorpus.getStartDate().clone(); 
        Calendar endDate = (Calendar) referenceCorpus.getEndDate().clone();
        
        System.out.println("Getting time partitions for the reference corpus spanning "+ dateFormat.format(startDate.getTime()) + " to " + dateFormat.format(endDate.getTime())+" :");
                
        while (!timeSpan.getStart().after(endDate)){
            processSingleTimepartition(timeSpan.getStart(), timeSpan.getEnd());
            timeSpan.advance();
        }
        System.out.println("Done building time partitions.\n");
    }

    private void clearDate(Calendar date) {
        date.clear(Calendar.HOUR);
        date.clear(Calendar.MINUTE);
        date.clear(Calendar.SECOND);
    }
    
    private void processSingleTimepartition(Calendar partitionStartDate, Calendar partitionEndDate){     
        System.out.print("\t"+dateFormat.format(partitionStartDate.getTime()) + " - " +dateFormat.format(partitionEndDate.getTime()));        
        Corpus timePartition = referenceCorpus.getTimePartition(partitionStartDate, partitionEndDate);
        
        if (!timePartition.getDocuments().isEmpty()){
                timePartitions.add(timePartition);
                System.out.println(" ("+timePartition.getDocuments().size()+" documents)");
        } else {
            System.out.println(" Partition was empty, skipping.");
        }
    }
    
    private void setupNllr(){
        nllr = new Nllr(referenceCorpus);
    }
    
    private void printCommands(){
        System.out.println("Known commands:");
        System.out.println("\trandom -- Processes a random document from the Reference corpus.");
        System.out.println("\tsingle -- Input custom text for processing.");
        System.out.println("\tcorpus -- Process a test corpus.");
        System.out.println("\thelp   -- Shows this help.");
        System.out.println("\tquit   -- Quits the application.");
    }
    
    private String queryFor(String query){
        System.out.println(query);
        printCommandPrompt();
        String command = in.nextLine();
        return command;
    }
    
    private void printCommandPrompt(){
        System.out.print(" > ");
    }
    
    private void processRandom(){
        int referenceCorpusSize = referenceCorpus.getDocuments().size();
        int randomDocumentId = new Random().nextInt(referenceCorpusSize);
        Document document = referenceCorpus.getDocuments().get(randomDocumentId);
        
        System.out.println("Random document #"+randomDocumentId);
        processDocument(document);
    }
    
    private void processSingle(){
        String raw = queryFor("Input text body:");
        
        String body = preProcessor.process(raw);
        System.out.println("Processed text:");
        System.out.println("\t"+body);
        
        Document document = new Document(body);
        processDocument(document);
    }
    
    private void processMultiple(){
        correct = 0;
        wrong = 0;
        getTestCorpus();
        for (Document document : testCorpus.getDocuments()){
            processDocument(document);
        }
        System.out.println("Correct: "+correct + ", Wrong:" + wrong);
        correct = 0;
        wrong = 0;
    }
    
    private void getTestCorpus(){
        String testCorpusPath = queryFor("Set path to test corpus:");
        while (!processTestCorpus(testCorpusPath)){
            System.out.println("File not found.");
            testCorpusPath = queryFor("Set path to test corpus:");
        }        
    }

    private boolean processTestCorpus(String testCorpusPath) {
        System.out.println("\tProcessing test corpus (this might take long) ... ");
        try {
            testCorpus = corpusReader.readCorpus(testCorpusPath, dateFormat, preProcessor);
        } catch (FileNotFoundException e){
            return false;
        }
        System.out.println("\tDone processing test corpus. \n");
        return true;
    }
    
    private void processDocument(Document document){        
        Object[] argMaxArgs = {document};
        Result<Corpus> result = new Argmax<Corpus>().single(nllr, timePartitions, argMaxArgs);
        
        Corpus resultCorpus = result.getArgument();
        double resultNllr = result.getValue();

        printResult(document, resultCorpus, resultNllr);
    }
        
    private void printResult(Document document, Corpus resultCorpus, double resultNllr){
        String docDate = "UNKNOWN";
        if (document.getDate() != null){
            docDate = dateFormat.format(document.getDate().getTime());
        }
        
        String corpStartDate = "UNKNOWN";
        if (resultCorpus != null && resultCorpus.getStartDate() != null){
            corpStartDate = dateFormat.format(resultCorpus.getStartDate().getTime());
        }
        
        String corpEndDate = "UNKNOWN";
        if (resultCorpus != null && resultCorpus.getEndDate() != null){
            corpEndDate = dateFormat.format(resultCorpus.getEndDate().getTime());
        }
        
        if (document.getDate() != null && resultCorpus.getEndDate() != null && resultCorpus.getStartDate() != null){
            if (!document.getDate().before(resultCorpus.getStartDate()) && !document.getDate().after(resultCorpus.getEndDate())){
                correct++;
            } else {
                wrong++;
            }
        }
        
        StringBuilder out = new StringBuilder();
        out.append("Actual date: ");
        out.append(docDate);
        out.append("\n\t");
        out.append(corpStartDate);
        out.append(" - ");
        out.append(corpEndDate);
        out.append(" : ");
        out.append(resultNllr);
        out.append("\n");
        
        System.out.println(out.toString());
    }
}