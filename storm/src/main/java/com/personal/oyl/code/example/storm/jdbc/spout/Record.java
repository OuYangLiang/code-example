package com.personal.oyl.code.example.storm.jdbc.spout;

public class Record {
    private Long id;
    private String desc;

    public Record(Long id, String desc) {
        super();
        this.id = id;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
