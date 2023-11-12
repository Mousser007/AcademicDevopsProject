package tn.esprit.spring.kaddem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.spring.kaddem.controllers.EtudiantRestController;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Option;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Slf4j
@Transactional
 class EtudiantServiceImpTest {
    @Mock
    IEtudiantService ies;
    @MockBean
    EtudiantRepository etudiantRepository;
    @Autowired
    EtudiantServiceImpl es;
    @InjectMocks
    private EtudiantRestController etudiantRestController;




//Mockito



    @Test
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("Selmi");
        etudiant.setPrenomE("Moussa");
        etudiant.setOp(Option.GAMIX);

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);


        Etudiant saveddEtudiant = es.addEtudiant(etudiant); // Use the mocked etudiantRepository here

        Assertions.assertNotNull(saveddEtudiant);
        Assertions.assertEquals(1, saveddEtudiant.getIdEtudiant());
        Assertions.assertEquals("Selmi", saveddEtudiant.getNomE());
        Assertions.assertEquals("Moussa", saveddEtudiant.getPrenomE());
        Assertions.assertEquals(Option.GAMIX, saveddEtudiant.getOp());

        verify(etudiantRepository).save(any(Etudiant.class));
    }

    @Test
    void testUpdateEtudiant() {
        // Create an Etudiant and save it
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("Selmi");
        etudiant.setPrenomE("Moussa");
        etudiant.setOp(Option.GAMIX);

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant savedEtudiant = es.addEtudiant(etudiant);

        // Make changes to the Etudiant
        savedEtudiant.setNomE("Updated Name");
        savedEtudiant.setPrenomE("Updated Prenom");
        savedEtudiant.setOp(Option.SE);

        // Mock the behavior of etudiantRepository.save to return the updated Etudiant
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(savedEtudiant);

        // Update the Etudiant
        Etudiant updatedEtudiant = es.updateEtudiant(savedEtudiant);

        // Assertions for the updated Etudiant
        Assertions.assertNotNull(updatedEtudiant);
        Assertions.assertEquals(1, updatedEtudiant.getIdEtudiant());
        Assertions.assertEquals("Updated Name", updatedEtudiant.getNomE());
        Assertions.assertEquals("Updated Prenom", updatedEtudiant.getPrenomE());
        Assertions.assertEquals(Option.SE, updatedEtudiant.getOp());

        verify(etudiantRepository, times(2)).save(any(Etudiant.class));
    }
    @Test
    void testRetrieveAllEtudiant() {
        // Create a list of Etudiant entities
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        etudiant1.setNomE("Selmi");
        etudiant1.setPrenomE("Moussa");
        etudiant1.setOp(Option.GAMIX);

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2);
        etudiant2.setNomE("Smith");
        etudiant2.setPrenomE("John");
        etudiant2.setOp(Option.SE);

        List<Etudiant> etudiantList = new ArrayList<>();
        etudiantList.add(etudiant1);
        etudiantList.add(etudiant2);

        // Mock the behavior of etudiantRepository.findAll to return the list of Etudiant entities
        when(etudiantRepository.findAll()).thenReturn(etudiantList);

        // Call the retrieveAllEtudiant method
        List<Etudiant> retrievedEtudiants = es.retrieveAllEtudiants();

        // Assertions for the retrieved Etudiant list
        Assertions.assertNotNull(retrievedEtudiants);
        Assertions.assertEquals(2, retrievedEtudiants.size()); // Check the size of the list
        Assertions.assertEquals(1, retrievedEtudiants.get(0).getIdEtudiant());
        Assertions.assertEquals(2, retrievedEtudiants.get(1).getIdEtudiant());

        // Add more assertions as needed to validate the retrieved data

        verify(etudiantRepository).findAll();
    }
    @Test
    void testRemoveEtudiant() {
        // Create an Etudiant
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("Selmi");
        etudiant.setPrenomE("Moussa");
        etudiant.setOp(Option.GAMIX);

        // Mock the behavior of etudiantRepository.findById to return the Etudiant
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));

        // Mock the behavior of etudiantRepository.delete to delete the Etudiant
        doNothing().when(etudiantRepository).delete(etudiant);

        // Call the removeEtudiant method
        es.removeEtudiant(1);


        // Assertions for the removeEtudiant result
        verify(etudiantRepository).findById(1); // Verify that findById was called
        verify(etudiantRepository).delete(etudiant); // Verify that delete was called
        Optional<Etudiant> removedEtudiant = etudiantRepository.findById(1);

        // Assertions for the removed Etudiant
        Assertions.assertTrue(removedEtudiant.isPresent());
    }
}




// Junit
/*    @Test
    @Order(1)
    public void TestaddEtudiant (){
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("Selmi");
        etudiant.setPrenomE("Moussa");
        Etudiant savedEtudiant=es.addEtudiant(etudiant);
        Assertions.assertNotNull(savedEtudiant);
        Assertions.assertEquals("Selmi",savedEtudiant.getNomE());
        Assertions.assertEquals("Moussa",savedEtudiant.getPrenomE());
    }

    @Test
    @Order(2)
    @Transactional
    public void TestretrieveAllEtudiants(){
    List<Etudiant> etudiantList=    (List<Etudiant>) etudiantRepository.findAll();
        Assertions.assertEquals(0, etudiantList.size());
    }
    @Test
    @Order(3)
    @Transactional
    public void TestupdateEtudiant (){
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("Selmi");
        etudiant.setPrenomE("Moussa");
        Etudiant savedEtudiant = es.addEtudiant(etudiant);
        Assertions.assertNotNull(savedEtudiant);

        savedEtudiant.setNomE("Selmiii");

        Etudiant updatedEtudiant = es.updateEtudiant(savedEtudiant);
        Assertions.assertNotNull(updatedEtudiant);

        Assertions.assertEquals("Selmiii", updatedEtudiant.getNomE());
        Assertions.assertEquals("Moussa", updatedEtudiant.getPrenomE());
    }
    @Test
    @Order(4)
    @Transactional
    public void TestremoveEtudiant(){
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("test");
        etudiant.setPrenomE("pp");

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        Assertions.assertNotNull(savedEtudiant);

        es.removeEtudiant(savedEtudiant.getIdEtudiant());

        Etudiant removedEtudiant = etudiantRepository.findById(savedEtudiant.getIdEtudiant()).orElse(null);
        Assertions.assertNull(removedEtudiant);
    }*/