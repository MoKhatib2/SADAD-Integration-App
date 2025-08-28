import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Signup } from './components/signup/signup';
import { MainLayout } from './layouts/main-layout/main-layout';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { Home } from './components/home/home';
import { CreateRecord } from './components/create-record/create-record';

export const routes: Routes = [
    {path: '', component: MainLayout ,children: [
        {path: '', redirectTo:'home', pathMatch:'full'},
        {path: 'home', component: Home},
        {path: 'create', component: CreateRecord}
    ]},
    {path: '', component: AuthLayout, children: [
        {path: '', redirectTo:'login', pathMatch:'full'},
        {path: 'login', component: Login},
        {path: 'signup', component: Signup}  
    ]}
];
