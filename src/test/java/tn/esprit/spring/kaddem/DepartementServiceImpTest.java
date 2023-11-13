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
import tn.esprit.spring.kaddem.controllers.DepartementRestController;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;
import tn.esprit.spring.kaddem.services.IDepartementService;

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

public class DepartementServiceImpTest {
    @InjectMocks
    private DepartementRestController departementRestController;
    @Mock
    private IDepartementService departementService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private DepartementServiceImpl ds;

    @Test
    void testUpdateDepartement() {
        // Create a university and save it
        Departement departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Informatique");

        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement savedDepartement = ds.addDepartement(departement);

        // Make changes to the university
        savedDepartement.setNomDepart("Mecanique");

        // Mock the behavior of universityRepository.save to return the updated University
        when(departementRepository.save(any(Departement.class))).thenReturn(savedDepartement);

        // Update the University
        Departement updatedDepartement = ds.updateDepartement(savedDepartement);

        // Assertions for the updated University
        Assertions.assertNotNull(updatedDepartement);
        Assertions.assertEquals(1, updatedDepartement.getIdDepart());
        verify(departementRepository, times(2)).save(any(Departement.class));
    }


    @Test

    void testDeleteUniversite() {
        // Create an Etudiant
        Departement departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("esprit");

        // Mock the behavior of etudiantRepository.findById to return the Etudiant
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Mock the behavior of etudiantRepository.delete to delete the Etudiant
        doNothing().when(departementRepository).delete(departement);

        // Call the removeEtudiant method
        ds.deleteDepartement(1);


        // Assertions for the removeEtudiant result
        verify(departementRepository).findById(1); // Verify that findById was called
        verify(departementRepository).delete(departement); // Verify that delete was called
        Optional<Departement> removedDepartement = departementRepository.findById(1);
        // Assertions for the removed Etudiant
        assertTrue(removedDepartement.isPresent());
    }
    @Test
    void testAddDepartement() {
        Departement departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("esprit");
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement savedDepartement = ds.addDepartement(departement);

        Assertions.assertNotNull(savedDepartement);
        Assertions.assertEquals(1, savedDepartement.getIdDepart());
        Assertions.assertEquals("esprit", savedDepartement.getNomDepart());
        verify(departementRepository).save(any(Departement.class));
    }
    @Test
    void testRetrieveUniversite() {
        Departement departement = new Departement();
        Mockito.when(departementRepository.findById(Mockito.any())).thenReturn(Optional.of(departement));
        Departement departement1 = ds.retrieveDepartement(2);
        Assertions.assertNotNull(departement1);
    }
    @Test
    void testRetrieveAllUniversites() {
        ArrayList<Departement> departementList = new ArrayList<>();
        when(departementRepository.findAll()).thenReturn(departementList);
        List<Departement> actualRetrieveAllDepartementsResult = ds.retrieveAllDepartements();
        assertSame(departementList, actualRetrieveAllDepartementsResult);
        assertTrue(actualRetrieveAllDepartementsResult.isEmpty());
        verify(departementRepository).findAll();
    }
}
