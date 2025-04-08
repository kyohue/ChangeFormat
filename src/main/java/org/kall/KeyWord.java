package org.kall;

public enum KeyWord {
    OutputFileDirectory(),
    InputFileDirectory(),
    PlateFilePath(),
    FileSuffix(),
    ExamPaperYear(),
    ExamPaperName(),
    RegPlateExamPart(),
    ExamClassification(),
    ExamClassificationNextLevel();

    private String name;
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
}
