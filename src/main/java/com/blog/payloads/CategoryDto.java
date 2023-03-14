package com.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	@NotBlank
	@Size(min = 4, message = "Minimum size of Title must be 4 chars")
	private String categoryTitle;
	@NotBlank
	@Size(min=10, message = "Minimum size of description must be 20 chars")
	private String categoryDescription;
}
