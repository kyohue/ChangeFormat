package org.kall;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = KeyWord.OutputFileDirectory.getName()+KeyWord.ExamPaperName.getName()+KeyWord.FileSuffix.getName();
        ProcessDocx processDocx = new ProcessDocx();
        List<Object> plateContent = processDocx.readDocx(KeyWord.PlateFilePath.getName());
        processDocx.writeDocx(fileName, processDocx.convert(null,plateContent));
    }
}