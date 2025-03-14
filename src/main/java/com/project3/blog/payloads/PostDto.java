package com.project3.blog.payloads;

import java.util.Date;

import com.project3.blog.entities.Category;
import com.project3.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
	
	private String imageName;
	private Date postDate;
	private CategoryDto category;
	private UserDto user;
}
