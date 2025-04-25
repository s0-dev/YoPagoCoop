import { Routes } from '@angular/router';

export const routes: Routes = [
  // Redirección de la raíz al login
  // TODO: APP_ROUTES
  // Chequear si el miembro esta logueado
  // si !logueado == /auth/login
  // si logueado == /miembros
  { path: '', redirectTo: '/auth/login', pathMatch: 'full' },
  // Ruta /auth contiene:
  // /auth/login
  // /auth/register
  {
    path: 'auth',
    loadChildren: () => import('./modulos/auth/auth.routes').then(module => module.default),
  },
  // Ruta /miembros contiene:
  // /miembros/pagos
  // /miembros/perfil
  // /miembros/reportes
  {
    path: 'miembros',
    loadChildren: () => import('./modulos/miembros/miembros.routes').then(module => module.default),
    canActivate: [() => import('./guards/auth/auth.guard').then(guard => guard.AuthGuard)],
  },
  // Ruta /admin contiene:
  // /admin/miembros
  // /admin/reportes
  {
    path: 'admin',
    loadChildren: () => import('./modulos/escuelas/escuelas.routes').then(module => module.default),
    canActivate: [() => import('./guards/auth/auth.guard').then(guard => guard.AuthGuard)],
  }
];
