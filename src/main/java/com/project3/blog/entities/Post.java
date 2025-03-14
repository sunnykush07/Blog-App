package com.project3.blog.entities;

import java.util.Date;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name="title",nullable = false)
	private String title;
	
	@Column(length = 1000)
	private String content;
	
	@Column(name="image")
	private String imageName;
	
	@Column(name="date",nullable = false)
	private Date postDate;
	
	@ManyToOne
	private User user;
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
}
