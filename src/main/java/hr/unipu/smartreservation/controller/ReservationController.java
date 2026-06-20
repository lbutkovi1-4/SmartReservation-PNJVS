package hr.unipu.smartreservation.controller;

import hr.unipu.smartreservation.model.Reservation;
import hr.unipu.smartreservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/saveReservation")
    public String saveReservation(@ModelAttribute Reservation reservation) {
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