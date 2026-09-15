import { Component, inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });

  error = '';
  cargando = false;

  enviar(): void {
    if (this.form.invalid) {
      return;
    }

    this.error = '';
    this.cargando = true;
    const { email, password } = this.form.getRawValue();

    this.authService.registro(email, password).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = error.status === 409 ? 'Ese email ya está registrado' : 'Algo salió mal, inténtalo de nuevo';
      }
    });
  }
}
