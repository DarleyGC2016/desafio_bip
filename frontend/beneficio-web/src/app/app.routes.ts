import { Routes } from '@angular/router';
import { BeneficioListComponent } from './features/beneficio/beneficio-list/beneficio-list.component';


export const routes: Routes = [
    {path:'', component: BeneficioListComponent},
    {path: 'beneficios', component: BeneficioListComponent}
];
