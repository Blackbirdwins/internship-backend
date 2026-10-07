package com.internship.books_api.dto;

public class PublisherResponseDTO {

    private Long id;
    private String name;
    private String country;
    private Integer foundedYear;

    public PublisherResponseDTO() {
    }

    public PublisherResponseDTO(Long id, String name, String country, Integer foundedYear) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.foundedYear = foundedYear;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getFoundedYear() { return foundedYear; }
    public void setFoundedYear(Integer foundedYear) { this.foundedYear = foundedYear; }
}