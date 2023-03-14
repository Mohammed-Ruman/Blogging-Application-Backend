package com.blog.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(name = "Likescounter")
public class LikesCount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
	private String likeUsername;
	
	private int userid;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;
	
	

	
}
