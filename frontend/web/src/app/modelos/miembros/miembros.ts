export interface Miembro {
  id?: number;
  email: string;
  nombre: string;
  apellido: string;
}

export interface CredencialLogin {
  email: string;
  password: string;
}

export interface SolicitudRegistro {
  escuelaId: number;
  nombre: string;
  apellido: string;
  email: string;
  celular: string;
  password: string;
  confirmPassword?: string;
  atributos?: { [key: string]: string };
}

export interface RespuestaAuth {
  token: string;
  miembro: Miembro;
  expira?: number;
}