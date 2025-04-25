import { Routes } from '@angular/router';

import { LoginComponent } from '../../componentes/auth/login/login.component';
import { RegisterComponent } from '../../componentes/auth/register/register.component';

const authRoutes: Routes = [
  { path: 'login', component: LoginComponent }, // login manda al componente de login
  { path: 'register', component: RegisterComponent}, // register manda al componente de register
];

export default authRoutes;