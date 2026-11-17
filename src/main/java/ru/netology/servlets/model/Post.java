package ru.netology.servlets.model;

public class Post {

    private Long id;
    private String content;
    private boolean removed;

    public Post() {
    }

    public Post(Long id, String content) {
        this.id = id;
        this.content = content;
        this.removed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}