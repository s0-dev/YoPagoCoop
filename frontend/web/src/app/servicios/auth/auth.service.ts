import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Miembro, CredencialLogin, SolicitudRegistro, RespuestaAuth } from '../../modelos/miembros/miembros';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'
  private usuarioActual = new BehaviorSubject<Miembro | null>(null);
  public miembro$ = this.usuarioActual.asObservable();
  
  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.cargarUsuario();
  }
  
  // Verificar si hay un token almacenado y es válido
  estaAutenticado(): boolean {
    return !!localStorage.getItem('authToken');
  }
  
  // Cargar miembro desde localStorage al iniciar la aplicación
  private cargarUsuario(): void {
    const token = localStorage.getItem('authToken');
    const userData = localStorage.getItem('userData');
    
    if (token && userData) {
      try {
        const miembro = JSON.parse(userData);
        this.usuarioActual.next(miembro);
      } catch {
        this.logout();
      }
    }
  }
  
  // Login
  login(credenciales: CredencialLogin): Observable<RespuestaAuth> {
    return this.http.post<RespuestaAuth>(`${this.apiUrl}/login`, credenciales)
      .pipe(
        tap(respuesta => this.almacenarSesion(respuesta)),
        catchError(error => {
          console.error('Error durante el login', error);
          return throwError(() => new Error(error.error?.mensaje || 'Error en la autenticación'));
        })
      );
  }
  
  // Registro
  registro(datos: SolicitudRegistro): Observable<RespuestaAuth> {
    return this.http.post<RespuestaAuth>(`${this.apiUrl}/registro`, datos)
      .pipe(
        tap(respuesta => this.almacenarSesion(respuesta)),
        catchError(error => {
          console.error('Error durante el registro', error);
          return throwError(() => new Error(error.error?.mensaje || 'Error en el registro'));
        })
      );
  }
  
  // Almacenar sesión
  private almacenarSesion(respuesta: RespuestaAuth): void {
    localStorage.setItem('authToken', respuesta.token);
    localStorage.setItem('userData', JSON.stringify(respuesta.miembro));
    this.usuarioActual.next(respuesta.miembro);
  }
  
  // Cerrar sesión
  logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userData');
    this.usuarioActual.next(null);
    this.router.navigate(['/auth/login']);
  }
  
  // Obtener el token JWT para las peticiones autenticadas
  getToken(): string | null {
    return localStorage.getItem('authToken');
  }
  
  // Obtener el miembro actual
  getUsuarioActual(): Miembro | null {
    return this.usuarioActual.value;
  }
}