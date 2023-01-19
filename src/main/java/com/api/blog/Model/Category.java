package com.api.blog.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name="category_table")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cat_id")
	private Long categoryId;
	@Column(name="cat_title",nullable = false)
	private String categoryTitle;
	@Column(name="cat_description")
	private String categoryDescription;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy = "category")
	@JsonManagedReference(value = "category_table")
	private List<Post> post;
	

}
