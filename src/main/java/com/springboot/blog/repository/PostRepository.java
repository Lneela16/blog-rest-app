package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface PostRepository extends JpaRepository<Post, Long> {
}
