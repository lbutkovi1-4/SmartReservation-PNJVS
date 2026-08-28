package hr.unipu.smartreservation.controller;

import hr.unipu.smartreservation.model.Reservation;
import hr.unipu.smartreservation.repository.ReservationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/reservation")
    public String showForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation-form";
    }

    // Dohvaća postojeću rezervaciju i puni formu njenim podacima za uređivanje
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rezervacija s ID-om " + id + " ne postoji"));
        model.addAttribute("reservation", reservation);
        return "reservation-form";
    }

    // @Valid pokreće provjeru anotacija iz Reservation.java (@NotBlank, @Min...)
    // Ako ima grešaka, BindingResult ih hvata i vraćamo korisnika natrag na formu s porukama
    @PostMapping("/saveReservation")
    public String saveReservation(@Valid @ModelAttribute("reservation") Reservation reservation,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "reservation-form";
        }
        // Ako reservation.id nije null, JPA radi UPDATE umjesto INSERT-a (isti save() poziv za oboje)
        reservationRepository.save(reservation);
        return "redirect:/reservations";
    }

    @GetMapping("/reservations")
    public String showReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservations";
    }

    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id) {

        reservationRepository.deleteById(id);

        return "redirect:/reservations";
    }
}