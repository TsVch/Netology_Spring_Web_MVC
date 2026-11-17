package ru.netology.servlets.repository;

import org.springframework.stereotype.Repository;
import ru.netology.servlets.model.Post;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PostRepository {

    private final Map<Long, Post> posts = new LinkedHashMap<>();
    private long nextId = 1;

    // Получить все неудалённые записи
    public synchronized List<Post> findAll() {
        List<Post> result = new ArrayList<>();

        for (Post post : posts.values()) {
            if (!post.isRemoved()) {
                result.add(copy(post));
            }
        }

        return result;
    }

    // Найти запись по ID
    public synchronized Optional<Post> findById(long id) {
        Post post = posts.get(id);

        if (post == null || post.isRemoved()) {
            return Optional.empty();
        }

        return Optional.of(copy(post));
    }

    // Сохранить новую или существующую запись
    public synchronized Post save(Post post) {
        if (post.getId() == null) {
            post.setId(nextId++);
        }

        Post savedPost = copy(post);
        posts.put(savedPost.getId(), savedPost);

        return copy(savedPost);
    }

    // Логически удалить запись
    public synchronized void removeById(long id) {
        Post post = posts.get(id);

        if (post != null) {
            post.setRemoved(true);
        }
    }

    // Создать копию объекта
    private Post copy(Post post) {
        Post copy = new Post(post.getId(), post.getContent());
        copy.setRemoved(post.isRemoved());
        return copy;
    }
}