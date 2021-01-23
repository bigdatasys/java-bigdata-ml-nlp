package nlptasks;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

import java.io.*;

public class OpenNLP {

    private static final String TEST_TEXT = "Sentiment analysis is also known as opinion mining or emotion AI. There are many challenges in natural language processing.";

    public static void SentenceDetection() throws InvalidFormatException, IOException {
        String paragraph = TEST_TEXT;
        // starting with a model, a model is learned from training data
        InputStream is = new FileInputStream("./opennlp/en-sent.bin");
        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sentencedetection = new SentenceDetectorME(model);
        String sentences[] = sentencedetection.sentDetect(paragraph);
        int progress = 0;
        for (int i = 0; i < sentences.length; i++) {
            System.out.println(sentences[i]);
        }
        is.close();
    }
    public static void Tokenize() throws InvalidFormatException, IOException {
        InputStream is = new FileInputStream("./opennlp/en-token.bin");
        TokenizerModel model = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(model);
        String tokens[] = tokenizer.tokenize(TEST_TEXT);
        for (String a : tokens)
            System.out.println(a);
        is.close();
    }
    public static void findName() throws IOException {
        InputStream is = new FileInputStream("./opennlp/en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(is);
        is.close();
        NameFinderME nameFinder = new NameFinderME(model);
        String []sentence = new String[]{"Sentiment", "analysis", "is", "also", "known", "as", "opinion", "mining", "or", "emotion", "AI."};
        Span nameSpans[] = nameFinder.find(sentence);
        for(Span s: nameSpans)
            System.out.println(s.toString());
    }
    public static void POSTag() throws IOException {
        POSModel posmodel = new POSModelLoader()
                .load(new File("./opennlp/en-pos-maxent.bin"));
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(posmodel);
        String input = TEST_TEXT;
        ObjectStream<String> lineStream = new PlainTextByLineStream(
                new StringReader(input));
        perfMon.start();
        String line;
        while ((line = lineStream.read()) != null) {
            String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            String[] tags = tagger.tag(whitespaceTokenizerLine);
            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            System.out.println(sample.toString());
            perfMon.incrementCounter();
        }
        perfMon.stopAndPrintFinalResult();
    }
    public static void chunk() throws IOException {
        POSModel posmodel = new POSModelLoader()
                .load(new File("./opennlp/en-pos-maxent.bin"));
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(posmodel);
        String input = TEST_TEXT;
        ObjectStream<String> lineStream = new PlainTextByLineStream(
                new StringReader(input));
        perfMon.start();
        String line;
        String whitespaceTokenizerLine[] = null;
        String[] tags = null;
        while ((line = lineStream.read()) != null) {
            whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            tags = tagger.tag(whitespaceTokenizerLine);
            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            System.out.println(sample.toString());
            perfMon.incrementCounter();
        }
        perfMon.stopAndPrintFinalResult();
        // chunker
        InputStream is = new FileInputStream("./opennlp/en-chunker.bin");
        ChunkerModel cModel = new ChunkerModel(is);
        ChunkerME chunkerME = new ChunkerME(cModel);
        String result[] = chunkerME.chunk(whitespaceTokenizerLine, tags);
        for (String s : result)
            System.out.println(s);
        Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
        for (Span s : span)
            System.out.println(s.toString());
    }
    public static void Parse() throws InvalidFormatException, IOException {
        InputStream is = new FileInputStream("./opennlp/en-parser-chunking.bin");
        ParserModel model = new ParserModel(is);
        Parser parser = ParserFactory.create(model);
        String sentence = TEST_TEXT;
        Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
        for (Parse p : topParses)
            p.show();
        is.close();
    }

}
