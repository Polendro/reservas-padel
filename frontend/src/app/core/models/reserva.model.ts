export type EstadoReserva = 'CONFIRMADA' | 'CANCELADA';

export interface Reserva {
  id: number;
  pistaId: number;
  usuarioId: number;
  inicio: string;
  fin: string;
  estado: EstadoReserva;
}
