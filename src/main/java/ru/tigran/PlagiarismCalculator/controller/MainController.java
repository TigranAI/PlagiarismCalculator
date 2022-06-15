package ru.tigran.PlagiarismCalculator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plagiarism")
public class MainController {
    @RequestMapping("/analyze/cpp")
    public HttpStatus getAnalyzeCpp(int taskId, String solutionId) {
        return HttpStatus.OK;
    }
    @RequestMapping("/analyze/java")
    public HttpStatus getAnalyzeJava(int taskId, String solutionId) {
        return HttpStatus.OK;
    }
    @RequestMapping("/analyze/csharp")
    public HttpStatus getAnalyzeCsharp(int taskId, String solutionId) {
        return HttpStatus.OK;
    }
}
