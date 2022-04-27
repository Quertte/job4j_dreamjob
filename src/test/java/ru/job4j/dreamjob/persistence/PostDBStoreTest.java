package ru.job4j.dreamjob.persistence;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PostDBStoreTest {

    private static PostDBStore store;

    @BeforeClass
    public static void init() {
        store = new PostDBStore(new Main().loadPool());
    }

    @After
    public void wipeTable() throws SQLException {
        store.clearTable();
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(
                0,
                "Java Job",
                new City(1, "Москва"),
                true,
                "remote");
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenFindAllPosts() {
        Post post = new Post(
                0,
                "Java Job",
                new City(1, "Москва"),
                true,
                "remote");
        Post post1 = new Post(
                1,
                "Javascript Job",
                new City(2, "Санкт-Петерубрг"),
                true,
                "офис");
        store.add(post);
        store.add(post1);
        Collection<Post> posts = store.findAll();
        Collection<Post> expected = List.of(post, post1);
        assertThat(posts, is(expected));
    }

    @Test
    public void whenFindById() {
        Post post = new Post(
                0,
                "Java Job",
                new City(1, "Москва"),
                true,
                "remote");
        Post post1 = new Post(
                1,
                "Javascript Job",
                new City(2, "Санкт-Петерубрг"),
                true,
                "офис");
        store.add(post);
        store.add(post1);
        int id = post.getId();
        assertThat(store.findById(id), is(post));
    }

    @Test
    public void whenUpdate() {
        Post post = new Post(1,
                "Java Developer",
                new City(1, "Москва"));
        store.add(post);
        Post post1 = new Post(post.getId(),
                "Frontend Developer",
                new City(2, "Санкт-Петербург"));
        store.update(post1);
        assertThat(store.findById(post.getId()).getName(), is("Frontend Developer"));
    }
}