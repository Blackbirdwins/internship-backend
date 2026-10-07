package com.internship.books_api.dto;

import jakarta.validation.constraints.*;

public class PublisherRequestDTO {

    @NotBlank(message = "Publisher name is required")
    @Size(min = 2, max = 100, message = "Publisher name must be between 2 and 100 characters")
    private String name;

    @Size(max = 50, message = "Country must be at most 50 characters")
    private String country;

    @Min(value = 1000, message = "Founded year must be after 1000")
    @Max(value = 2026, message = "Founded year cannot be in the future")
    private Integer foundedYear;

    public PublisherRequestDTO() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getFoundedYear() { return foundedYear; }
    public void setFoundedYear(Integer foundedYear) { this.foundedYear = foundedYear; }
}