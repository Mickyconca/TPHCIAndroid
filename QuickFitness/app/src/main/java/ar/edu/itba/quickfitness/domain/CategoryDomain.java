package ar.edu.itba.quickfitness.domain;

public class CategoryDomain {

    private Integer id;
    private String name;
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public CategoryDomain(String name, String detail){
        this(0, name, detail);
    }

    public CategoryDomain(int id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        return this.getId().equals(((CategoryDomain) o).getId());
    }

    public String toConvert(){
        return id + "|" + name + "|" + detail;
    }
}
