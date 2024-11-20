package org.player64.mariuszspetitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetitionService {
    private final PetitionRepository petitionRepository;

    @Autowired
    public PetitionService(PetitionRepository petitionRepository) {
        this.petitionRepository = petitionRepository;
    }

    public List<Petition> getAllPetitions() {
        return petitionRepository.getAll();
    }

    public Optional<Petition> getPetitionById(Long petitionId) {
        return petitionRepository.findById(petitionId);
    }

    public List<Petition> getPetitionByTitle(String title) {
        return petitionRepository.searchByTitle(title);
    }

    public boolean createPetition(Petition petition) {
        if(petitionRepository.existsByExactTitle(petition.getTitle())) {
            return false;
        }
        petitionRepository.saveOrUpdate(petition);
        return true;
    }

    public boolean signPetition(Long petitionId, User user) {
        Optional<Petition> petition = petitionRepository.findById(petitionId);

        if (petition.isPresent() && !isUserAlreadySigned(petition.get(), user.getEmail())) {
            petition.get().getSignUsers().add(user);
            petitionRepository.saveOrUpdate(petition.get());
            return true;
        }
        return false; // Return false if user has already signed or petition not found
    }

    private boolean isUserAlreadySigned(Petition petition, String email) {
        return petition.getSignUsers().stream()
                .anyMatch(existingUser -> existingUser.getEmail().equalsIgnoreCase(email));
    }
}
