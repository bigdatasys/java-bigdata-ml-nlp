package nlptasks;

import java.io.IOException;

import static nlptasks.OpenNLP.*;

public class NLPTasksAPP {
    public static void main(String[] args) {
        try { SentenceDetection(); } catch (IOException ioException) { ioException.printStackTrace(); }
        try { Tokenize(); } catch (IOException ioException) { ioException.printStackTrace(); }
        try { findName(); } catch (IOException ioException) { ioException.printStackTrace(); }
        try { POSTag(); } catch (IOException ioException) { ioException.printStackTrace(); }
        try { chunk(); } catch (IOException ioException) { ioException.printStackTrace(); }
        try { Parse(); } catch (IOException ioException) { ioException.printStackTrace(); }
    }
}
