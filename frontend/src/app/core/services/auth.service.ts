import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { API_URL } from '../api.config';

const CLAVE_TOKEN = 'padel_booking_token';

interface TokenResponse {
  token: string;
}

interface UsuarioResponse {
  id: number;
  email: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);

  registro(email: string, password: string): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(`${API_URL}/auth/registro`, { email, password });
  }

  login(email: string, password: string): Observable<TokenResponse> {
    return this.http
      .post<TokenResponse>(`${API_URL}/auth/login`, { email, password })
      .pipe(tap((respuesta) => localStorage.setItem(CLAVE_TOKEN, respuesta.token)));
  }

  logout(): void {
    localStorage.removeItem(CLAVE_TOKEN);
  }

  getToken(): string | null {
    return localStorage.getItem(CLAVE_TOKEN);
  }

  estaAutenticado(): boolean {
    return this.getToken() !== null;
  }
}
