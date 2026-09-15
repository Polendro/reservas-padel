import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../api.config';
import { Reserva } from '../models/reserva.model';

@Injectable({ providedIn: 'root' })
export class ReservaService {
  private http = inject(HttpClient);

  crear(pistaId: number, inicio: string, fin: string): Observable<Reserva> {
    return this.http.post<Reserva>(`${API_URL}/reservas`, { pistaId, inicio, fin });
  }

  misReservas(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${API_URL}/reservas/mias`);
  }

  cancelar(id: number): Observable<Reserva> {
    return this.http.patch<Reserva>(`${API_URL}/reservas/${id}/cancelar`, {});
  }
}
