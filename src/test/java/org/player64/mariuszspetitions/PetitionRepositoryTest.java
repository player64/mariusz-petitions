package org.player64.mariuszspetitions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class PetitionRepositoryTest {
    private PetitionRepository repository;


    @BeforeEach
    void setUp() {
        repository = new PetitionRepository();
    }

    @Test
    void getAllReturnsNotEmptyList() {
        assertFalse(repository.getAll().isEmpty());
    }

    @Test
    void getAllOnInitReturnsExpectedSize() {
        assertEquals(10, repository.getAll().size());
    }

    @Test
    void saveOrUpdateAddsNewPetitionWhenIdIsNull() {
        Petition petition = new Petition("New Petition", new User("Alice", "alice@example.com"));
        Petition savedPetition = repository.saveOrUpdate(petition);
        assertNotNull(savedPetition.getId());
        assertTrue(repository.getAll().contains(savedPetition));
    }

    @Test
    void saveOrUpdateUpdatesExistingPetitionWhenIdIsNotNull() {
        Petition petition = repository.getAll().get(0);
        petition.setTitle("Updated Title");
        Petition updatedPetition = repository.saveOrUpdate(petition);
        assertEquals("Updated Title", updatedPetition.getTitle());
        assertTrue(repository.getAll().contains(updatedPetition));
    }

    @Test
    void deleteByIdRemovesPetition() {
        Petition petition = repository.getAll().get(0);
        repository.deleteById(petition.getId());
        assertFalse(repository.getAll().contains(petition));
    }

    @Test
    void saveOrUpdateHandlesNullPetition() {
        assertThrows(NullPointerException.class, () -> repository.saveOrUpdate(null));
    }

    @Test
    void deleteByIdHandlesNonExistentId() {
        int initialSize = repository.getAll().size();
        repository.deleteById(-1L);
        assertEquals(initialSize, repository.getAll().size());
    }

    @Test
    void deleteByIdRemovesPetitionWhenIdExists() {
        Petition petition = repository.getAll().get(0);
        repository.deleteById(petition.getId());
        assertFalse(repository.getAll().contains(petition));
    }

    @Test
    void deleteByIdDoesNothingWhenIdDoesNotExist() {
        int initialSize = repository.getAll().size();
        repository.deleteById(-1L);
        assertEquals(initialSize, repository.getAll().size());
    }

    @Test
    void findByIdReturnsPetitionWhenIdExists() {
        Petition petition = repository.getAll().get(0);
        Optional<Petition> foundPetition = repository.findById(petition.getId());
        assertTrue(foundPetition.isPresent());
        assertEquals(petition, foundPetition.get());
    }

    @Test
    void findByIdReturnsEmptyWhenIdDoesNotExist() {
        Optional<Petition> foundPetition = repository.findById(-1L);
        assertFalse(foundPetition.isPresent());
    }

    @Test
    void searchByTitleReturnsPetitionWhenTitleExists() {
        Petition petition = repository.getAll().get(0);
        List<Petition> foundPetitions = repository.searchByTitle(petition.getTitle());
        assertFalse(foundPetitions.isEmpty());
        assertTrue(foundPetitions.contains(petition));
    }

    @Test
    void searchByTitleReturnsEmptyWhenTitleDoesNotExist() {
        List<Petition> foundPetitions = repository.searchByTitle("Nonexistent Petition");
        assertTrue(foundPetitions.isEmpty());
    }

    @Test
    void searchByTitleIsCaseInsensitive() {
        Petition petition = repository.getAll().get(0);
        List<Petition> foundPetitions = repository.searchByTitle(petition.getTitle().toUpperCase());
        assertFalse(foundPetitions.isEmpty());
        assertTrue(foundPetitions.contains(petition));
    }

    @Test
    void existsByTitleReturnsWhenTitleProvidedInVariousOptions() {
        Petition petition = new Petition("New Petition", new User("Alice", "alice@example.com"));
        repository.saveOrUpdate(petition);
        assertFalse(repository.existsByExactTitle("new"));
        assertFalse(repository.existsByExactTitle("Petition"));
        assertTrue(repository.existsByExactTitle("new petition"));
    }

    @Test
    void searchByTitleReturnsAllMatchesWhenMultipleTitlesStartWithSameString() {
        Petition petition1 = new Petition("Title1", new User("User1", "user1@example.com"));
        Petition petition2 = new Petition("Title2", new User("User2", "user2@example.com"));
        repository.saveOrUpdate(petition1);
        repository.saveOrUpdate(petition2);
        List<Petition> foundPetitions = repository.searchByTitle("Title");
        assertTrue(foundPetitions.contains(petition1));
        assertTrue(foundPetitions.contains(petition2));
    }

    @Test
    public void testSearchByTitle_Found() {
        List<Petition> result = repository.searchByTitle("student study");
        assertEquals(1, result.size());
        assertEquals("Increase Student Study Spaces on Campus", result.get(0).getTitle());
    }

    @Test
    public void testSearchByTitle_NotFound() {
        List<Petition> result = repository.searchByTitle("nonexistent");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchByTitle_MixedCaseInput() {
        Petition petition1 = new Petition("Clean Air Act", new User("User1", "user1@example.com"));
        repository.saveOrUpdate(petition1);
        List<Petition> result = repository.searchByTitle("aiR AC");
        assertEquals(1, result.size());
        assertEquals("Clean Air Act", result.get(0).getTitle());
    }

    @Test
    public void testSearchByTitlePartialMatch() {
        Petition petition1 = new Petition("Renewable Energy NOW", new User("User1", "user1@example.com"));
        repository.saveOrUpdate(petition1);
        List<Petition> result = repository.searchByTitle("energy");
        assertEquals(1, result.size());
        assertEquals("Renewable Energy NOW", result.get(0).getTitle());
    }

    @Test
    void signPetitionAddsUserToSignUsersWhenIdExists() {
        Petition petition = repository.getAll().get(0);
        User user = new User("New User", "new.user@example.com");
        repository.signPetition(petition.getId(), user);
        assertTrue(petition.getSignUsers().contains(user));
    }

    @Test
    void signPetitionDoesNothingWhenIdDoesNotExist() {
        User user = new User("New User", "new.user@example.com");
        int initialSize = repository.getAll().size();
        repository.signPetition(-1L, user);
        assertEquals(initialSize, repository.getAll().size());
    }
}
