import { Routes } from '@angular/router';
import { BeneficioListComponent } from './features/beneficio/beneficio-list/beneficio-list.component';
import { BeneficioDetailComponent } from './features/beneficio/beneficio-detail/beneficio-detail.component';
import { HomeComponent } from './features/page/home/home.component';


export const routes: Routes = [
    {path:'', component: HomeComponent},
    {path: 'beneficios', component: BeneficioListComponent},
    {path: 'beneficios/:id', component: BeneficioDetailComponent}
];
