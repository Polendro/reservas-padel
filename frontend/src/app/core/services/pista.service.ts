import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../api.config';
import { FranjaDisponible, Pista } from '../models/pista.model';

@Injectable({ providedIn: 'root' })
export class PistaService {
  private http = inject(HttpClient);

  listar(): Observable<Pista[]> {
    return this.http.get<Pista[]>(`${API_URL}/pistas`);
  }

  consultarDisponibilidad(pistaId: number, fecha: string): Observable<FranjaDisponible[]> {
    return this.http.get<FranjaDisponible[]>(`${API_URL}/pistas/${pistaId}/disponibilidad`, {
      params: { fecha }
    });
  }
}
