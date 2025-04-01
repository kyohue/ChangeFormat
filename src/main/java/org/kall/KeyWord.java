package org.kall;

public enum KeyWord {
    OutputFileDirectory("/Users/kyohue/Desktop/"),
    InputFileDirectory("/Users/kyohue/Desktop/TASK/5、试题套模版/"),
    PlateFilePath("src/main/resources/plate.docx"),
    FileSuffix(".docx"),
    ExamPaperYear("2025"),
    ExamPaperName("test"),
    RegPlateExamPart("第NUM部分"),
    ExamClassification("计算机考研408/"),
    ExamClassificationNextLevel("自命题真题");

    private String name;
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    KeyWord(String name){
        this.name = name;
    }
}
