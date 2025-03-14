package com.project3.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	private List<PostDto> contents;
	private int pageNumber;
	private int totalPages;
	private int pageSize;
	private long totalElements;
	private boolean lastPage;
}
