package ru.job4j.dreamjob.persistence;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CandidateStore {

    private final AtomicInteger id = new AtomicInteger(3);
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Andrew"));
        candidates.put(2, new Candidate(2, "Petr"));
        candidates.put(3, new Candidate(3, "Stas"));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(id.incrementAndGet());
        candidates.putIfAbsent(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
