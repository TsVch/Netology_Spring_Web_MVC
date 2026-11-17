package ru.netology.servlets.service;

import org.springframework.stereotype.Service;
import ru.netology.servlets.exception.InvalidPostException;
import ru.netology.servlets.exception.PostNotFoundException;
import ru.netology.servlets.model.Post;
import ru.netology.servlets.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    // Получить все неудалённые записи
    public List<Post> getAll() {
        return repository.findAll();
    }

    // Получить запись по ID
    public Post getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    // Создать новую запись
    public Post save(Post post) {
        validate(post);

        // Новая запись всегда получает новый ID
        post.setId(null);
        post.setRemoved(false);

        return repository.save(post);
    }

    // Обновить существующую запись
    public Post update(long id, Post post) {
        // Проверяем, что запись существует и не удалена
        Post existingPost = getById(id);

        validate(post);

        existingPost.setContent(post.getContent());

        return repository.save(existingPost);
    }

    // Логически удалить запись
    public void removeById(long id) {
        // Не разрешаем удалять несуществующую запись
        getById(id);

        repository.removeById(id);
    }

    // Проверка содержимого записи
    private void validate(Post post) {
        if (post == null) {
            throw new InvalidPostException("Post cannot be null");
        }

        if (post.getContent() == null || post.getContent().isBlank()) {
            throw new InvalidPostException("Post content cannot be empty");
        }
    }
}