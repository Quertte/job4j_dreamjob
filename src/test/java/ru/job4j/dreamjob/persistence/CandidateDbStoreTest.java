package ru.job4j.dreamjob.persistence;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CandidateDbStoreTest {

    private static CandidateDbStore store;

    @BeforeClass
    public static void init() {
        store = new CandidateDbStore(new Main().loadPool());
    }

    @After
    public void wipeTable() throws SQLException {
        store.clearTable();
    }

    @Test
    public void whenCreateCandidate() {
        Candidate candidate = new Candidate(
                1, "Andrew"
        );
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenFindAllCandidate() {
        Candidate candidate = new Candidate(
                1, "Andrew"
        );
        Candidate candidate1 = new Candidate(
                2, "Vasiliy"
        );
        store.add(candidate);
        store.add(candidate1);
        Collection<Candidate> posts = store.findAll();
        Collection<Candidate> expected = List.of(candidate, candidate1);
        assertThat(posts, is(expected));
    }

    @Test
    public void whenFindById() {
        Candidate candidate = new Candidate(
                1, "Andrew"
        );
        Candidate candidate1 = new Candidate(
                2, "Vasiliy"
        );
        store.add(candidate);
        store.add(candidate1);
        int id = candidate.getId();
        assertThat(store.findById(id), is(candidate));
    }

    @Test
    public void whenUpdate() {
        Candidate candidate = new Candidate(
                1, "Andrew"
        );
        store.add(candidate);
        Candidate candidate1 = new Candidate(
                candidate.getId(), "Vasiliy"
        );
        store.update(candidate1);
        assertThat(store.findById(candidate.getId()).getName(), is("Vasiliy"));
    }
}