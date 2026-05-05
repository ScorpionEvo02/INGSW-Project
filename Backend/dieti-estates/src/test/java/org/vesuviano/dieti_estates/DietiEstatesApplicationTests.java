package org.vesuviano.dieti_estates;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.vesuviano.dieti_estates.controller.AmministratoreController;
import org.vesuviano.dieti_estates.controller.OffertaController;
import org.vesuviano.dieti_estates.controller.SessioneController;

@SpringBootTest
class DietiEstatesApplicationTests {

	AmministratoreController adminController = new AmministratoreController();
	OffertaController offertaController = new OffertaController();
	
	
	@SuppressWarnings("deprecation")
	@Test
	void testChangePassword_ValidInput() {
	    // Arrange
	    int codAmministratore = 123;
	    String newPassword = "Password123";

	    // Act
	    ResponseEntity<String> response = adminController.changePassword("validSessionId", codAmministratore, newPassword);

	    // Assert
	    assertEquals(200, response.getStatusCodeValue(), "CT1: Password cambiata correttamente");
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	void testChangePassword_InvalidSession() {
	    // Arrange
	    String sessioneId = null;
	    int codAmministratore = 1;
	    String newPassword = "NuovaPassword123";

	    // Act
	    ResponseEntity<String> response =adminController.changePassword(sessioneId, codAmministratore, newPassword);

	    // Assert
	    assertEquals(400, response.getStatusCodeValue(), "CT2: Sessione non valida deve restituire errore");
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	void testChangePassword_NegativeAdminId() {
	    // Arrange
	    int codAmministratore = -1;
	    String newPassword = "Password123";

	    // Act
	    ResponseEntity<String> response = adminController.changePassword("validSessionId", codAmministratore, newPassword);

	    // Assert
	    assertEquals(400, response.getStatusCodeValue(), "CT3: Codice amministratore negativo deve restituire errore");
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	void testChangePassword_PasswordTooShort() {
	    // Arrange
	    int codAmministratore = 123;
	    String newPassword = "123";

	    // Act
	    ResponseEntity<String> response = adminController.changePassword("validSessionId", codAmministratore, newPassword);

	    // Assert
	    assertEquals(400, response.getStatusCodeValue(), "CT4: La password troppo corta deve restituire errore");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testInsertOffer_ValidInput() {
	    // Arrange
	    String sessioneId = "sessioneValida";
	    int codImmobile = 101;
	    int offerta = 80000;

	    // Act
	    ResponseEntity<String> response = offertaController.insertOffer(sessioneId, codImmobile, offerta);

	    // Assert
	    assertEquals(200, response.getStatusCodeValue(), "CT1: Offerta inserita correttamente");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testInsertOffer_InvalidSession() {
	    // Arrange
	    String sessioneId = null;
	    int codImmobile = 101;
	    int offerta = 80000;

	    // Act
	    ResponseEntity<String> response = offertaController.insertOffer(sessioneId, codImmobile, offerta);

	    // Assert
	    assertEquals(400, response.getStatusCodeValue(), "CT2: Sessione non valida deve restituire 401");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testInsertOffer_InvalidPropertyId() {
	    // Arrange
	    String sessioneId = "sessioneValida";
	    int codImmobile = 9999; // inesistente
	    int offerta = 80000;

	    // Act
	    ResponseEntity<String> response = offertaController.insertOffer(sessioneId, codImmobile, offerta);

	    // Assert
	    assertEquals(400, response.getStatusCodeValue(), "CT3: Codice immobile inesistente deve restituire errore");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testInsertOffer_NegativeOffer() {
	    // Arrange
	    String sessioneId = "sessioneValida";
	    int codImmobile = 101;
	    int offerta = -100;

	    // Act
	    ResponseEntity<String> response = offertaController.insertOffer(sessioneId, codImmobile, offerta);

	    // Assert
	    assertEquals(400, response.getStatusCodeValue(), "CT4: Offerta negativa deve restituire errore");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testInsertOffer_InvalidOffer() {
	    // Arrange
	    String sessioneId = "sessioneValida";
	    int codImmobile = 101;
	    int offerta = 20000000;

	    // Act
	    ResponseEntity<String> response = offertaController.insertOffer(sessioneId, codImmobile, offerta);

	    // Assert
	    assertEquals(400, response.getStatusCodeValue(), "CT5: Offerta esagerata deve restituire errore");
	}
	
}
