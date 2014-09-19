package loez.nllr.userinterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import loez.nllr.preprocessor.PreProcessor;
import loez.nllr.preprocessor.SimplePreprocessor;
import loez.nllr.preprocessor.SnowballPreprocessor;
import loez.nllr.preprocessor.exception.StemmerCreationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import org.junit.rules.TemporaryFolder;

/**
 *
 * @author ljleppan
 */
public class CommandLineInterfaceTest {
    private UserInterface ui;
    private File testFile;

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {

        testFile = testFolder.newFile("test.csv");
        BufferedWriter out = new BufferedWriter(new FileWriter(testFile));
        out.write("TEST; 8-APR-1987 00:38:26.78;    BELGRADE, April 8 - ;YUGOSLAV WORKERS MAY BE ANGERED BY LOST SUBSIDIES;  Yugoslav government plans to stop subsidising loss-making firms will anger hundreds of thousands of workers, Western diplomats said.     The law, proposed by Prime Minister Branko Mikulic, goes into effect on July 1 and aims to end a long-standing practice of supporting unprofitable companies. Under the law, wage cuts will be imposed on losing enterprises, while those failing to recover within a six-month grace period will face liquidation.     The diplomats said Mikulic's attempt to create a market economy is inevitable, but has still come as a shock to those accustomed to government subsidies.     \"It was a bitter pill which had to be swallowed, but if an overdose is taken too abruptly, it may have adverse effects on the system,\" a Western diplomat said.     He said if the law was applied too strictly it would probably provoke a new wave of strikes and unrest.     Yugoslavia was swept by strikes last month following the introduction of a wage-freeze law, later amended to allow more flexibility and some exemptions in what some political analysts saw as a retreat by Mikulic.     But with inflation moving towards 100 pct, trade union leaders have asked how much more deprivation workers can take.     The union leaders said workers thoughout the country are already receiving salaries below limits set under existing law, while others have received no wages at all this year because their employers are unable to pay them.     Workers also complain much of their income is taken in local, state and federal taxes.     Many others are losing their motivation to work and confidence in government as they feel their decision-making powers are being eroded, trade union officials said.     Meanwhile, the official Tanjug news agency reported a paper and cellulose factory at Ivangrad in the Montenegro republic closed yesterday and 2,000 of its workers were given \"temporary leave.\"     Tanjug said the plant had been running at a loss for the 24 years it had been in operation, and its closure was the result of \"economic necessity\" rather than bankruptcy.  REUTER \n");
        out.write("TEST; 8-APR-1987 00:44:28.08;    PARIS, April 8 - ;FRENCH GOVERNMENT WINS CONFIDENCE VOTE;  The French government won, as expected, a vote of parliamentary confidence sought by Prime Minister Jacques Chirac on his general policies.     Votes from Chirac's Gaullist RPR party and its junior partner in the ruling coalition, the centre-right UDF, gave Chirac's cabinet a slim majority in the National Assembly.     A total of 294 deputies in the 577-member assembly voted to support Chirac, with 282 voting against. One member was absent.     The Socialists, Communists and the extreme-right National Front voted against the prime minister's call.  REUTER\n" );
        out.write("TEST; 9-APR-1987 05:42:58.89;    BRUSSELS, April 9 - ;EC SUGAR TENDER SEEN MARKING NO CHANGE IN POLICY;  The maximum export rebate granted at yesterday's EC sugar tender marked no change in policy over producer complaints that they are not obtaining the EC intervention price in exporting sugar outside the Community, EC Commission sources said.     The maximum rebate was 46.496 Ecus per 100 kilos for 118,350 tonnes of sugar, down from 46.864 Ecus the previous week, but the change is explained by world market conditions.     Producers claim the rebate was short of the level needed to obtain a price equivalent to the intervention price by over one Ecu per 100 kilos, and was 0.87 Ecu short the previous week, the sources said.     They said this was despite the fact that the Commission had to accept 785,000 tonnes of sugar into intervention from operators protesting that rebates are too low.     Operators have now until early May to withdraw this sugar. But they have not given any sign of planned withdrawals unless the Commission reviews its export policy, they said.  REUTER\n");
        out.write("TEST; 10-APR-1987 17:56:18.18;    FREEHOLD, N.J., April 10 -      ;DELMED INC <DMD> YEAR LOSS;  Oper shr loss 30 cts vs loss 1.27 dlrs     Oper net loss 8,648,000 vs loss 25.6 mln     Revs 27.4 mln vs 33.3 mln     Avg shrs 29.1 mln vs 20.1 mln     Note: Oper excludes loss on provision for discontinued operations of 971,000 vs 12.2 mln and loss from conversion of debt 587,000 vs gain of 1,734,000.     1985 oper excludes loss from pension plan liquidation of 631,000 and loss from discontinued operations of 1,015,000.   Reuter\n");
        out.write("TEST; 10-APR-1987 17:56:55.34;    DETROIT, April 10 - ;MARCH TRUCK SALES SAID UP 16.4 PCT;  Retail sales of trucks in March rose 16.4 pct over the same month last year, said the Motor Vehicle Manufacturers Association.     The trade group said dealers sold 377,617 trucks in March, up from 324,327 last year.     Year-to-date truck sales were up 3.6 pct at 934,074 from 1986's 901,757.  Reuter \n");
        out.write("TEST; 11-APR-1987 01:23:09.83;    HOUSTON, April 11 - ;COR TEXACO <TX> SAYS IT CAN'T POST MORE THAN 1 BILLION;  Texaco Inc, in documents filed with a Texas state appeals court, said a ruling forcing it to post a bond of more than one billion dlrs as security for a 10.53 billion dlr judgment would halt its credit agreements and force the company into bankruptcy.     A hearing is scheduled Monday on Texaco's motion to reduce the amount of bond required by Texas state law to secure a 1985 judgment in favour of Pennzoil Co <PZL> in a dispute over the acquisition of Getty Oil.     Pennzoil today said it proposed to the court that Texaco secure its bond with assets valued at about 50 pct of the bond.     It said this would not force Texaco into bankruptcy.     Richard Brinkman, Texaco's chief financial officer, said in a newly filed affidavit that the company was willing to use its 65 mln shares of <Texaco Canada Inc> as collateral for a letter of credit or loan to provide security to Pennzoil.     The Canadian unit's stock, valued at about 1.8 billion dlrs, was returned to Texaco earlier this week after being held as security by a federal district court in New York while Texaco awaited a ruling by the U.S. Supreme Court. Brinkman estimated that Texaco could borrow no more than one billion dlrs using its Canadian subsidiary stock as collateral for a loan.     Texaco, he said, was unwilling to pledge the stock of its other foreign subsidiary corporations as security because the stock is not widely traded and its market value was unclear.     Brinkman also said the company had already lost access to a four billion dlr revolving credit line since the initial jury verdict against Texaco.     Texaco had been able to raise working capital by selling about 700 mln dlrs in receivables to a group of banks since the judgment, he said. The banks earlier this week notified Texaco that they would not purchase any additional receivables because of the company's uncertain status, Brinkman said.     Four other credit agreements for 1.15 billion dlrs would also be cut off if a judgment of more than four billion dlrs is outstanding against the company and it is unable to obtain a stay, Brinkman said.     \"Since the Supreme Court decision on April 6, many other suppliers to Texaco have notified Texaco that their dealings with it are under review and a number have demanded cash payment or terminated their dealings with Texaco,\" he said.     The Supreme Court overturned a lower federal court decision to cut Texaco's bond to one billion dlrs, saying the issue should first be considered in Texas courts.     In a separate affidavit, <Morgan Stanley & Co> Managing Director Donald Brennan also said Texaco would be forced into bankruptcy if the company were forced to provide Pennzoil with security exceeding one billion dlrs.  REUTER \n");
        out.write("TEST; 11-APR-1987 01:27:53.25;    TOKYO, April 11 - ;JAPAN WARNS U.S. IT MAY RETALIATE IN TRADE DISPUTE;  Japan warned the United States it may take retaliatory measures if the United States imposes its planned trade sanctions on April 17, a senior government official said.     Shinji Fukukawa, Vice Minister of the International Trade and Industry Ministry, said in a statement Japan would consider measures under the General Agreement on Tariffs and Trade and other actions if the United States imposes 100 pct tariffs on some Japanese exports as planned next week.     However, Fukukawa said Japan was ready to continue trade talks with the United States despite its failure to convince America to call off the threatened tariffs during two days of emergency talks which ended in Washington yesterday.     Last month President Reagan announced the sanctions in retaliation for what he called Japan's failure to honour a July 1986 agreement to stop dumping computer microchips in markets outside the United States and to open its home market to American goods.     Fukukawa said the United States had regrettably not listened to Japan's explanation of its efforts to live up the pact and said Washington had not given any detailed explanation of why it planned to impose the tariffs.  REUTER \n");
        out.write("TEST; 12-APR-1987 22:21:56.67;    TOKYO, April 12 - ;AVERAGE YEN CD RATES FALL IN LATEST WEEK;  Average interest rates on yen certificates of deposit (CD) fell to 4.13 pct in the week ended April 8 from 4.33 pct the previous week, the Bank of Japan said.     New rates (previous in brackets) -     Average CD rates all banks 4.13 pct (4.33)     Money Market Certificate (MMC) ceiling rates for week starting from April 13 -       3.38 pct (3.58)     Average CD rates of city, trust and long-term banks -     Less than 60 days          4.15 pct (4.41)     60-90 days                 4.14 pct (4.29)     Average CD rates of city, trust and long-term banks -     90-120 days                4.12 pct (4.25)     120-150 days               4.12 pct (4.23)     150-180 days               unquoted (4.03)     180-270 days               4.05 pct (4.05)     Over 270 days              4.05 pct (unqtd)     Average yen bankers acceptance rates of city, trust and long-term banks -     30 to less than 60 days    3.98 pct (4.20)     60-90 days                 4.03 pct (3.97)     90-120 days                unquoted (unqtd)  REUTER \n");
        out.write("TEST; 12-APR-1987 22:51:00.04;    WASHINGTON, April 12 - ;JAPAN HAS NO PLANS TO CUT DISCOUNT RATE;  Bank of Japan sources said the bank has no plans to cut its discount rate.     They told reporters that there was no pressure on Japan during the Group of Seven (G-7) meeting here yesterday to lower its discount rate. They added that they themselves do not feel any need for a cut at all.     Chancellor of the Exchequer Nigel Lawson told reporters earlier today that some countries - those with strong currencies - might have to cut interest rates.     The Bank of Japan sources also said that it was too soon to call the G-7 pact a failure.     The central bank sources were commenting on the dollar's renewed tumble in New York and Tokyo, which was sparked by remarks by U.S. Treasury Secretary James Baker that the dollar's fall had been orderly.     They said the market must have misinterpreted Baker's comments because he was referring to the dollar's fall since the Plaza agreement in September 1985, over a long-time span, not the currency's recent movements.     They added that the foreign exchange markest seem to seize on anything to use as an excuse to drive the dollar one way or the other.     The Bank of Japan sources said the U.S. Is putting more weight on the dollar/yen rate in terms of judging market stability than on other currencies.     Throughout the G-7 meeting, Japan pointed to the dangers that would arise from a further dollar fall because it would reduce the flow of Japanese capital to the U.S., Hurting the U.S. And world economies, they said.     In February and in March of this year, Japanese investors reduced their purchases of U.S. Treasury bonds, the sources said.     Each country in the G-7 - Britain, Canada, France, Italy, Japan, the U.S. And West Germany - has a different view about currency stability, the Bank of Japan sources said.     This is because the overall foreign exchange market is a triangle of dollar/yen, European currencies/yen and dollar/European currencies.     At the time of the Louvre agreement, European countries did not want the yen to weaken against their currencies so they did not object to the yen strengthening, they said.  REUTER \n");
        out.write("TEST; 13-APR-1987 03:11:28.04;    ABU DHABI, April 13 - ;UAE CENTRAL BANK CD YIELDS RISE;  Yields on certificates of deposit (CD) offered by the United Arab Emirates Central Bank were higher than last Monday's offering, the bank said.     The one-month CD rose 1/4 point to 6-3/8 pct, while the two, three and six-month maturities rose 5/16 point each to 6-7/16, 6-1/2 and 6-5/8 pct respectively.  REUTER \n");
        out.write("TEST; 13-APR-1987 03:14:22.80;    HONG KONG, April 13 - ;CHINESE HOTEL RAISES 21 MLN DLR LOAN;  A tourist hotel in Suzhou, Jiangsu province, is raising a 21 mln U.S. Dlr loan to cover its construction cost, lead manager <DnC Ltd> said.     The loan for the Suzhou Aster Hotel will mature in 10 years and is guaranteed by the provincial investment arm <Jiangsu International Trust and Investment Corp>. The terms were not revealed. The loan, to be signed by the end of the month, will be provided by a number of banks on a club basis.     The hotel is being developed on a contractual joint venture basis between an unnamed Hong Kong Chinese investor and Suzhou municipal entities.  REUTER \n");
        out.close();

        ArrayList<String> ppNames = new ArrayList<>();
        ArrayList<PreProcessor> pps = new ArrayList<>();

        ppNames.add("");
        pps.add(new SimplePreprocessor());

        ppNames.add("simple");
        pps.add(new SimplePreprocessor());

        try {
            pps.add(new SnowballPreprocessor());
            ppNames.add("snowball");
        } catch (StemmerCreationException e) { }

        ui = new CommandLineInterface();
        ui.setupPreprocessors(pps, ppNames);
    }

    @Test
    public void giganticTestThatAlsoTestsCorpusReaderSomewhat(){
        systemInMock.provideText(""
                + "d-MMM-yyyy\n"
                + "snowball\n"
                + "English\n"
                + testFile.getAbsolutePath()+"\n"
                + "daily\n"
                + "single\n"
                + "YUGOSLAV WORKERS MAY BE ANGERED BY LOST SUBSIDIES  Yugoslav government plans to stop subsidising loss-making firms will anger hundreds of thousands of workers, Western diplomats said.     The law, proposed by Prime Minister Branko Mikulic, goes into effect on July 1 and aims to end a long-standing practice of supporting unprofitable companies. Under the law, wage cuts will be imposed on losing enterprises, while those failing to recover within a six-month grace period will face liquidation.     The diplomats said Mikulic's attempt to create a market economy is inevitable, but has still come as a shock to those accustomed to government subsidies.     \"It was a bitter pill which had to be swallowed, but if an overdose is taken too abruptly, it may have adverse effects on the system,\" a Western diplomat said.     He said if the law was applied too strictly it would probably provoke a new wave of strikes and unrest.     Yugoslavia was swept by strikes last month following the introduction of a wage-freeze law, later amended to allow more flexibility and some exemptions in what some political analysts saw as a retreat by Mikulic.     But with inflation moving towards 100 pct, trade union leaders have asked how much more deprivation workers can take.     The union leaders said workers thoughout the country are already receiving salaries below limits set under existing law, while others have received no wages at all this year because their employers are unable to pay them.     Workers also complain much of their income is taken in local, state and federal taxes.     Many others are losing their motivation to work and confidence in government as they feel their decision-making powers are being eroded, trade union officials said.     Meanwhile, the official Tanjug news agency reported a paper and cellulose factory at Ivangrad in the Montenegro republic closed yesterday and 2,000 of its workers were given \"temporary leave.\"     Tanjug said the plant had been running at a loss for the 24 years it had been in operation, and its closure was the result of \"economic necessity\" rather than bankruptcy.  REUTER \n"
                + "help\n"
                + "random\n"
                + "quit\n");

        ui.run();

        String out = log.getLog();
        assertTrue(out.contains("Set date format:"));
        assertTrue(out.contains("Set preprocessor"));
        assertTrue(out.contains("simple"));
        assertTrue(out.contains("snowball"));
        assertTrue(out.contains("Set language:"));
        assertTrue(out.contains("Language set to English"));
        assertTrue(out.contains("Set path to reference corpus"));
        assertTrue(out.contains("Processing reference corpus (this might take long) ..."));
        assertTrue(out.contains("Done processing reference corpus."));
        assertTrue(out.contains("Set time partition size"));
        assertTrue(out.contains("daily"));
        assertTrue(out.contains("Getting time partitions for the reference corpus spanning 8-Apr-1987 to 13-Apr-1987"));
        assertTrue(out.contains("\t8-Apr-1987 - 8-Apr-1987 (2 documents)\n\t9-Apr-1987 - 9-Apr-1987 (1 documents)\n\t10-Apr-1987 - 10-Apr-1987 (2 documents)\n\t11-Apr-1987 - 11-Apr-1987 (2 documents)\n\t12-Apr-1987 - 12-Apr-1987 (2 documents)\n\t13-Apr-1987 - 13-Apr-1987 (2 documents)"));
        assertTrue(out.contains("Done building time partitions."));
        assertTrue(out.contains("Known commands:"));
        assertTrue(out.contains("random -- Processes a random document from the Reference corpus."));
        assertTrue(out.contains("single -- Input custom text for processing."));
        assertTrue(out.contains("quit   -- Quits the application."));
        assertTrue(out.contains("help   -- Shows this help."));
        assertTrue(out.contains("Input text body:"));
        assertTrue(out.contains("Processed text:"));
        assertTrue(out.contains("Random document"));
        assertTrue(out.contains("Actual date"));
    }

}
