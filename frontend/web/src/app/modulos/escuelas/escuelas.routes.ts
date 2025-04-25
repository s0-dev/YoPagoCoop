import { Routes } from '@angular/router';

import { EscuelasComponent } from '../../vistas/escuelas/escuelas.component';
import { MiembrosComponent } from '../../vistas/escuelas/miembros/miembros.component';
import { ReportesComponent } from '../../vistas/escuelas/reportes/reportes.component';

const adminRoutes: Routes = [
  { path: '', component: EscuelasComponent},
  { path: 'miembros', component: MiembrosComponent},
  { path: 'reportes', component: ReportesComponent},
];

export default adminRoutes;