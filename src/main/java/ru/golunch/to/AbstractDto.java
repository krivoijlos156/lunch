package ru.golunch.to;

public class AbstractDto {
    private Integer id;

    public AbstractDto() {
    }

    public AbstractDto(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
