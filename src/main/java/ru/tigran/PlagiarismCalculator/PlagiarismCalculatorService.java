package ru.tigran.PlagiarismCalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class PlagiarismCalculatorService {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(PlagiarismCalculatorService.class, args);

        Map<String, String> argsParsed = new HashMap<>();
        for (String arg : args) {
            if (arg.contains("=")) {
                String[] argSplitted = arg.split("=");
                argsParsed.put(argSplitted[0], argSplitted[1]);
            }
        }
        File file1 = new File(argsParsed.get("--file1"));
        File file2 = new File(argsParsed.get("--file2"));
        CPP14AnalyzeWorker.fromFiles(file1, file2).run();
    }
}
