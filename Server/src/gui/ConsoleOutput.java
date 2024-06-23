package gui;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleOutput extends PrintStream {
    private TextArea consoleArea;

    public ConsoleOutput(TextArea consoleArea) {
        super(new ConsoleOutputStream(consoleArea));
        this.consoleArea = consoleArea;
    }
    private static class ConsoleOutputStream extends OutputStream {
        private TextArea consoleArea;

        public ConsoleOutputStream(TextArea consoleArea) {
            this.consoleArea = consoleArea;
        }
        @Override
        public void write(int b) {
            Platform.runLater(() -> consoleArea.appendText(String.valueOf((char) b)));
        }
    }
}
