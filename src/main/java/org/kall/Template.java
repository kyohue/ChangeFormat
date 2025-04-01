package org.kall;

public enum Template {
    Classification("*分类*"),
    KnowledgeTree("*知识树（选填）*"),
    Difficulty("*题目难度（选填）*"),
    Question("*题干*"),
    OptionA("*选项A*"),
    OptionB("*选项B*"),
    OptionC("*选项C*"),
    OptionD("*选项D*"),
    AnswerOptionJudge("*正确答案*"),
    AnswerOther("*正确答案（选填）*"),
    Analysis("*解析（选填）*");


    private final String name;
    public String getName() {
        return name;
    }

    Template(String name) {
        this.name = name;
    }

}

