package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostControlTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(
                postService, cityService
        );
        String page = postControl.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post");
        City city = new City(1, "new city");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(
                postService, cityService
        );
        String page = postControl.createPost(input, city.getId());
        verify(postService).add(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFormAddPost() {
        List<City> cities = Arrays.asList(
                new City(1, "New city"),
                new City(2, "New city")
        );
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        PostControl postControl = new PostControl(
                postService, cityService
        );
        String page = postControl.addPost(model, session);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenUpdatePost() {
        Post in = new Post(1, "new Post");
        Post update = new Post(2, "Update post");
        City city = new City(1, "new city");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(
                postService, cityService
        );
        postControl.createPost(in, city.getId());
        String page = postControl.updatePost(update, city.getId());
        verify(postService).update(update);
        assertThat(page, is("redirect:/posts"));
    }
}