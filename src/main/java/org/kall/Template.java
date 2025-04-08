package org.kall;

public enum Template {
    Classification(),
    KnowledgeTree(),
    Difficulty(),
    Question(),
    OptionA(),
    OptionB(),
    OptionC(),
    OptionD(),
    AnswerOptionJudge(),
    AnswerOther(),
    Analysis();

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

}

