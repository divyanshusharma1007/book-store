package com.projects.book.store.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookDTO {

    @JsonManagedReference
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    private String author;

    @Positive(message = "Price must be positive")
    private double price;

    @NotNull(message = "Publication year cannot be null")
    private Integer publicationYear;

    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(regexp = "\\d{3}-\\d{10}", message = "ISBN must be in the format XXX-XXXXXXXXXX")
    private String isbn;
}