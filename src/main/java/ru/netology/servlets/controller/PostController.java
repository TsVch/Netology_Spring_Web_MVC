package ru.netology.servlets.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.netology.servlets.model.Post;
import ru.netology.servlets.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    // GET /api/posts
    // Получить все записи
    @GetMapping
    public List<Post> getAll() {
        return service.getAll();
    }

    // GET /api/posts/{id}
    // Получить запись по ID
    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        return service.getById(id);
    }

    // POST /api/posts
    // Создать новую запись
    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        Post savedPost = service.save(post);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPost);
    }

    // PUT /api/posts/{id}
    // Обновить существующую запись
    @PutMapping("/{id}")
    public Post update(
            @PathVariable long id,
            @RequestBody Post post
    ) {
        return service.update(id, post);
    }

    // DELETE /api/posts/{id}
    // Логически удалить запись
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable long id) {
        service.removeById(id);

        return ResponseEntity.noContent().build();
    }
}