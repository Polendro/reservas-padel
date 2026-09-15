import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { FranjaDisponible, Pista } from '../../core/models/pista.model';
import { PistaService } from '../../core/services/pista.service';
import { ReservaService } from '../../core/services/reserva.service';

@Component({
  selector: 'app-disponibilidad',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './disponibilidad.component.html',
  styleUrl: './disponibilidad.component.css'
})
export class DisponibilidadComponent implements OnInit {
  private pistaService = inject(PistaService);
  private reservaService = inject(ReservaService);
  private authService = inject(AuthService);
  private router = inject(Router);

  pistas: Pista[] = [];
  pistaSeleccionada: number | null = null;
  fecha = '';
  franjas: FranjaDisponible[] = [];

  buscado = false;
  cargandoFranjas = false;
  reservando = false;
  mensaje = '';
  error = '';

  ngOnInit(): void {
    this.pistaService.listar().subscribe((pistas) => (this.pistas = pistas));
  }

  buscar(): void {
    if (!this.pistaSeleccionada || !this.fecha) {
      return;
    }

    this.error = '';
    this.mensaje = '';
    this.cargandoFranjas = true;
    this.buscado = true;
    this.franjas = [];

    this.pistaService.consultarDisponibilidad(this.pistaSeleccionada, this.fecha).subscribe({
      next: (franjas) => {
        this.cargandoFranjas = false;
        this.franjas = franjas;
      },
      error: () => {
        this.cargandoFranjas = false;
        this.error = 'No se pudo consultar la disponibilidad';
      }
    });
  }

  reservar(franja: FranjaDisponible): void {
    if (!this.pistaSeleccionada) {
      return;
    }

    // No tiene sentido intentar el POST sin token: fallaría con 401 igualmente,
    // mejor mandar directo a login.
    if (!this.authService.estaAutenticado()) {
      this.router.navigate(['/login']);
      return;
    }

    this.error = '';
    this.mensaje = '';
    this.reservando = true;

    this.reservaService.crear(this.pistaSeleccionada, franja.inicio, franja.fin).subscribe({
      next: () => {
        this.reservando = false;
        this.mensaje = 'Reserva creada. La puedes ver en "Mis reservas".';
        this.franjas = this.franjas.filter((f) => f !== franja);
      },
      error: () => {
        this.reservando = false;
        this.error = 'No se pudo crear la reserva (puede que otra persona la haya cogido antes)';
      }
    });
  }

  horaDe(iso: string): string {
    return iso.substring(11, 16);
  }
}
