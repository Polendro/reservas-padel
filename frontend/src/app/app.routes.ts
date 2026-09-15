import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

// loadComponent en vez de un import normal arriba del archivo: cada ruta solo
// descarga su JS cuando el usuario la visita, no todo de golpe al abrir la app.
export const routes: Routes = [
  { path: '', redirectTo: 'disponibilidad', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./features/login/login.component').then((m) => m.LoginComponent)
  },
  {
    path: 'registro',
    loadComponent: () => import('./features/registro/registro.component').then((m) => m.RegistroComponent)
  },
  {
    path: 'disponibilidad',
    loadComponent: () =>
      import('./features/disponibilidad/disponibilidad.component').then((m) => m.DisponibilidadComponent)
  },
  {
    path: 'mis-reservas',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/mis-reservas/mis-reservas.component').then((m) => m.MisReservasComponent)
  },
  { path: '**', redirectTo: 'disponibilidad' }
];
