import { Component, OnInit, inject } from '@angular/core';
import { Reserva } from '../../core/models/reserva.model';
import { ReservaService } from '../../core/services/reserva.service';

@Component({
  selector: 'app-mis-reservas',
  standalone: true,
  imports: [],
  templateUrl: './mis-reservas.component.html',
  styleUrl: './mis-reservas.component.css'
})
export class MisReservasComponent implements OnInit {
  private reservaService = inject(ReservaService);

  reservas: Reserva[] = [];
  cargando = true;
  error = '';
  cancelandoId: number | null = null;

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.reservaService.misReservas().subscribe({
      next: (reservas) => {
        this.cargando = false;
        this.reservas = reservas;
      },
      error: () => {
        this.cargando = false;
        this.error = 'No se pudieron cargar tus reservas';
      }
    });
  }

  cancelar(reserva: Reserva): void {
    this.error = '';
    this.cancelandoId = reserva.id;

    this.reservaService.cancelar(reserva.id).subscribe({
      next: (actualizada) => {
        this.cancelandoId = null;
        // Actualiza el estado en la fila en vez de recargar toda la lista.
        reserva.estado = actualizada.estado;
      },
      error: () => {
        this.cancelandoId = null;
        this.error = 'No se pudo cancelar (¿quedan menos de 2h para el inicio?)';
      }
    });
  }

  horaDe(iso: string): string {
    return iso.substring(0, 16).replace('T', ' ');
  }
}
