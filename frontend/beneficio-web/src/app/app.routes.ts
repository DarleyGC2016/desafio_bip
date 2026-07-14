import { Routes } from '@angular/router';
import { BeneficioListComponent } from './features/beneficio/beneficio-list/beneficio-list.component';
import { BeneficioDetailComponent } from './features/beneficio/beneficio-detail/beneficio-detail.component';
import { HomeComponent } from './features/page/home/home.component';
import { BeneficioFormComponent } from './features/beneficio/beneficio-form/beneficio-form/beneficio-form.component';
import { TransferirFormComponent } from './features/transferir/transferir-form/transferir-form.component';


export const routes: Routes = [
    {path:'', component: HomeComponent},
    {path: 'beneficios',  children: [
        {path: 'todos', component: BeneficioListComponent},
        {path: 'detalhe/:id', component: BeneficioDetailComponent},
        {path: 'novo', component: BeneficioFormComponent},
        {path: 'transferir', component: TransferirFormComponent},
    ]},
];
