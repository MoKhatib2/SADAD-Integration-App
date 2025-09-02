import { Routes, CanActivateFn } from '@angular/router';
import { Login } from './components/login/login';
import { Signup } from './components/signup/signup';
import { MainLayout } from './layouts/main-layout/main-layout';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { Home } from './components/home/home';
import { CreateRecord } from './components/create-record/create-record';
import { authGuard } from './core/guards/auth-guard';
import { UpdateRecord } from './components/update-record/update-record';
import { VerifyCode } from './components/verify-code/verify-code';
import { ResetPassword } from './components/reset-password/reset-password';

export const routes: Routes = [
    {path: '', component: MainLayout, canActivate: [authGuard], children: [
        {path: '', redirectTo:'home', pathMatch:'full'},
        {path: 'home', component: Home},
        {path: 'create', component: CreateRecord},
        {path: 'update/:id', component: UpdateRecord}
    ]},
    {path: '', component: AuthLayout, children: [
        {path: '', redirectTo:'login', pathMatch:'full'},
        {path: 'login', component: Login},
        {path: 'signup', component: Signup},
        {path: 'verify-code', component: VerifyCode},
        {path: 'reset-password', component: ResetPassword} 
    ]}
];
