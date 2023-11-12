package tn.esprit.spring.kaddem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.kaddem.controllers.UniversiteRestController;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import tn.esprit.spring.kaddem.services.IUniversiteService;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
public class UniversiteServiceImplTest {
    @InjectMocks
    private UniversiteRestController universiteRestController;
    @Mock
    private IUniversiteService universiteService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Autowired
    private UniversiteRepository universiteRepository;
    @Autowired
    private UniversiteServiceImpl us;

    @Test
     void testUpdateUniversite() {
        // Create a university and save it
        Universite universite = new Universite();
        universite.setIdUniv(1);
        universite.setNomUniv("Esprit");

        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = us.addUniversite(universite);

        // Make changes to the university
        savedUniversite.setNomUniv("ISET RADES");

        // Mock the behavior of universityRepository.save to return the updated University
        when(universiteRepository.save(any(Universite.class))).thenReturn(savedUniversite);

        // Update the University
        Universite updatedUniversite = us.updateUniversite(savedUniversite);

        // Assertions for the updated University
        Assertions.assertNotNull(updatedUniversite);
        Assertions.assertEquals(1, updatedUniversite.getIdUniv());
        verify(universiteRepository, times(2)).save(any(Universite.class));
    }


    @Test

         void testDeleteUniversite() {
        // Create an Etudiant
        Universite universite = new Universite();
        universite.setIdUniv(1);
        universite.setNomUniv("esprit");

        // Mock the behavior of etudiantRepository.findById to return the Etudiant
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        // Mock the behavior of etudiantRepository.delete to delete the Etudiant
        doNothing().when(universiteRepository).delete(universite);

        // Call the removeEtudiant method
        us.deleteUniversite(1);


        // Assertions for the removeEtudiant result
        verify(universiteRepository).findById(1); // Verify that findById was called
        verify(universiteRepository).delete(universite); // Verify that delete was called
        Optional<Universite> removedUniversite = universiteRepository.findById(1);
        // Assertions for the removed Etudiant
        assertTrue(removedUniversite.isPresent());
    }
    @Test
     void testAddUniversite() {
        Universite universite = new Universite();
        universite.setIdUniv(1);
        universite.setNomUniv("esprit");
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = us.addUniversite(universite);

        Assertions.assertNotNull(savedUniversite);
        Assertions.assertEquals(1, savedUniversite.getIdUniv());
        Assertions.assertEquals("esprit", savedUniversite.getNomUniv());
        verify(universiteRepository).save(any(Universite.class));
    }
    @Test
     void testRetrieveUniversite() {
        Universite universite = new Universite();
        Mockito.when(universiteRepository.findById(Mockito.any())).thenReturn(Optional.of(universite));
        Universite universite1 = us.retrieveUniversite(2);
        Assertions.assertNotNull(universite1);
    }
    @Test
    void testRetrieveAllUniversites() {
        ArrayList<Universite> universiteList = new ArrayList<>();
        when(universiteRepository.findAll()).thenReturn(universiteList);
        List<Universite> actualRetrieveAllUniversitesResult = us.retrieveAllUniversites();
        assertSame(universiteList, actualRetrieveAllUniversitesResult);
        assertTrue(actualRetrieveAllUniversitesResult.isEmpty());
        verify(universiteRepository).findAll();
    }
}
