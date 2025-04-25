import { Routes } from '@angular/router';

import { MiembrosComponent } from '../../vistas/miembros/miembros.component';
import { PagosComponent } from '../../vistas/miembros/pagos/pagos.component';
import { ReportesComponent } from '../../vistas/miembros/reportes/reportes.component';
import { PerfilComponent } from '../../vistas/miembros/perfil/perfil.component';

const miembrosRoutes: Routes = [
  { path: '', component: MiembrosComponent},
  { path: 'pagos', component: PagosComponent },
  { path: 'reportes', component: ReportesComponent },
  { path: 'perfil', component: PerfilComponent },
];

export default miembrosRoutes;